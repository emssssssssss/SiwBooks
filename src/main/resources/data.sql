INSERT INTO libro (id, titolo, descrizione, genere, anno_pubblicazione)
VALUES 
  (1, 'Il Signore degli Anelli', 'Un epico viaggio nella Terra di Mezzo.', 'Fantasy', 1954),
  (2, 'Harry Potter e la Pietra Filosofale', 'Il primo anno alla Scuola di Magia e Stregoneria di Hogwarts.', 'Fantasy', 1997),
  (3, 'Il codice da Vinci', 'Un thriller ricco di misteri e simboli nascosti.', 'Thriller', 2003),
  (4, 'Sherlock Holmes: Uno studio in rosso', 'La prima indagine del celebre investigatore.', 'Thriller', 1887);

-- Immagini (mappa immagini alle righe della tabella "libro_immagini")
INSERT INTO libro_immagini (libro_id, immagini)
VALUES 
  (1, '/imges/lotr.jpg'),
  (2, '/imges/hp.jpg'),
  (3, '/img/daVinci.jpg'),
  (4, '/img/holmes.jpg');


INSERT INTO utente (email, username, password, ruolo)
VALUES (
  'admin@email.com',
  'admin',
  '$2a$10$zkVCkO.ZxevVP9x/fzF0O.7jUn2M1wbxEOkbc/r9XzvRkNG6xA9xi', -- "admin123"
  'ADMIN'
);
