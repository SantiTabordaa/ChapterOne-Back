package com.utn.chapterone.repositories;

import com.utn.chapterone.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Integer> {
}