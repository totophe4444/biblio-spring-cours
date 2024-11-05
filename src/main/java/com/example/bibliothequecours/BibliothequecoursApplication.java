package com.example.bibliothequecours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.bibliothequecours.entity.Livre;
import com.example.bibliothequecours.repository.LivreRepository;

@SpringBootApplication
public class BibliothequecoursApplication {
	private  static  LivreRepository  livreRepository  =  null;
	public static void main(String[] args) {
		ApplicationContext  ctx  = SpringApplication.run(BibliothequecoursApplication.class, args);
		livreRepository  =  ctx.getBean(LivreRepository.class);
		initialiser();
	}
	public  static  void  initialiser()  {
		Livre  livre1  =  new  Livre("Les  quatre  accords",  1,  1602,  "tolteques.jpg",  "Castaneda  a  fait  découvrir  ...");
		Livre  livre2  =  new  Livre("Saturne",  2,  466,  "saturne.jpg",  "Sa  fille  est  encore  un  bébé  quand  ...");
		livreRepository.save(livre1);
		livreRepository.save(livre2);
	}
}
