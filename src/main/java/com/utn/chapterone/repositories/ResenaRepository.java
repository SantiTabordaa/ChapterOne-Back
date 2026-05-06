package com.utn.chapterone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.utn.chapterone.entities.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    
}
