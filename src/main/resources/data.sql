INSERT INTO CATEGORY(id, name) VALUES (1, 'Eurogames');
INSERT INTO CATEGORY(id, name) VALUES (2, 'Ameritrash');
INSERT INTO CATEGORY(id, name) VALUES (3, 'Familiar');

INSERT INTO CLIENT(id, name) VALUES (1, 'Cliente 1');
INSERT INTO CLIENT(id, name) VALUES (2, 'Cliente 2');
INSERT INTO CLIENT(id, name) VALUES (3, 'Cliente 3');

INSERT INTO AUTHOR(id, name, nationality) VALUES (1, 'Alan R. Moon', 'US');
INSERT INTO AUTHOR(id, name, nationality) VALUES (2, 'Vital Lacerda', 'PT');
INSERT INTO AUTHOR(id, name, nationality) VALUES (3, 'Simone Luciani', 'IT');
INSERT INTO AUTHOR(id, name, nationality) VALUES (4, 'Perepau Llistosella', 'ES');
INSERT INTO AUTHOR(id, name, nationality) VALUES (5, 'Michael Kiesling', 'DE');
INSERT INTO AUTHOR(id, name, nationality) VALUES (6, 'Phil Walker-Harding', 'US');

INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (1, 'On Mars', '14', 1, 2);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (2, 'Aventureros al tren', '8', 3, 1);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (3, '1920: Wall Street', '12', 1, 4);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (4, 'Barrage', '14', 1, 3);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (5, 'Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (6, 'Azul', '8', 3, 5);

-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (1,1,1,'2022-02-10','2022-02-22');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (2,2,3,'2022-02-5','2022-02-22');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (3,3,1,'2022-02-1','2022-02-13');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (4,4,3,'2022-02-22','2022-02-22');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (5,5,1,'2022-02-22','2022-02-22');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (6,3,1,'2022-02-23','2022-02-26');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (7,5,3,'2022-03-02','2022-03-05');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (8,6,1,'2022-03-12','2022-03-22');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (9,1,3,'2023-03-07','2023-03-15');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (10,2,1,'2023-02-20','2023-02-22');
-- INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (11,4,3,'2023-02-11','2023-02-13');
INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (1,3,1,'2022-02-23','2022-02-26');
INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (2,5,3,'2022-03-02','2022-03-05');
INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (3,6,1,'2022-03-12','2022-03-22');
INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (4,1,3,'2023-03-07','2023-03-15');
INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (5,2,1,'2023-02-20','2023-02-22');
INSERT INTO LOAN(id,game_id,client_id,start_date,end_date) VALUES (6,4,3,'2023-02-11','2023-02-13');
