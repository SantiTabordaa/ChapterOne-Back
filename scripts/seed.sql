-- Seed data for ChapterOne
-- Run on empty database or after truncating tables

SET FOREIGN_KEY_CHECKS=0;

USE chapter_one;

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
  (5, 'Historia'),
  (6, 'Terror'),
  (7, 'Aventura'),
  (8, 'Clasicos'),
  (9, 'No Ficcion');

INSERT INTO sagas (id_saga, nombre) VALUES
  (1, 'Cronicas de la Torre'),
  (2, 'Fundacion'),
  (3, 'Sherlock Holmes'),
  (4, 'Harry Potter'),
  (5, 'Percy Jackson');

INSERT INTO autores (id_autor, nombre, apellido, pseudonimo, nacionalidad, fecha_nacimiento, fecha_fallecimiento, lugar_nacimiento, lugar_fallecimiento, resumen, url_foto) VALUES
  (1, 'Jorge', 'Borges', NULL, 'argentino', '1899-08-24', '1986-06-14', 'Buenos Aires, Argentina', 'Ginebra, Suiza', 'Escritor argentino clave del siglo XX, autor de relatos y ensayos sobre laberintos, espejos y el infinito.', '/uploads/autores/jorge-borges.jpg'),
  (2, 'Julio', 'Cortazar', NULL, 'argentino', '1914-08-26', '1984-02-12', 'Ixelles, Belgica', 'Paris, Francia', 'Narrador y poeta asociado al boom latinoamericano, reconocido por su experimentacion formal.', '/uploads/autores/julio-cortazar.jpg'),
  (3, 'Agatha', 'Christie', NULL, 'britanica', '1890-09-15', '1976-01-12', 'Torquay, Inglaterra', 'Wallingford, Inglaterra', 'Una de las autoras mas importantes del policial clasico y creadora de Poirot y Miss Marple.', '/uploads/autores/agatha-christie.jpg'),
  (4, 'Isaac', 'Asimov', NULL, 'ruso', '1920-01-02', '1992-04-06', 'Petrovichi, Rusia', 'Nueva York, Estados Unidos', 'Autor prolifico de ciencia ficcion y divulgacion, famoso por la saga de la Fundacion.', '/uploads/autores/isaac-asimov.jpg'),
  (5, 'Laura', 'Gallego', NULL, 'espanola', '1977-10-11', NULL, 'Quart de Poblet, Espana', NULL, 'Escritora espanola de literatura fantastica juvenil, muy leida en el ambito hispano.', '/uploads/autores/laura-gallego.jpg'),
  (6, 'J. K.', 'Rowling', NULL, 'britanica', '1965-07-31', NULL, 'Yate, Inglaterra', NULL, 'Autora britanica de la saga Harry Potter y una de las escritoras contemporaneas mas populares.', '/uploads/autores/jk-rowling.jpg'),
  (7, 'Rick', 'Riordan', NULL, 'estadounidense', '1964-06-05', NULL, 'San Antonio, Estados Unidos', NULL, 'Autor de fantasia juvenil y aventuras mitologicas, conocido por Percy Jackson.', '/uploads/autores/rick-riordan.jpg'),
  (8, 'Mary', 'Shelley', NULL, 'britanica', '1797-08-30', '1851-02-01', 'Londres, Inglaterra', 'Londres, Inglaterra', 'Pionera de la novela gótica y autora de Frankenstein, obra fundacional del terror moderno.', '/uploads/autores/mary-shelley.jpg'),
  (9, 'Gabriel', 'Garcia Marquez', NULL, 'colombiano', '1927-03-06', '2014-04-17', 'Aracataca, Colombia', 'Ciudad de Mexico, Mexico', 'Narrador colombiano, referencia del realismo magico y de la literatura latinoamericana.', '/uploads/autores/gabriel-garcia-marquez.jpg'),
  (10, 'George', 'Orwell', NULL, 'britanico', '1903-06-25', '1950-01-21', 'Motihari, India', 'Londres, Inglaterra', 'Novelista y ensayista britanico, autor de distopias politicas y critica social.', '/uploads/autores/george-orwell.jpg'),
  (11, 'Carlos', 'Ruiz Zafon', NULL, 'espanol', '1964-09-25', '2020-06-19', 'Barcelona, Espana', 'Los Angeles, Estados Unidos', 'Autor de novelas de misterio y atmosfera literaria, muy conocido por El cementerio de los libros olvidados.', '/uploads/autores/carlos-ruiz-zafon.jpg'),
  (12, 'Haruki', 'Murakami', NULL, 'japones', '1949-01-12', NULL, 'Kioto, Japon', NULL, 'Escritor japonés reconocido por sus novelas oniricas y su estilo contemporaneo.', '/uploads/autores/haruki-murakami.jpg'),
  (13, 'Stephen', 'King', NULL, 'estadounidense', '1947-09-21', NULL, 'Portland, Estados Unidos', NULL, 'Autor estadounidense de terror y suspenso, uno de los mas leidos del genero.', '/uploads/autores/stephen-king.jpg'),
  (14, 'Victoria', 'Hislop', NULL, 'britanica', '1959-06-08', NULL, 'Kent, Inglaterra', NULL, 'Novelista britanica de ficcion historica y novelas corales centradas en memoria y paisajes.', '/uploads/autores/victoria-hislop.jpg'),
  (15, 'Homero', '', NULL, 'griego', NULL, NULL, 'Jonia, Grecia', NULL, 'Poeta griego al que se atribuyen la Iliada y la Odisea, figura central de la literatura clasica.', '/uploads/autores/homero.jpg'),
  (16, 'Platon', '', NULL, 'griego', NULL, NULL, 'Atenas, Grecia', NULL, 'Filosofo griego clasico, autor de dialogos fundamentales sobre politica, etica y conocimiento.', '/uploads/autores/platon.jpg'),
  (17, 'German', 'Beder', NULL, 'argentino', '1983-05-24', NULL, 'Bahia Blanca, Argentina', NULL, 'Periodista, novelista, presentador de radio y streamer argentino, autor de relatos y libros sobre su experiencia personal y profesional.', '/uploads/autores/german-beder.jpg'),
  (18, 'Lucas Nicolas', 'Rodriguez', 'Luis Luis Rodriguez', 'argentino', '1992-10-24', NULL, 'Argentina', NULL, 'Humorista, streamer y escritor argentino, coautor de Desplazamientos junto a Sebastian Porrini.', '/uploads/autores/lucas-rodriguez.jpg'),
  (19, 'Sebastian', 'Porrini', NULL, 'argentino', NULL, NULL, 'Argentina', NULL, 'Escritor y docente argentino, coautor de Desplazamientos junto a Lucas Rodriguez.', '/uploads/autores/sebastian-porrini.jpg'),
  (20, 'Adrian', 'Newey', NULL, 'britanico', '1958-12-26', NULL, 'Stroud, Inglaterra', NULL, 'Ingeniero y diseñador tecnico britanico de Formula 1, autor de How to Build a Car.', '/uploads/autores/adrian-newey.jpg');

INSERT INTO libros (id_libro, titulo, sinopsis, id_saga, nro_tomo, cant_pag, url_portada) VALUES
  (1, 'El Aleph', 'Relatos clasicos sobre el infinito y la memoria', NULL, NULL, 160, '/uploads/libros/el-aleph.jpg'),
  (2, 'Rayuela', 'Novela de estructura libre y lectura no lineal', NULL, NULL, 736, '/uploads/libros/rayuela.jpg'),
  (3, 'Asesinato en el Orient Express', 'Un misterio cerrado en un tren de lujo', NULL, NULL, 256, '/uploads/libros/asesinato-en-el-orient-express.jpg'),
  (4, 'Fundacion', 'Nacimiento de una ciencia y de un imperio galactico', 2, 1, 255, '/uploads/libros/fundacion.jpg'),
  (5, 'La Torre de Marfil', 'Fantasia juvenil con secretos antiguos', 1, 1, 320, '/uploads/libros/la-torre-de-marfil.jpg'),
  (6, 'Harry Potter y la Piedra Filosofal', 'El inicio de la saga del joven mago', 4, 1, 320, '/uploads/libros/harry-potter-piedra-filosofal.jpg'),
  (7, 'Percy Jackson y el ladron del rayo', 'Aventura mitologica con dioses griegos', 5, 1, 384, '/uploads/libros/percy-jackson-ladron-del-rayo.jpg'),
  (8, 'Frankenstein', 'La criatura y las consecuencias de jugar a ser dios', NULL, NULL, 280, '/uploads/libros/frankenstein.jpg'),
  (9, 'El nombre del viento', 'Un musico cuenta su vida y su leyenda', NULL, NULL, 880, '/uploads/libros/el-nombre-del-viento.jpg'),
  (10, 'Cien anios de soledad', 'La historia de la familia Buendia en Macondo', NULL, NULL, 496, '/uploads/libros/cien-anios-de-soledad.jpg'),
  (11, '1984', 'Un futuro distopico vigilado por el Gran Hermano', NULL, NULL, 328, '/uploads/libros/1984.jpg'),
  (12, 'La sombra del viento', 'Un misterio literario en la Barcelona de posguerra', NULL, NULL, 576, '/uploads/libros/la-sombra-del-viento.jpg'),
  (13, 'Tokio blues', 'Una historia intimista sobre memoria y juventud', NULL, NULL, 384, '/uploads/libros/tokio-blues.jpg'),
  (14, 'It', 'Un grupo de amigos enfrenta un terror ancestral', NULL, NULL, 1138, '/uploads/libros/it.jpg'),
  (15, 'La isla', 'Una historia de secretos, guerra y amor en Creta', NULL, NULL, 624, '/uploads/libros/la-isla.jpg'),
  (16, 'Iliada', 'Poema epico sobre la guerra de Troya y la furia de Aquiles', NULL, NULL, 704, '/uploads/libros/iliada.jpg'),
  (17, 'Odisea', 'Poema epico del regreso de Ulises a su hogar', NULL, NULL, 560, '/uploads/libros/odisea.jpg'),
  (18, 'La Republica', 'Dialogo filosofico sobre justicia, politica y la ciudad ideal', NULL, NULL, 416, '/uploads/libros/la-republica.jpg'),
  (19, 'La vez que casi me muero y otros relatos', 'Coleccion de relatos breves con humor e historias de la vida moderna', NULL, NULL, 224, '/uploads/libros/la-vez-que-casi-me-muero-y-otros-relatos.jpg'),
  (21, 'El Legado: Historia de un grupo que le torcio la mano al destino', 'Cronica sobre un grupo y su recorrido hacia una victoria improbable', NULL, NULL, 288, '/uploads/libros/el-legado.jpg'),
  (22, 'Desplazamientos', 'Dialogo entre Lucas Rodriguez y Sebastian Porrini sobre politica, arte y vida espiritual', NULL, NULL, 144, '/uploads/libros/desplazamientos.jpg'),
  (20, 'Como hacer un coche', 'Autobiografia de Adrian Newey sobre su carrera en Formula 1', NULL, NULL, 438, '/uploads/libros/como-hacer-un-coche.jpg');

INSERT INTO libros_autores (id_libro, id_autor) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5),
  (6, 6),
  (7, 7),
  (8, 8),
  (9, 1),
  (9, 5),
  (10, 9),
  (11, 10),
  (12, 11),
  (13, 12),
  (14, 13),
  (15, 14),
  (16, 15),
  (17, 15),
  (18, 16),
  (19, 17),
  (20, 20),
  (22, 18),
  (21, 17),
  (22, 19);

INSERT INTO libros_generos (id_libro, id_genero) VALUES
  (1, 8),
  (2, 8),
  (3, 3),
  (4, 2),
  (5, 1),
  (6, 1),
  (6, 7),
  (7, 7),
  (8, 6),
  (9, 1),
  (10, 5),
  (10, 8),
  (11, 2),
  (11, 8),
  (12, 3),
  (12, 8),
  (13, 4),
  (14, 6),
  (15, 5),
  (16, 8),
  (17, 8),
  (18, 8),
  (19, 8),
  (20, 5),
  (21, 9),
  (22, 9);

INSERT INTO usuarios (id_usuario, nombre, apellido, email, url_foto_perfil, admin, username, password) VALUES
  (1, 'Ana', 'Lopez', 'ana@correo.com', 'https://example.com/ana.jpg', 1, 'ana.admin', '1234'),
  (2, 'Bruno', 'Garcia', 'bruno@correo.com', 'https://example.com/bruno.jpg', 0, 'bruno.reader', '1234'),
  (3, 'Carla', 'Mendez', 'carla@correo.com', 'https://example.com/carla.jpg', 0, 'carla.book', '1234'),
  (4, 'Diego', 'Fernandez', 'diego@correo.com', 'https://example.com/diego.jpg', 0, 'diego.club', '1234'),
  (5, 'Elena', 'Sosa', 'elena@correo.com', 'https://example.com/elena.jpg', 0, 'elena.pages', '1234'),
  (6, 'Facundo', 'Perez', 'facundo@correo.com', 'https://example.com/facundo.jpg', 0, 'facu.reads', '1234'),
  (7, 'Gabriela', 'Torres', 'gabriela@correo.com', 'https://example.com/gabriela.jpg', 0, 'gaby.books', '1234'),
  (8, 'Hector', 'Sosa', 'hector@correo.com', 'https://example.com/hector.jpg', 0, 'hector.lectura', '1234'),
  (9, 'Ines', 'Ramirez', 'ines@correo.com', 'https://example.com/ines.jpg', 0, 'ines.pages', '1234'),
  (10, 'Juan', 'Diaz', 'juan@correo.com', 'https://example.com/juan.jpg', 0, 'juan.novelas', '1234');

INSERT INTO club (id_club, nombre_club, descrip, id_genero, id_admin, privado) VALUES
  (1, 'Club Fantasia', 'Lecturas de fantasia y worldbuilding', 1, 1, 0),
  (2, 'Club Misterio', 'Casos, detectives y crimenes clasicos', 3, 1, 1),
  (3, 'Club Ciencia Ficcion', 'Tecnologia, futuros posibles y sagas espaciales', 2, 1, 0),
  (4, 'Club Clasicos', 'Obras canonicas de la literatura', 8, 1, 0),
  (5, 'Club Terror', 'Historias para leer con la luz prendida', 6, 1, 1),
  (6, 'Club Romance', 'Novelas de relaciones, emociones y epocas distintas', 4, 1, 0),
  (7, 'Club Historico', 'Novelas ambientadas en hechos y contextos reales', 5, 1, 0);

INSERT INTO solicitudes (id_solicitud, estado, fecha_solicitud, fecha_union, rol, usuario_solicitante, club_solicitado) VALUES
  (1, 'Aceptado', '2026-04-01 09:30:00', '2026-04-02 11:15:00', 'miembro', 2, 1),
  (2, 'Pendiente', '2026-04-03 18:20:00', NULL, NULL, 3, 2),
  (3, 'Aceptado', '2026-04-04 14:05:00', '2026-04-05 09:10:00', 'moderador', 4, 3),
  (4, 'Rechazado', '2026-04-06 16:40:00', NULL, NULL, 5, 1),
  (5, 'Aceptado', '2026-04-07 20:00:00', '2026-04-08 08:45:00', 'miembro', 6, 4),
  (6, 'Pendiente', '2026-04-09 10:00:00', NULL, NULL, 7, 5),
  (7, 'Aceptado', '2026-04-10 12:15:00', '2026-04-11 09:30:00', 'miembro', 8, 6),
  (8, 'Aceptado', '2026-04-12 17:45:00', '2026-04-13 08:00:00', 'moderador', 9, 7),
  (9, 'Pendiente', '2026-04-14 19:25:00', NULL, NULL, 10, 2),
  (10, 'Rechazado', '2026-04-15 15:40:00', NULL, NULL, 7, 3);

INSERT INTO posteos_club (id_posteo, contenido, fecha_hora_creacion, id_usuario, id_club) VALUES
  (1, 'Bienvenidos al club!', '2026-04-05 10:00:00', 1, 1),
  (2, 'Que libro leemos este mes?', '2026-04-06 12:30:00', 2, 1),
  (3, 'Propongo un ciclo de autores argentinos', '2026-04-07 09:15:00', 3, 4),
  (4, 'A alguien mas le gusta la ciencia ficcion clasica?', '2026-04-08 18:45:00', 4, 3),
  (5, 'Este mes leemos Frankenstein', '2026-04-09 20:00:00', 1, 2),
  (6, 'La tension en 1984 sigue siendo impresionante', '2026-04-10 19:10:00', 7, 3),
  (7, 'Recomiendo Cien anios de soledad para el proximo mes', '2026-04-11 13:20:00', 8, 4),
  (8, 'Armemos una lista de lecturas de terror clasico', '2026-04-12 21:05:00', 1, 5),
  (9, 'Estamos leyendo Harry Potter en orden', '2026-04-13 08:55:00', 9, 1),
  (10, 'Hoy debatimos sobre la belleza de las novelas historicas', '2026-04-14 17:35:00', 10, 7),
  (11, 'Percy Jackson tiene un gran arranque', '2026-04-15 11:45:00', 6, 1),
  (12, 'La sombra del viento es ideal para este club', '2026-04-16 16:10:00', 5, 2);

INSERT INTO historial_club (id_historial, id_club, id_libro, mes_anio) VALUES
  (1, 1, 5, '2026-04'),
  (2, 2, 3, '2026-03'),
  (3, 3, 4, '2026-04'),
  (4, 4, 1, '2026-02'),
  (5, 4, 8, '2026-03'),
  (6, 1, 6, '2026-05'),
  (7, 2, 12, '2026-05'),
  (8, 3, 11, '2026-06'),
  (9, 4, 10, '2026-06'),
  (10, 5, 14, '2026-05'),
  (11, 6, 13, '2026-05'),
  (12, 7, 15, '2026-04');

INSERT INTO lecturas (id_lectura, id_usuario, id_libro, estado, fecha_ini, fecha_fin) VALUES
  (1, 2, 4, 'leido', '2026-03-01 00:00:00', '2026-03-20 21:30:00'),
  (2, 3, 3, 'leyendo', '2026-04-01 19:00:00', NULL),
  (3, 4, 6, 'leido', '2026-04-02 08:15:00', '2026-04-12 22:10:00'),
  (4, 5, 8, 'quiero leer', '2026-04-10 13:00:00', NULL),
  (5, 6, 7, 'leyendo', '2026-04-11 17:25:00', NULL),
  (6, 2, 1, 'leido', '2026-02-10 10:00:00', '2026-02-18 23:00:00'),
  (7, 7, 10, 'leido', '2026-05-01 09:00:00', '2026-05-18 20:30:00'),
  (8, 8, 11, 'leyendo', '2026-05-03 14:10:00', NULL),
  (9, 9, 12, 'quiero leer', '2026-05-05 18:40:00', NULL),
  (10, 10, 13, 'leido', '2026-05-07 12:05:00', '2026-05-22 19:55:00'),
  (11, 7, 14, 'leyendo', '2026-05-10 16:15:00', NULL),
  (12, 8, 15, 'quiero leer', '2026-05-12 09:25:00', NULL),
  (13, 9, 2, 'leido', '2026-05-14 11:10:00', '2026-05-27 21:00:00'),
  (14, 10, 9, 'leyendo', '2026-05-16 19:35:00', NULL),
  (15, 3, 19, 'leido', '2026-05-18 08:20:00', '2026-05-28 20:15:00'),
  (16, 4, 20, 'leido', '2026-05-19 10:45:00', '2026-05-27 19:40:00'),
  (17, 5, 16, 'leyendo', '2026-05-20 18:05:00', NULL),
  (18, 6, 17, 'quiero leer', '2026-05-21 12:30:00', NULL),
  (19, 7, 18, 'leido', '2026-05-22 09:15:00', '2026-05-29 21:50:00'),
  (20, 8, 21, 'leyendo', '2026-05-23 14:55:00', NULL),
  (21, 9, 22, 'quiero leer', '2026-05-24 16:05:00', NULL),
  (22, 10, 19, 'leido', '2026-05-25 11:40:00', '2026-06-01 22:10:00');

INSERT INTO resena (id_resena, contenido, puntaje, fecha_resena, id_lectura) VALUES
  (1, 'Excelente lectura, muy potente en ideas y simbolismo', 4.5, '2026-03-21', 1),
  (2, 'Misterio muy bien armado y con gran final', 4.8, '2026-04-13', 3),
  (3, 'Buena construccion del mundo y ritmo interesante', 4.2, '2026-02-19', 6),
  (4, 'Distopia vigentisima y muy incomoda', 4.7, '2026-05-23', 7),
  (5, 'Una obra intensa y muy atmosferica', 4.1, '2026-05-24', 10),
  (6, 'Arranque solido para una saga divertida', 4.6, '2026-05-25', 13),
  (7, 'Dialogos agiles y reflexiones muy personales', 4.3, '2026-05-29', 15),
  (8, 'Una mirada muy entretenida a la Formula 1 desde adentro', 4.9, '2026-05-28', 16),
  (9, 'Relatos cortos con humor y mucho ritmo', 4.4, '2026-05-30', 22),
  (10, 'Un libro intenso y muy bien construido', 4.6, '2026-05-31', 19),
  (11, 'Clave para entender la fantasia clasica', 4.8, '2026-05-31', 17),
  (12, 'Una obra filosofica imprescindible', 4.7, '2026-06-01', 20);
