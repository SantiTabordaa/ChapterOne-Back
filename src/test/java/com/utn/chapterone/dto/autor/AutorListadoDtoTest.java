package com.utn.chapterone.dto.autor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AutorListadoDtoTest {

    @Test
    void deberiaConstruirConConstructorVacioYSetters() {
        AutorListadoDto dto = new AutorListadoDto();

        assertNull(dto.getIdAutor());
        assertNull(dto.getNombre());
        assertNull(dto.getApellido());
        assertNull(dto.getPseudonimo());
        assertNull(dto.getNacionalidad());
        assertNull(dto.getUrlFoto());

        dto.setIdAutor(10);
        dto.setNombre("Jorge");
        dto.setApellido("Borges");
        dto.setPseudonimo("Borges");
        dto.setNacionalidad("Argentina");
        dto.setUrlFoto("https://example.com/autor.jpg");

        assertEquals(10, dto.getIdAutor());
        assertEquals("Jorge", dto.getNombre());
        assertEquals("Borges", dto.getApellido());
        assertEquals("Borges", dto.getPseudonimo());
        assertEquals("Argentina", dto.getNacionalidad());
        assertEquals("https://example.com/autor.jpg", dto.getUrlFoto());
    }

    @Test
    void deberiaConstruirConConstructorCompleto() {
        AutorListadoDto dto = new AutorListadoDto(
                5,
                "Julio",
                "Cortazar",
                "Julio",
                "Argentina",
                "https://example.com/cortazar.jpg"
        );

        assertEquals(5, dto.getIdAutor());
        assertEquals("Julio", dto.getNombre());
        assertEquals("Cortazar", dto.getApellido());
        assertEquals("Julio", dto.getPseudonimo());
        assertEquals("Argentina", dto.getNacionalidad());
        assertEquals("https://example.com/cortazar.jpg", dto.getUrlFoto());
    }
}
