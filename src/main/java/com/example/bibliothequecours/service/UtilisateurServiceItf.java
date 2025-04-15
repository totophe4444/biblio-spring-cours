package com.example.bibliothequecours.service;

import java.util.List;
import com.example.bibliothequecours.entity.Emprunt;
import com.example.bibliothequecours.entity.Livre;
import com.example.bibliothequecours.entity.Utilisateur;

public interface UtilisateurServiceItf {
	Utilisateur creerUtilisateur(String  login,  String  password,  String  mail); /* REFACTORING */
	Utilisateur lireUtilisateurParLogin(String login);
	Utilisateur validerLoginUtilisateur(String login, String passwd);
	Utilisateur lireUtilisateurParId(Long id);
	void emprunterListLivreUtilisateur(List<Long> livreIdList, Long id);
	List<com.example.bibliothequecours.entity.Emprunt> getEmpruntLivreList(Long idUtilisateur);
	Emprunt getEmpruntById(Long id);
	void majEmprunt(Emprunt emprunt);
}
