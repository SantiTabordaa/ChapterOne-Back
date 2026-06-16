package com.utn.chapterone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utn.chapterone.entities.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    
    @Query("SELECT COUNT(s) FROM Solicitud s WHERE s.club.idClub = :idClub AND s.estado = 'Aceptado'")
    Long contarIntegrantesAceptadosPorClub(@Param("idClub") Integer idClub);
}