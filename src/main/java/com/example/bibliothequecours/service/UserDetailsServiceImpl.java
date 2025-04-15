package com.example.bibliothequecours.service;

import java.util.ArrayList;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.bibliothequecours.entity.Utilisateur;

import jakarta.websocket.server.ServerEndpoint;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UtilisateurServiceItf utilisateurService;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		System.out.println("UserDetailsServiceImpl - loadUserByUsername login=" + login);
		Utilisateur utilisateur = utilisateurService.lireUtilisateurParLogin(login);
		System.out.println("utilisateur : " + utilisateur);
		if (utilisateur == null) throw new UsernameNotFoundException("L'utilisateur " + login + " n'existe pas.");
		System.out.println("utilisateur=" + utilisateur);
		ArrayList<String> roles = new ArrayList<>();
		if(utilisateur.getRole().equals("ADMIN")) {
			roles.add("ABONNE");
		}
		roles.add(utilisateur.getRole()); 
		String[] aRole = new String[roles.size()];
		for(int i=0; i < roles.size(); i++) aRole[i] = roles.get(i);
		UserDetails userDetails = User
				.withUsername(utilisateur.getLogin())
				.password(utilisateur.getPasswdHash())
				.roles(aRole)
				.build();
		HttpSession session = getCurrentHttpRequest().get().getSession();
		session.setAttribute("id",  utilisateur.getId());
		session.setAttribute("login",  utilisateur.getLogin());
		session.setAttribute("role",  utilisateur.getRole());
		return userDetails;
	}
	public Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
            .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
            .map(ServletRequestAttributes::getRequest);
    }

		
}
