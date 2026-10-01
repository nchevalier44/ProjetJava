use projetjava;
DROP TABLE IF EXISTS activities;
DROP TABLE IF EXISTS activity_types;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    sport_favori VARCHAR(50),
    visibilite BOOLEAN NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE activity_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE activities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    type_id INT NOT NULL,
    datetime DATETIME NOT NULL,
    duration INT NOT NULL,
    user_id INT NOT NULL,
    
    CONSTRAINT fk_activity_type FOREIGN KEY (type_id) REFERENCES activity_types(id),
    CONSTRAINT fk_activity_user FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO activity_types (name) VALUES 
('Course à pied'),
('Natation'),
('Renforcement musculaire'),
('Vélo'),
('Marche'),
('Randonnée');