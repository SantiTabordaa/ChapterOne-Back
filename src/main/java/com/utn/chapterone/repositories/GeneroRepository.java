package com.utn.chapterone.repositories;

import com.utn.chapterone.entities.Genero;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    Optional<Genero> findByNombreGen(String nombreGen);
}
