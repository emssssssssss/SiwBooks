
INSERT INTO utente (email, username, password, ruolo)
VALUES (
  'admin@email.com',
  'admin',
  '$2a$10$dKcrLuW.qaTAKuI.5F/k..2UfPjQ/.VGaxL4qHKKJNj/GJv3EKgUW', -- "admin123"
  'ADMIN'
);




-- 7 libri Fantasy (ID 1-7)
INSERT INTO libro (id, titolo, descrizione, genere, anno_pubblicazione) VALUES
  (1,  'Lo Hobbit',                              'Il piccolo e improbabile eroe Bilbo Baggins in un’avventura straordinaria.',                     'Fantasy', 1937),
  (2,  'A Game of Thrones',                      'Intrighi e lotte per il Trono di Spade in un continente diviso.',                                  'Fantasy', 1996),
  (3,  'Le Cronache di Narnia: Il Leone...',     'I fratelli Pevensie scoprono un mondo magico oltre l’armadio.',                                   'Fantasy', 1950),
  (4,  'Il Nome del Vento',                      'La storia di Kvothe, mago e menestrello, narrata in prima persona.',                              'Fantasy', 2007),
  (5, 'Il Signore degli Anelli',                 'Un epico viaggio nella Terra di Mezzo.', 'Fantasy', 1954),
  (6,  'Mistborn: L’Ultimo Impero',              'La magia del metallo e la rivolta contro un dio tiranno.',                                       'Fantasy', 2006),
  (7,  'La Ruota del Tempo: L''Occhio...',      'La missione di Rand al’Ultimo Villaggio per salvare il mondo dal male.',                         'Fantasy', 1990);

-- Copertine Fantasy
INSERT INTO libro_immagini (libro_id, immagini) VALUES
  (1,  '/images/hobbit.jpg'),
  (2,  '/images/got.jpg'),
  (3,  '/images/narnia.jpg'),
  (4,  '/images/nameofwind.jpg'),
  (5,  '/images/lotr.jpg'),
  (6,  '/images/mistborn.jpg'),
  (7,  '/images/wheeloftime.jpg');

-- 7 libri Thriller (ID 8-14)
INSERT INTO libro (id, titolo, descrizione, genere, anno_pubblicazione) VALUES
(8, 'Il Codice Da Vinci', 'Un thriller ricco di misteri e simboli nascosti.', 'Thriller', 2003),
(9, 'Angeli e Demoni', 'Robert Langdon contro gli Illuminati in Vaticano.', 'Thriller', 2000),
(10, 'Inferno', 'Langdon si risveglia a Firenze senza memoria e in fuga.', 'Thriller', 2013),
(11, 'Il Simbolo Perduto', 'Una missione per salvare Washington dai segreti massonici.', 'Thriller', 2009),
(12, 'Origine', 'Un’innovazione scientifica che potrebbe cambiare la religione.', 'Thriller', 2017),
(13, 'Gone Girl', 'Un matrimonio perfetto nasconde bugie letali.', 'Thriller', 2012),
(14, 'Dark Places', 'Una donna cerca la verità su un massacro in famiglia.', 'Thriller', 2009);


-- Immagini Thriller
INSERT INTO libro_immagini (libro_id, immagini) VALUES
(8, '/images/daVinci.jpg'),
(9, '/images/angelsdemons.jpg'),
(10, '/images/inferno.jpg'),
(11, '/images/simboloperduto.jpg'),
(12, '/images/origine.jpg'),
(13, '/images/gonegirl.jpg'),
(14, '/images/darkplaces.jpg');

-- Libri Horror (ID 8–14)
INSERT INTO libro (id, titolo, descrizione, genere, anno_pubblicazione) VALUES
(15, 'It', 'Una forza malvagia prende la forma di un clown e terrorizza i bambini.', 'Horror', 1986),
(16, 'Shining', 'Un hotel isolato spinge un padre alla follia.', 'Horror', 1977),
(17, 'Pet Sematary', 'Un cimitero segreto restituisce la vita... ma non senza conseguenze.', 'Horror', 1983),
(18, 'Misery', 'Uno scrittore è prigioniero della sua fan più ossessiva.', 'Horror', 1987),
(19, 'Carrie', 'Una ragazza con poteri telecinetici si vendica dei suoi aguzzini.', 'Horror', 1974),
(20, 'Doctor Sleep', 'Il seguito di Shining, Danny Torrance contro entità oscure.', 'Horror', 2013),
(21, 'Frankenstein', 'Uno scienziato dà vita a una creatura con effetti devastanti.', 'Horror', 1818);

-- Immagini Horror
INSERT INTO libro_immagini (libro_id, immagini) VALUES
(15, '/images/it.jpg'),
(16, '/images/shining.jpg'),
(17, '/images/petsematary.jpg'),
(18, '/images/misery.jpg'),
(19, '/images/carrie.jpg'),
(20, '/images/doctorsleep.jpg'),
(21, '/images/frankenstein.jpg');


-- Libri Romance (ID 15–21)
INSERT INTO libro (id, titolo, descrizione, genere, anno_pubblicazione) VALUES
(22, 'Orgoglio e Pregiudizio', 'Elizabeth Bennet e Mr. Darcy si sfidano a colpi di intelletto e sentimento.', 'Romance', 1813),
(23, 'Ragione e Sentimento', 'Due sorelle affrontano amori e inganni in epoca georgiana.', 'Romance', 1811),
(24, 'Emma', 'Una giovane donna si diletta nel fare da cupido con esiti comici.', 'Romance', 1815),
(25, 'Le pagine della nostra vita', 'Un amore che supera tempo e memoria.', 'Romance', 1996),
(26, 'Come un uragano', 'Una storia intensa tra due anime alla deriva.', 'Romance', 1998),
(27, 'I passi dell’amore', 'Un amore giovane e tragico che cambia una vita.', 'Romance', 1999),
(28, 'Il meglio di me', 'Due ex amanti si rincontrano anni dopo per affrontare il passato.', 'Romance', 2011);



-- Immagini Romance
INSERT INTO libro_immagini (libro_id, immagini) VALUES
(22, '/images/pride.jpg'),
(23, '/images/sense.jpg'),
(24, '/images/emma.jpg'),
(25, '/images/notebook.jpg'),
(26, '/images/hurricane.jpg'),
(27, '/images/walk.jpg'),
(28, '/images/bestofme.jpg');


INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, foto_url) VALUES
(1, 'J.R.R.', 'Tolkien', 1892, 1973, 'Britannica', '/images/tolkien.jpg'),
(2, 'George R.R.', 'Martin', 1948, NULL, 'Statunitense', '/images/martin.jpg'),
(3, 'C.S.', 'Lewis', 1898, 1963, 'Britannica', '/images/lewis.jpg'),
(4, 'Patrick', 'Rothfuss', 1973, NULL, 'Statunitense', '/images/rothfuss.jpg'),
(5, 'Brandon', 'Sanderson', 1975, NULL, 'Statunitense', '/images/sanderson.jpg'),
(6, 'Robert', 'Jordan', 1948, 2007, 'Statunitense', '/images/jordan.jpg');


INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, foto_url) VALUES
(7, 'Dan', 'Brown', 1964, NULL, 'Statunitense', '/images/brown.jpg'),
(8, 'Gillian', 'Flynn', 1971, NULL, 'Statunitense', '/images/flynn.jpg');



INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, foto_url) VALUES
(9, 'Stephen', 'King', 1947, NULL, 'Statunitense', '/images/king.jpg'),
(10, 'Mary', 'Shelley', 1797, 1851, 'Britannica', '/images/shelley.jpg');



INSERT INTO autore (id, nome, cognome, data_nascita, data_morte, nazionalita, foto_url) VALUES
(11, 'Jane', 'Austen', 1775, 1817, 'Britannica', '/images/austen.jpg'),
(12, 'Nicholas', 'Sparks', 1965, NULL, 'Statunitense', '/images/sparks.jpg');



-- Fantasy (Autori: Tolkien=1, Martin=2, Lewis=3, Rothfuss=4, Sanderson=5, Jordan=6)
INSERT INTO autore_libri (libro_id, autore_id) VALUES (1, 1); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (2, 2);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (3, 3);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (4, 4);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (5, 1);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (6, 5);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (7, 6);  

-- Thriller (Autore: Dan Brown=7, Gillian Flynn=8)
INSERT INTO autore_libri (libro_id, autore_id) VALUES (8, 7); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (9, 7);  
INSERT INTO autore_libri (libro_id, autore_id) VALUES (10, 7); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (11, 7); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (12, 7); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (13, 8); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (14, 8); 

-- Horror (Autori: Stephen King=9, Mary Shelley=10)
INSERT INTO autore_libri (libro_id, autore_id) VALUES (15, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (16, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (17, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (18, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (19, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (20, 9); 
INSERT INTO autore_libri (libro_id, autore_id) VALUES (21, 10);

-- Romance (Autori: Jane Austen=11, Nicholas Sparks=12)
INSERT INTO autore_libri (libro_id, autore_id) VALUES (22, 11);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (23, 11);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (24, 11);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (25, 12);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (26, 12);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (27, 12);
INSERT INTO autore_libri (libro_id, autore_id) VALUES (28, 12);