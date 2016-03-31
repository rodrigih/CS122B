CREATE database IF NOT EXISTS sampledb;

USE sampledb;

CREATE TABLE IF NOT EXISTS stars(
    id INTEGER PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    dob DATE,
    photo_url VARCHAR(200));

INSERT INTO stars VALUES(755011, 'Arnold', 'Schwarzeneggar', '1947/07/30', 'http://www.imdb.com/gallery/granitz/2028/Events/2028/ArnoldSchw_Grani_1252920_400.jpg?path=pgallery&path_key=Schwarzenegger,%20Arnold');

INSERT INTO stars VALUES(755017, 'Eddie', 'Murphy', '1961/04/03', 'http://www.imdb.com/gallery/granitz/2487/Events/2487/EddieMurph_Pimen_2724994_400.jpg?path=pgallery&path_key=Murphy,%20Eddie%20(I)');
