package com.example.bibliothequecours.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import com.example.bibliothequecours.entity.Livre;

@SpringBootTest
class LivreRepositoryTest {

	@Autowired
	private LivreRepository livreRepository; 
	
	@Test
	void testDecrementerNbExemplaireLivre() {
		Livre livre = livreRepository.findById(1L).get();
		System.out.println(livre);
		int nbInit = livre.getNbExemplaire();
		livreRepository.decrementerNbExemplaireLivre(1L);
		livre = livreRepository.findById(1L).get();
		int nb = livre.getNbExemplaire();
		assertEquals(nbInit - 1, nb); 
	}
	
	@Test
	void testIncrementerNbExemplaireLivre() {
		Livre livre = livreRepository.findById(1L).get();
		System.out.println(livre);
		int nbInit = livre.getNbExemplaire();
		livreRepository.incrementerNbExemplaireLivre(1L);
		livre = livreRepository.findById(1L).get();
		int nb = livre.getNbExemplaire();
		assertEquals(nbInit + 1, nb); 	
	}
}
