package com.utn.chapterone.repositories;

import com.utn.chapterone.entities.HistorialClub;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialClubRepository extends JpaRepository<HistorialClub, Integer> {
    List<HistorialClub> findByClubIdClub(Integer idClub);
    List<HistorialClub> findByLibroIdLibro(Integer idLibro);
}
