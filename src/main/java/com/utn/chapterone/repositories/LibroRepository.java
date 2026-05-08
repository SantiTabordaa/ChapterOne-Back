package com.utn.chapterone.repositories;

import com.utn.chapterone.entities.Libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Integer>{
  
}
