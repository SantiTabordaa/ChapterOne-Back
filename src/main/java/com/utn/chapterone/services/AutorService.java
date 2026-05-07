package com.utn.chapterone.services;

import org.springframework.stereotype.Service;
import com.utn.chapterone.entities.Autor;
import com.utn.chapterone.repositories.AutorRepository;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> obtenerTodos() {
        return autorRepository.findAll();
    }

    public Autor obtenerPorId(Integer id) {
        return autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
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
        autor.setUrlFoto(autorActualizado.getUrlFoto());

        return autorRepository.save(autor);
    }

    public void eliminar(Integer id) {
        autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        autorRepository.deleteById(id);
    }
}
