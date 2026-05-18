package com.utn.chapterone.config;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.utn.chapterone.entities.Autor;

@Component
public class DataSeeder implements ApplicationRunner {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        TypedQuery<Autor> q = em.createQuery("SELECT a FROM Autor a", Autor.class);
        List<Autor> autores = q.getResultList();

        Autor cort = autores.stream()
            .filter(a -> {
                String ap = a.getApellido();
                if (ap == null) return false;
                return ap.equalsIgnoreCase("Cortázar") || ap.equalsIgnoreCase("Cortazar");
            }).findFirst().orElse(null);

        if (cort == null) {
            cort = new Autor();
            cort.setNombre("Julio");
            cort.setApellido("Cortázar");
            cort.setNacionalidad("Argentina");
            em.persist(cort);
        }

        cort.setFechaNacimiento(LocalDate.of(1914, 8, 26));
        cort.setFechaFallecimiento(LocalDate.of(1984, 2, 12));
        cort.setLugarNacimiento("Ixelles, Bruselas, Bélgica");
        cort.setLugarFallecimiento("París, Francia");
        cort.setResumen("Julio Florencio Cortázar (1914–1984) fue un escritor argentino, figura clave del Boom latinoamericano, autor de 'Rayuela' y numerosos cuentos.");

        em.merge(cort);
    }
}
