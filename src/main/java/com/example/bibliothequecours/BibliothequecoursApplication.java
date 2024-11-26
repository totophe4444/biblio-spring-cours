package com.example.bibliothequecours;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.bibliothequecours.entity.Livre;
import com.example.bibliothequecours.entity.Utilisateur;
import com.example.bibliothequecours.outil.Outil;
import com.example.bibliothequecours.repository.LivreRepository;
import com.example.bibliothequecours.repository.UtilisateurRepository;

@SpringBootApplication
public class BibliothequecoursApplication {
	private  static  LivreRepository  livreRepository  =  null;
	private static UtilisateurRepository utilisateurRepository = null;
	public static void main(String[] args) {
		ApplicationContext  ctx  = SpringApplication.run(BibliothequecoursApplication.class, args);
		livreRepository  =  ctx.getBean(LivreRepository.class);
		utilisateurRepository = ctx.getBean(UtilisateurRepository.class);
		initialiser();
	}
	public  static  void  initialiser()  {
		Livre  livre1  =  new  Livre("Les  quatre  accords",  1,  1602,  "tolteques.jpg",  "Castaneda  a  fait  découvrir  ...");
		Livre  livre2  =  new  Livre("Saturne",  2,  466,  "saturne.jpg",  "Sa  fille  est  encore  un  bébé  quand  ...");
		livreRepository.save(livre1);
		livreRepository.save(livre2);
		String hashPassword;
		Utilisateur utilisateur = null;
		try {
			hashPassword = Outil.hashMdpSha256("tophe");
			utilisateur = new Utilisateur("tophe", hashPassword, "toutapri@gmail.com", "abonne");
			utilisateurRepository.save(utilisateur);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Impossible de créer l'utilisateur tophe");
		}
	}
}
