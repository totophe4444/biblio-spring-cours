package com.example.bibliothequecours.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import com.example.bibliothequecours.entity.Utilisateur;
import com.example.bibliothequecours.repository.UtilisateurRepository;

@Service
public  class UtilisateurService implements UtilisateurServiceItf {
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Override
	public void creerUtilisateur(Utilisateur utilisateur) {
		utilisateurRepository.save(utilisateur);	
	}
	
	@Override
	public Utilisateur lireUtilisateurParLogin(String login) {
		return utilisateurRepository.findByLogin(login);
	}

}
