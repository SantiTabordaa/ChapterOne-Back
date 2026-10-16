package com.utn.chapterone.repositories;

import com.utn.chapterone.entities.Lectura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturaRepository extends JpaRepository<Lectura, Integer> {
  @Query("Select AVG(r.puntaje) from Lectura l join l.resena r where l.libro.idLibro = :idLibro")
  Double obtenerValoracionPromedio(@Param("idLibro") Integer idLibro);

  List<Lectura> findByUsuario_IdUsuarioOrderByIdLecturaDesc(Integer idUsuario);

  Optional<Lectura> findByUsuario_IdUsuarioAndLibro_IdLibro(Integer idUsuario, Integer idLibro);
}
