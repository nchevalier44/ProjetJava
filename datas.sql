INSERT INTO users (username, name, surname, sport_favori, visibilite, password) VALUES
("martin1234", "Martin", "Dupont", "Course à pied", true, "martin1234"),
("sophie1234", "Sophie", "Durand", "Natation", false, "sophie1234"),
("lucas1234", "Lucas", "Lefevre", "Renforcement musculaire", true, "lucas1234"),
("emma1234", "Emma", "Moreau", "Vélo", false, "emma1234"),
("maxime1234", "Maxime", "Rousseau", "Marche", true, "maxime1234"),
("clara1234", "Clara", "Garnier", "Randonnée", false, "clara1234");

INSERT INTO activity_types (name) VALUES
('Course à pied'),
('Natation'),
('Renforcement musculaire'),
('Vélo'),
('Marche'),
('Randonnée');

INSERT INTO activities (title, description, type_id, datetime, duration, user_id) VALUES
('Footing matinal', 'Petite course dans le parc pour se réveiller.', 1, '2023-10-15 07:00:00', 2700, 1),
('Sortie longue', 'Préparation pour le semi-marathon, rythme constant.', 1, '2023-10-18 09:30:00', 6300, 1),
('Séance piscine', 'Entraînement crawl et brasse, 2km au total.', 2, '2023-10-16 18:00:00', 3600, 2),
('Nage libre', 'Séance de récupération tranquille.', 2, '2023-10-20 17:30:00', 2400, 2),
('Natation matinale', 'Quelques longueurs avant d''aller au travail.', 2, '2023-10-22 06:45:00', 2700, 2),
('Séance Haut du corps', 'Focus pectoraux, épaules et triceps.', 3, '2023-10-17 12:30:00', 3600, 3),
('Leg day', 'Entraînement intensif pour les jambes et fessiers.', 3, '2023-10-19 18:00:00', 4500, 3),
('Balade en forêt', 'Sortie VTT dans les bois avec pas mal de dénivelé.', 4, '2023-10-14 14:00:00', 7200, 4),
('Trajet vélotaf', 'Aller-retour au bureau à vélo.', 4, '2023-10-17 08:00:00', 3000, 4),
('Marche active', 'Tour du lac à bonne allure pour le cardio.', 5, '2023-10-15 10:00:00', 3600, 5),
('Promenade du soir', 'Marche digestive dans le quartier.', 5, '2023-10-21 20:00:00', 1800, 5),
('Rando en montagne', 'Ascension du sommet local, superbe vue à l''arrivée.', 6, '2023-10-14 08:00:00', 14400, 6),
('Randonnée forestière', 'Découverte de nouveaux sentiers avec un groupe d''amis.', 6, '2023-10-22 09:00:00', 10800, 6);