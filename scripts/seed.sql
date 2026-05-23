-- Seed data for ChapterOne
-- Run on empty database or after truncating tables

SET FOREIGN_KEY_CHECKS=0;

use chapter_one;

DELETE FROM resena;
DELETE FROM lecturas;
DELETE FROM historial_club;
DELETE FROM posteos_club;
DELETE FROM solicitudes;
DELETE FROM club;
DELETE FROM libros_autores;
DELETE FROM libros_generos;
DELETE FROM libros;
DELETE FROM autores;
DELETE FROM generos;
DELETE FROM sagas;
DELETE FROM usuarios;

SET FOREIGN_KEY_CHECKS=1;

INSERT INTO generos (id_genero, nombre_gen) VALUES
  (1, 'Fantasia'),
  (2, 'Ciencia Ficcion'),
  (3, 'Misterio'),
  (4, 'Romance'),
  (5, 'Historia');

INSERT INTO sagas (id_saga, nombre) VALUES
  (1, 'Cronicas de la Torre'),
  (2, 'Fundacion'),
  (3, 'Sherlock Holmes');

INSERT INTO autores (id_autor, nombre, apellido, pseudonimo, nacionalidad, url_foto) VALUES
  (1, 'Jorge', 'Borges', 'Borges', 'Argentina', 'https://example.com/jorge-borges.jpg'),
  (2, 'Julio', 'Cortazar', 'Julio', 'Argentina', 'https://example.com/julio-cortazar.jpg'),
  (3, 'Agatha', 'Christie', 'Agatha', 'Reino Unido', 'https://example.com/agatha-christie.jpg'),
  (4, 'Isaac', 'Asimov', 'Isaac', 'Rusia', 'https://example.com/isaac-asimov.jpg'),
  (5, 'Laura', 'Gallego', 'Laura', 'Espana', 'https://example.com/laura-gallego.jpg');

INSERT INTO libros (id_libro, titulo, sinopsis, id_saga, nro_tomo, cant_pag) VALUES
  (1, 'El Aleph', 'Relatos clasicos', NULL, NULL, 160),
  (2, 'Rayuela', 'Novela de estructura libre', NULL, NULL, 736),
  (3, 'Asesinato en el Orient Express', 'Misterio en un tren', NULL, NULL, 256),
  (4, 'Fundacion', 'Nacimiento de una ciencia', 2, 1, 255),
  (5, 'La Torre de Marfil', 'Fantasia juvenil', 1, 1, 320);

INSERT INTO libros_autores (id_libro, id_autor) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5);

INSERT INTO libros_generos (id_libro, id_genero) VALUES
  (1, 3),
  (2, 4),
  (3, 3),
  (4, 2),
  (5, 1);

INSERT INTO usuarios (id_usuario, nombre, apellido, email, url_foto_perfil, admin, username, password) VALUES
  (1, 'Ana', 'Lopez', 'ana@correo.com', 'https://example.com/ana.jpg', 1, 'ana.admin', '1234'),
  (2, 'Bruno', 'Garcia', 'bruno@correo.com', 'https://example.com/bruno.jpg', 0, 'bruno.reader', '1234'),
  (3, 'Carla', 'Mendez', 'carla@correo.com', 'https://example.com/carla.jpg', 0, 'carla.book', '1234');

INSERT INTO club (id_club, nombre_club, descrip, id_genero, id_admin, privado) VALUES
  (1, 'Club Fantasia', 'Lecturas de fantasia', 1, 1, 0),
  (2, 'Club Misterio', 'Casos y detectives', 3, 1, 1);

INSERT INTO solicitudes (id_solicitud, estado, fecha_solicitud, fecha_union, rol, usuario_solicitante, club_solicitado) VALUES
  (1, 'Aceptado', '2026-04-01', '2026-04-02', 'miembro', 2, 1),
  (2, 'Pendiente', '2026-04-03', NULL, NULL, 3, 2);

INSERT INTO posteos_club (id_posteo, contenido, fecha_hora_creacion, id_usuario, id_club) VALUES
  (1, 'Bienvenidos al club!', '2026-04-05 10:00:00', 1, 1),
  (2, 'Que libro leemos este mes?', '2026-04-06 12:30:00', 2, 1);

INSERT INTO historial_club (id_historial, id_club, id_libro, mes_anio) VALUES
  (1, 1, 5, '2026-04'),
  (2, 2, 3, '2026-03');

INSERT INTO lecturas (id_lectura, id_usuario, id_libro, estado, fecha_ini, fecha_fin) VALUES
  (1, 2, 4, 'leido', '2026-03-01', '2026-03-20'),
  (2, 3, 3, 'leyendo', '2026-04-01', NULL);

INSERT INTO resena (id_resena, contenido, puntaje, fecha_resena, id_lectura) VALUES
  (1, 'Excelente lectura', 4.5, '2026-03-21', 1);
