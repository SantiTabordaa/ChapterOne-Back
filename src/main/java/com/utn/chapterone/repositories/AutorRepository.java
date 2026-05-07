package com.utn.chapterone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.utn.chapterone.entities.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    
}
