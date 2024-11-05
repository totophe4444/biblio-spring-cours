package com.example.bibliothequecours.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bibliothequecours.entity.Livre;


public interface LivreRepository extends JpaRepository<Livre, Long>  {}
