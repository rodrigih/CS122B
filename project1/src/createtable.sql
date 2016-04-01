CREATE TABLE IF NOT EXISTS movies(
    id INTEGER NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL DEFAULT '',
    year INTEGER NOT NULL,
    director VARCHAR(100) NOT NULL DEFAULT '',
    banner_url VARCHAR(200) DEFAULT '',
    trailer_url VARCHAR(200) DEFAULT '',
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS stars(
    id INTEGER NOT NULL AUTO_INCREMENT ,
    first_name VARCHAR(50) NOT NULL DEFAULT '',
    last_name VARCHAR(50) NOT NULL DEFAULT '',
    dob DATE,
    photo_url VARCHAR(200) DEFAULT '',
    PRIMARY KEY (id)); 

CREATE TABLE IF NOT EXISTS stars_in_movies(
    star_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    FOREIGN KEY fk_star(star_id)
    REFERENCES stars(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY fk_movie(movie_id)
    REFERENCES movies(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS genres(
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL DEFAULT '',
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS genres_in_movies(
    genre_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    FOREIGN KEY fk_genre(genre_id)
    REFERENCES genres(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY fk_movie(movie_id)
    REFERENCES movies(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS customers(
    id INTEGER NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL DEFAULT '',
    last_name VARCHAR(50) NOT NULL DEFAULT '',
    cc_id VARCHAR(20) NOT NULL DEFAULT '',
    address VARCHAR(200) NOT NULL DEFAULT '',
    email VARCHAR(50) NOT NULL DEFAULT '',
    password VARCHAR(20) NOT NULL DEFAULT '',
    PRIMARY KEY (id),
    FOREIGN KEY fk_cc(cc_id)
    REFERENCES creditcards(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS sales(
    id INTEGER NOT NULL AUTO_INCREMENT,
    customer_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    sale_date DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_customer(customer_id)
    REFERENCES customers(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    FOREIGN KEY fk_movie(movie_id)
    REFERENCES movies(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS creditcards(
    id VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    expiration DATE NOT NULL,
    PRIMARY KEY (id));
