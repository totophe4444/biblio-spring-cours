package com.example.bibliothequecours.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.example.bibliothequecours.entity.Emprunt;
import com.example.bibliothequecours.entity.Livre;
import com.example.bibliothequecours.entity.Utilisateur;
import com.example.bibliothequecours.outil.Outil;
import com.example.bibliothequecours.repository.EmpruntRepository;
import com.example.bibliothequecours.repository.LivreRepository;
import com.example.bibliothequecours.repository.UtilisateurRepository;

@Service
public  class UtilisateurService implements UtilisateurServiceItf {
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private  LivreRepository  livreRepository;

	@Autowired
	private  LivreServiceItf  livreService;
	
	@Autowired
	private EmpruntRepository empruntRepository;

	@Override
	public Utilisateur creerUtilisateur(String  login,  String  password,  String  mail) {
		String  hashPassword  =  null;
		try  {
			hashPassword  =  Outil.hashMdpSha256(password);
		}  catch  (NoSuchAlgorithmException  e)  {
			System.out.println("ERREUR  -  fonction  hashMdpSha256");
		}
		Utilisateur  utilisateur  =  new  Utilisateur(login,  hashPassword,  mail,  "abonne");
		utilisateurRepository.save(utilisateur);	
		return utilisateur;
	}
	
	@Override
	public Utilisateur validerLoginUtilisateur(String login, String passwd) { /* REFACTORING */
		String hashPassword = null;
		try {
			hashPassword = Outil.hashMdpSha256(passwd);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("ERREUR - fonction hashMdpSha256");
		}
		System.out.println("hashPassword=" + hashPassword);
		Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
		System.out.println("utilisateur = " + utilisateur);
		if(utilisateur.getPasswdHash().equals(hashPassword)) {
			return utilisateur;
		}
		return null;
	}
	/*
	@Override
	public Utilisateur lireUtilisateurParLogin(String login) {
		return utilisateurRepository.findByLogin(login);
	}
	*/
	@Override
	public Utilisateur lireUtilisateurParId(Long id) {
		Utilisateur utilisateur = utilisateurRepository.findById(id).get();
		return utilisateur;
	}
	@Override
	public List<Emprunt> getEmpruntLivreList(Long idUtilisateur) {
		Utilisateur utilisateur = lireUtilisateurParId(idUtilisateur);
		System.out.println("UtilisateurService - getEmpruntLivreList utilisateur:" + utilisateur);
		return utilisateur.getEmprunterLivreList();
	}

	@Override
	public Emprunt getEmpruntById(Long id) {
		return empruntRepository.findById(id).get();
	}

	
	@Override
	public void emprunterListLivreUtilisateur(List<Long> livreIdList, Long idUtilisateur) {
		Utilisateur utilisateur = lireUtilisateurParId(idUtilisateur);
		List<Livre> livreList = livreService.getLivreEmprunterListParLivreIdList(livreIdList);
		System.out.println("UtilisateurService - emprunterListLivreUtilisateur livreList:\n" + livreList);
		System.out.println("majLivreEmprunterListUtilisateur utilisateur=" + utilisateur);
		Emprunt emprunt = null;
		for(int i=0; i < livreList.size(); i++) {
			emprunt = new Emprunt(livreList.get(i), new Date());
			empruntRepository.save(emprunt);
			utilisateur.emprunterLivre(emprunt);
		}
		System.out.println("majLivreEmprunterListUtilisateur utilisateur=" + utilisateur);
		utilisateurRepository.save(utilisateur);	
	}

	@Override
	public void majEmprunt(Emprunt emprunt) {
		empruntRepository.save(emprunt);
	}


}
