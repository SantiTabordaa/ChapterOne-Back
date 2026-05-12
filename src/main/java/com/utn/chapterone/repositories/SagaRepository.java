package com.utn.chapterone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.utn.chapterone.entities.Saga;


import java.util.Optional;

@Repository
public interface SagaRepository extends JpaRepository<Saga, Integer> {
    Optional<Saga> findByNombre(String nombre);
}
