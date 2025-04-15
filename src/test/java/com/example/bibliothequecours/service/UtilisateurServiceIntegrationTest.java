package com.example.bibliothequecours.service;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bibliothequecours.entity.Utilisateur;
import com.example.bibliothequecours.outil.Outil;
import com.example.bibliothequecours.repository.LivreRepository;

@SpringBootTest
class UtilisateurServiceIntegrationTest {

	@Autowired
	private UtilisateurService utilisateurService;
	
	//@Test
	void testValiderLoginUtilisateur() {
		String  hashPassword  =  null;
		try  {
			hashPassword  =  Outil.hashMdpSha256("tophe");
		}  catch  (NoSuchAlgorithmException  e)  {
			System.out.println("ERREUR  -  fonction  hashMdpSha256");
		}
		Utilisateur utilisateur = utilisateurService.validerLoginUtilisateur("tophe", "tophe");
		System.out.println("utilisateur ici:" + utilisateur);
		assertEquals(utilisateur.getRole(), "abonne");
		assertEquals(utilisateur.getLogin(), "tophe");
		assertEquals(utilisateur.getPasswdHash(), hashPassword);
	
	}
}
