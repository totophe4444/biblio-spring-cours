package com.example.bibliothequecours.service;

import com.example.bibliothequecours.entity.Utilisateur;

public interface UtilisateurServiceItf {
	void creerUtilisateur(Utilisateur utilisateur);
	Utilisateur lireUtilisateurParLogin(String login);
}
