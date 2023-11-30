drop database if exists pokemon_catcher_db;
create database pokemon_catcher_db;
use pokemon_catcher_db;

CREATE TABLE trainers(
                      trainer_id INT PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      permission ENUM('ADMIN', 'USER') NOT NULL,
                      password VARCHAR(255) NOT NULL
                      
);


CREATE TABLE pokemon (
                       pokemon_id INT PRIMARY KEY,
                       pokemon_name VARCHAR(255) NOT NULL,
                       pokemon_type1 VARCHAR(255) NOT NULL,
                       pokemon_type2 VARCHAR(255)
);


CREATE TABLE balls (
                       ball_id INT PRIMARY KEY AUTO_INCREMENT,
                       ball_name VARCHAR(255) NOT NULL
);

CREATE TABLE trainer_pokemon (
                       trainer_id INT,
                       pokemon_id INT,
                       catch_status ENUM('CAUGHT', 'NOT CAUGHT'),
                       PRIMARY KEY(trainer_id, pokemon_id),
                       FOREIGN KEY (trainer_id) references trainers(trainer_id),
                       FOREIGN KEY (pokemon_id) REFERENCES pokemon(pokemon_id)
);

CREATE TABLE trainer_balls (
                       trainer_id INT,
                       ball_id INT,
                       foreign key (trainer_id) references trainers(trainer_id),
					   foreign key (ball_id) references balls(ball_id)
);

CREATE TABLE pokemon_balls (
                       pokemon_id INT,
                       ball_id INT,
                       foreign key (pokemon_id) references pokemon(pokemon_id),
					   foreign key (ball_id) references balls(ball_id)
);


#Users
INSERT INTO trainers(username, permission, password) VALUES('Smithy', 'ADMIN', '0810');
INSERT INTO trainers(username, permission, password) VALUES('Ash', 'USER', '0000');


#Pokemon
INSERT INTO pokemon(pokemon_id, pokemon_name, pokemon_type1, pokemon_type2) VALUES(1, 'Bulbasaur', 'Grass', 'Poison');
INSERT INTO pokemon(pokemon_id, pokemon_name, pokemon_type1) VALUES(25, 'Pikachu', 'Electric');

#Pokeballs
INSERT INTO balls(ball_name) VALUES('Poke Ball');
INSERT INTO balls(ball_name) VALUES('Great Ball');



select * from trainers;
select * from pokemon;
select * from balls;








