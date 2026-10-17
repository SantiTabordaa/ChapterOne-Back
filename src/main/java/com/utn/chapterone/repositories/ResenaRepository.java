package com.utn.chapterone.repositories;

import com.utn.chapterone.entities.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    List<Resena> findByLectura_Libro_IdLibroOrderByFechaResenaDescIdResenaDesc(Integer idLibro);

    Optional<Resena> findByLectura_IdLectura(Integer idLectura);

    Optional<Resena> findByLectura_Usuario_IdUsuarioAndLectura_Libro_IdLibro(Integer idUsuario, Integer idLibro);
}
