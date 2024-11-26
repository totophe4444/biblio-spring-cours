package com.example.bibliothequecours.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliothequecours.entity.Livre;
import com.example.bibliothequecours.repository.LivreRepository;


@Service
public class LivreService implements LivreServiceItf{
	@Autowired
	private LivreRepository livreRepository;

	@Override
	public List<Livre> getAllLivre() {
		return livreRepository.findAll();
	}

}
