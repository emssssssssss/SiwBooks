INSERT INTO utente (email, username, password, ruolo)
VALUES (
  'admin@email.com',
  'admin',
  '$2a$11$SXWTmeJqut5qWbeYWHgAfuaN2N4tHJZ6z2MwUqZG.0DnrBIhFJy2G', -- "admin123"
  'ADMIN'
);



-- 7 libri Fantasy (senza id)
INSERT INTO libro (titolo, descrizione, genere, anno_pubblicazione) VALUES
  ('Lo Hobbit', 'Il piccolo e improbabile eroe Bilbo Baggins in un''avventura straordinaria.', 'Fantasy', 1937),
  ('A Game of Thrones', 'Intrighi e lotte per il Trono di Spade in un continente diviso.', 'Fantasy', 1996),
  ('Le Cronache di Narnia: Il Leone...', 'I fratelli Pevensie scoprono un mondo magico oltre l''armadio.', 'Fantasy', 1950),
  ('Il Nome del Vento', 'La storia di Kvothe, mago e menestrello, narrata in prima persona.', 'Fantasy', 2007),
  ('Il Signore degli Anelli', 'Un epico viaggio nella Terra di Mezzo.', 'Fantasy', 1954),
  ('Mistborn: L''Ultimo Impero', 'La magia del metallo e la rivolta contro un dio tiranno.', 'Fantasy', 2006),
  ('La Ruota del Tempo: L''Occhio...', 'La missione di Rand al''Ultimo Villaggio per salvare il mondo dal male.', 'Fantasy', 1990);

-- Copertine Fantasy
-- Qui devi sostituire libro_id con gli id effettivi ottenuti dopo l'inserimento di libri
-- (Esempio: puoi fare una SELECT su titolo per recuperare libro_id)
-- Per ora li lascio come placeholders (da aggiornare)
INSERT INTO libro_immagini (libro_id, immagini) VALUES
  (1, '/images/hobbit.jpg'),
  (2, '/images/got.jpg'),
  (3, '/images/narnia.jpg'),
  (4, '/images/nameofwind.jpg'),
  (5, '/images/lotr.jpg'),
  (6, '/images/mistborn.jpg'),
  (7, '/images/wheeloftime.jpg');

-- 7 libri Thriller (senza id)
INSERT INTO libro (titolo, descrizione, genere, anno_pubblicazione) VALUES
  ('Il Codice Da Vinci', 'Un thriller ricco di misteri e simboli nascosti.', 'Thriller', 2003),
  ('Angeli e Demoni', 'Robert Langdon contro gli Illuminati in Vaticano.', 'Thriller', 2000),
  ('Inferno', 'Langdon si risveglia a Firenze senza memoria e in fuga.', 'Thriller', 2013),
  ('Il Simbolo Perduto', 'Una missione per salvare Washington dai segreti massonici.', 'Thriller', 2009),
  ('Origine', 'Un''innovazione scientifica che potrebbe cambiare la religione.', 'Thriller', 2017),
  ('Gone Girl', 'Un matrimonio perfetto nasconde bugie letali.', 'Thriller', 2012),
  ('Dark Places', 'Una donna cerca la verità su un massacro in famiglia.', 'Thriller', 2009);

-- Immagini Thriller (da aggiornare libro_id)
INSERT INTO libro_immagini (libro_id, immagini) VALUES
  (8, '/images/daVinci.jpg'),
  (9, '/images/angelsdemons.jpg'),
  (10, '/images/inferno.jpg'),
  (11, '/images/simboloperduto.jpg'),
  (12, '/images/origine.jpg'),
  (13, '/images/gonegirl.jpg'),
  (14, '/images/darkplaces.jpg');

-- Libri Horror (senza id)
INSERT INTO libro (titolo, descrizione, genere, anno_pubblicazione) VALUES
  ('It', 'Una forza malvagia prende la forma di un clown e terrorizza i bambini.', 'Horror', 1986),
  ('Shining', 'Un hotel isolato spinge un padre alla follia.', 'Horror', 1977),
  ('Pet Sematary', 'Un cimitero segreto restituisce la vita... ma non senza conseguenze.', 'Horror', 1983),
  ('Misery', 'Uno scrittore è prigioniero della sua fan più ossessiva.', 'Horror', 1987),
  ('Carrie', 'Una ragazza con poteri telecinetici si vendica dei suoi aguzzini.', 'Horror', 1974),
  ('Doctor Sleep', 'Il seguito di Shining, Danny Torrance contro entità oscure.', 'Horror', 2013),
  ('Frankenstein', 'Uno scienziato dà vita a una creatura con effetti devastanti.', 'Horror', 1818);

-- Immagini Horror (da aggiornare libro_id)
INSERT INTO libro_immagini (libro_id, immagini) VALUES
  (15, '/images/it.jpg'),
  (16, '/images/shining.jpg'),
  (17, '/images/petsematary.jpg'),
  (18, '/images/misery.jpg'),
  (19, '/images/carrie.jpg'),
  (20, '/images/doctorsleep.jpg'),
  (21, '/images/frankenstein.jpg');

-- Libri Romance (senza id)
INSERT INTO libro (titolo, descrizione, genere, anno_pubblicazione) VALUES
  ('Orgoglio e Pregiudizio', 'Elizabeth Bennet e Mr. Darcy si sfidano a colpi di intelletto e sentimento.', 'Romance', 1813),
  ('Ragione e Sentimento', 'Due sorelle affrontano amori e inganni in epoca georgiana.', 'Romance', 1811),
  ('Emma', 'Una giovane donna si diletta nel fare da cupido con esiti comici.', 'Romance', 1815),
  ('Le pagine della nostra vita', 'Un amore che supera tempo e memoria.', 'Romance', 1996),
  ('Come un uragano', 'Una storia intensa tra due anime alla deriva.', 'Romance', 1998),
  ('I passi dell''amore', 'Un amore giovane e tragico che cambia una vita.', 'Romance', 1999),
  ('Il meglio di me', 'Due ex amanti si rincontrano anni dopo per affrontare il passato.', 'Romance', 2011);

-- Immagini Romance (da aggiornare libro_id)
INSERT INTO libro_immagini (libro_id, immagini) VALUES
  (22, '/images/pride.jpg'),
  (23, '/images/sense.jpg'),
  (24, '/images/emma.jpg'),
  (25, '/images/notebook.jpg'),
  (26, '/images/hurricane.jpg'),
  (27, '/images/walk.jpg'),
  (28, '/images/bestofme.jpg');

-- Autori (senza id)
INSERT INTO autore (nome, cognome, data_nascita, data_morte, nazionalita, foto_url) VALUES
  ('J.R.R.', 'Tolkien', 1892, 1973, 'Britannica', '/images/tolkien.jpg'),
  ('George R.R.', 'Martin', 1948, NULL, 'Statunitense', '/images/martin.jpg'),
  ('C.S.', 'Lewis', 1898, 1963, 'Britannica', '/images/lewis.jpg'),
  ('Patrick', 'Rothfuss', 1973, NULL, 'Statunitense', '/images/rothfuss.jpg'),
  ('Brandon', 'Sanderson', 1975, NULL, 'Statunitense', '/images/sanderson.jpg'),
  ('Robert', 'Jordan', 1948, 2007, 'Statunitense', '/images/jordan.jpg'),
  ('Dan', 'Brown', 1964, NULL, 'Statunitense', '/images/brown.jpg'),
  ('Gillian', 'Flynn', 1971, NULL, 'Statunitense', '/images/flynn.jpg'),
  ('Stephen', 'King', 1947, NULL, 'Statunitense', '/images/king.jpg'),
  ('Mary', 'Shelley', 1797, 1851, 'Britannica', '/images/shelley.jpg'),
  ('Jane', 'Austen', 1775, 1817, 'Britannica', '/images/austen.jpg'),
  ('Nicholas', 'Sparks', 1965, NULL, 'Statunitense', '/images/sparks.jpg');

-- Inserimento autore_libri (tabella join)
-- Qui devi aggiornare libro_id e autore_id con quelli effettivi dopo l'inserimento degli autori e libri

-- Fantasy (Autori: Tolkien, Martin, Lewis, Rothfuss, Sanderson, Jordan)
INSERT INTO autore_libri (libro_id, autore_id) VALUES (1, 1); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (2, 2);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (3, 3);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (4, 4);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (5, 1);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (6, 5);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (7, 6);  

-- Thriller (Autore: Dan Brown, Gillian Flynn)
INSERT INTO autore_libri (libro_id, autore_id) VALUES (8, 7); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (9, 7);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (10, 7); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (11, 7); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (12, 7); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (13, 8); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (14, 8); 

-- Horror (Autori: Stephen King, Mary Shelley)
INSERT INTO autore_libri (libro_id, autore_id) VALUES (15, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (16, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (17, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (18, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (19, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (20, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (21, 10);

-- Romance (Autori: Jane Austen, Nicholas Sparks)
INSERT INTO autore_libri (libro_id, autore_id) VALUES (22, 11);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (23, 11);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (24, 11);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (25, 12);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (26, 12);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (27, 12);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (28, 12);





