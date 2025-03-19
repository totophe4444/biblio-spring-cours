package com.example.bibliothequecours.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.bibliothequecours.entity.Utilisateur;
import com.example.bibliothequecours.outil.Outil;
import com.example.bibliothequecours.repository.UtilisateurRepository;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {
	
	@InjectMocks
	private UtilisateurService utilisateurService;
	
	@Mock
	private UtilisateurRepository clientDaoMock;
	
	@Test
	void testValiderLoginUtilisateur() {
		String hashPassword = null;
		try {
			hashPassword = Outil.hashMdpSha256("tophe");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utilisateur utilisateurExcept = new Utilisateur("tophe", hashPassword, "toutapri@gmail.com", "abonne");
		when(clientDaoMock.findByLogin("tophe")).thenReturn(utilisateurExcept);
		Utilisateur utilisateurCreer =  utilisateurService.validerLoginUtilisateur("tophe",  "tophe");
		verify(clientDaoMock).findByLogin("tophe");
		assertEquals(utilisateurExcept.getPasswdHash(), utilisateurCreer.getPasswdHash());
	}
}
