package com.utn.chapterone.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.utn.chapterone.dto.autor.AutorListadoDto;
import com.utn.chapterone.dto.autor.AutorDetalleDto;
import com.utn.chapterone.dto.autor.LibroAutorDto;
import com.utn.chapterone.entities.Autor;
import com.utn.chapterone.repositories.AutorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> obtenerTodos() {
        return autorRepository.findAll();
    }

    public List<AutorListadoDto> obtenerListado() {
        return autorRepository.findAll().stream()
            .map(this::mapearAutorListado)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AutorDetalleDto obtenerPorId(Integer id) {
        Autor autor = autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        AutorDetalleDto dto = new AutorDetalleDto();
        dto.setIdAutor(autor.getIdAutor());
        dto.setNombre(autor.getNombre());
        dto.setApellido(autor.getApellido());
        dto.setPseudonimo(autor.getPseudonimo());
        dto.setNacionalidad(autor.getNacionalidad());
        dto.setFechaNacimiento(autor.getFechaNacimiento());
        dto.setFechaFallecimiento(autor.getFechaFallecimiento());
        dto.setResumen(autor.getResumen());
        dto.setLugarNacimiento(autor.getLugarNacimiento());
        dto.setLugarFallecimiento(autor.getLugarFallecimiento());
        dto.setUrlFoto(autor.getUrlFoto());
        dto.setLibros(
            autor.getLibros().stream()
                .map(libro -> new LibroAutorDto(
                    libro.getIdLibro(),
                    libro.getTitulo(),
                    libro.getNroTomo()
                ))
                .toList()
        );

        return dto;
    }

    public Autor crear(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor actualizar(Integer id, Autor autorActualizado) {
        Autor autor = autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        autor.setNombre(autorActualizado.getNombre());
        autor.setApellido(autorActualizado.getApellido());
        autor.setPseudonimo(autorActualizado.getPseudonimo());
        autor.setNacionalidad(autorActualizado.getNacionalidad());
        autor.setFechaNacimiento(autorActualizado.getFechaNacimiento());
        autor.setFechaFallecimiento(autorActualizado.getFechaFallecimiento());
        autor.setResumen(autorActualizado.getResumen());
        autor.setUrlFoto(autorActualizado.getUrlFoto());

        return autorRepository.save(autor);
    }

    public void eliminar(Integer id) {
        autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        autorRepository.deleteById(id);
    }

    private AutorListadoDto mapearAutorListado(Autor autor) {
        return new AutorListadoDto(
            autor.getIdAutor(),
            autor.getNombre(),
            autor.getApellido(),
            autor.getPseudonimo(),
            autor.getNacionalidad(),
            autor.getUrlFoto()
        );
    }
}
