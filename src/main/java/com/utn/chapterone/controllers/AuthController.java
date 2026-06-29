package com.utn.chapterone.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.utn.chapterone.dto.auth.AuthResponse;
import com.utn.chapterone.dto.auth.LoginRequest;
import com.utn.chapterone.dto.auth.RegisterRequest;
import com.utn.chapterone.entities.Usuario;
import com.utn.chapterone.security.JwtService;
import com.utn.chapterone.services.UsuarioService;



@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final String UPLOAD_DIR = Paths.get(System.getProperty("user.home"), "uploads", "profileImage").toString();
    private final String GET_AVATAR_URL = "http://localhost:8080/images/profile/";

    public AuthController(
            UsuarioService usuarioService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> register(
        @RequestParam(value = "profileImage", required = false) MultipartFile file,
        @RequestParam("nombre") String nombre,
        @RequestParam("apellido") String apellido,
        @RequestParam("email") String email,
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        if(!password.matches("^(?=.*[a-z])(?=.*[A-Z]).{8,}$")){
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "error");
                errorResponse.put("message", "La contraseña no cumple con los requisitos mínimos de seguridad.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        try {
            
            
            Path rutaCompleta = null;
            String rutaDB = null;
            Map<String, Object> response = new HashMap<>();

            if(file != null && !file.isEmpty()){
            // GUARDADO DEL ARCHIVO:
            // limpiar archivo viejo (evitar ataques pathTraversal)
            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            // renombramiento del archivo para evitar duplicados
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = username + "_profile" + extension;

            Path rutaDirectorio = Paths.get(UPLOAD_DIR);
            
            rutaCompleta = rutaDirectorio.resolve(fileName);
            
            rutaDB = GET_AVATAR_URL + fileName;
            //DEBUG
            System.out.println(rutaDB);

            if(!Files.exists(rutaDirectorio)){
                Files.createDirectories(rutaDirectorio);
            }

            // guardado en disco
            Files.copy(file.getInputStream(), rutaCompleta,StandardCopyOption.REPLACE_EXISTING);
            response.put("status", "success");
            response.put("usuario", username);
            response.put("archivoRecibido", originalFilename);
            // DEBUG
            response.put("nombreFinalArchivo", fileName);
        } else {
            response.put("archivoRecibido", "null");
            rutaDB = "noPhotoSelected";
        }
        // Creacion de la RegisterRequest
        RegisterRequest registerRequest = new RegisterRequest(nombre, apellido, email, username, password, rutaDB);
        // guardado en persistencia
        usuarioService.register(registerRequest);
             
        return ResponseEntity.ok(response);
    }catch (IOException e){
        e.printStackTrace();

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("message", "Error al guardar imagen: " + e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.obtenerPorUsername(request.getUsername()) //TODO: cambiar a bloque try catch
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Credenciales inválidas."
                ));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales inválidas."
            );
        }

        String token = jwtService.generateToken(
                usuario.getUsername(),
                Map.of(
                    "admin", usuario.isAdmin(),
                    "urlFotoPerfil", usuario.getUrlFotoPerfil())
        );

        AuthResponse response = new AuthResponse(token, jwtService.getExpirationMs());
        return ResponseEntity.ok(response);
    }
}
