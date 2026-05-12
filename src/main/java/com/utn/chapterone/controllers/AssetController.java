package com.utn.chapterone.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AssetController {

    private static final Path BASE_UPLOADS_DIR = Paths.get("uploads");

    @GetMapping("/uploads/{entidad}/{nombre}")
    public ResponseEntity<Resource> getAsset(
            @PathVariable String entidad,
            @PathVariable String nombre
    ) throws MalformedURLException {
        Path entityPath = BASE_UPLOADS_DIR.resolve(entidad).normalize();
        Path filePath = entityPath.resolve(nombre).normalize();

        if (!filePath.startsWith(BASE_UPLOADS_DIR)) {
            return ResponseEntity.badRequest().build();
        }

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());
        MediaType contentType = MediaTypeFactory.getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok().contentType(contentType).body(resource);
    }
}
