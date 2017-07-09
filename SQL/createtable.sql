DROP DATABASE moviedb;
CREATE DATABASE moviedb;
USE moviedb;

CREATE TABLE  movies (
	id int NOT NULL AUTO_INCREMENT,
    title varchar(100) NOT NULL,
	year int NOT NULL,
    director varchar(100) NOT NULL,
    banner_url varchar(200) DEFAULT '',
    trailer_url varchar(200) DEFAULT '',
	PRIMARY KEY(id)
);

CREATE TABLE stars (
	id int NOT NULL AUTO_INCREMENT,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    dob date DEFAULT NULL,
    photo_url varchar(200) DEFAULT '',
    PRIMARY KEY(id)
);

CREATE TABLE stars_in_movies (
	star_id int NOT NULL,
    movie_id int NOT NULL,
    FOREIGN KEY(star_id) REFERENCES stars(id),
    FOREIGN KEY(movie_id) REFERENCES movies(id)
);

CREATE TABLE genres (
	id int NOT NULL AUTO_INCREMENT,
    name varchar(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE genres_in_movies (
	genre_id int NOT NULL,
    movie_id int NOT NULL,
    FOREIGN KEY(genre_id) REFERENCES genres(id),
    FOREIGN KEY(movie_id) REFERENCES movies(id)
);

CREATE TABLE creditcards (
	id varchar(20) NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    expiration date NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE customers (
	id int NOT NULL AUTO_INCREMENT,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    cc_id varchar(20) NOT NULL,
    address varchar(200) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(20) NOT NULL,
    FOREIGN KEY(cc_id) REFERENCES creditcards(id),
    PRIMARY KEY(id)
);

CREATE TABLE sales (
	id int NOT NULL AUTO_INCREMENT,
    customer_id int NOT NULL,
    movie_id int NOT NULL,
    sales_date date NOT NULL,
	FOREIGN KEY(customer_id) REFERENCES customers(id),
    FOREIGN KEY(movie_id) REFERENCES movies(id),
    PRIMARY KEY(id)
);


DROP PROCEDURE IF EXISTS add_movie;

DELIMITER $$
CREATE PROCEDURE add_movie(
        IN movie_title VARCHAR(100),
        IN movie_year INTEGER,
        IN movie_director VARCHAR(100),
        IN movie_banner VARCHAR(200),
        IN movie_trailer VARCHAR(200),
        IN star_first_name VARCHAR(50),
        IN star_last_name VARCHAR(50),
        IN genre VARCHAR(32),
        OUT movie_ID INTEGER,
        OUT star_ID INTEGER,
        OUT genre_ID INTEGER
)

BEGIN
        IF (SELECT EXISTS(SELECT * FROM movies WHERE movies.title = movie_title)) = 0 THEN
                INSERT INTO movies(title, year, director, banner_url, trailer_url) VALUES (movie_title, movie_year, movie_director, movie_banner, movie_trailer);
        END IF;

        IF (SELECT EXISTS(SELECT * FROM stars WHERE stars.first_name = star_first_name and stars.last_name = star_last_name)) = 0 THEN
                INSERT INTO stars(first_name, last_name) VALUES (star_first_name, star_last_name);
        END IF;

        IF (SELECT EXISTS(SELECT * FROM genres WHERE genres.name = genre)) = 0 THEN
                INSERT INTO genres(name) VALUES (genre);
        END IF;

        SET movie_ID = (SELECT id FROM movies WHERE movies.title = movie_title);
        SET star_ID = (SELECT id FROM stars WHERE stars.first_name = star_first_name and stars.last_name = star_last_name);
        SET genre_ID = (SELECT id FROM genres WHERE genres.name = genre);

        IF (SELECT EXISTS(SELECT * FROM stars_in_movies as sm WHERE sm.star_id = star_ID and sm.movie_id = movie_ID)) = 0 THEN
                INSERT INTO stars_in_movies VALUES(star_ID, movie_ID);
        END IF;

        IF (SELECT EXISTS(SELECT * FROM genres_in_movies as gm WHERE gm.genre_id = genre and gm.movie_id = movie_ID)) = 0 THEN
                INSERT INTO genres_in_movies VALUES(genre_ID, movie_ID);
        END IF;
		
END $$

DELIMITER ;

