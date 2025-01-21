package com.example.bibliothequecours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.bibliothequecours.entity.Emprunt;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {}
