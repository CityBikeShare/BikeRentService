--INSERT INTO Users (name, surname, username, password, email, region) VALUES ('Zoran', 'Jankovic', 'zoki123', 'jassemcar', 'zoki123@hotmail.com', 'Ljubljana'), ('Borut', 'Pahor', 's(a)tan666', 'satan', 'borut@pahor.si', 'Ljubljana'), ('Lojze', 'Slak', 'lojzeslak', 'cvicek', 'lojze.slak@zakon.je', 'Novo mesto')
--INSERT INTO Bikes (type, size, description, price, isAvailable, user_id) VALUES  ('Mountain bike', 'S', 'Bike for mountain riding', 35.0, TRUE, 1), ('Mountain bike', 'M', 'Bike for mountain riding', 40.0, TRUE, 1), ('Road bike', 'L', 'Bike designed to be ridden fast on smooth pavement', 50.0, TRUE, 2), ('Touring bike', 'M', 'Bike for use on self-supported long-distance riding', 40.0, TRUE, 3), ('Touring bike', 'L', 'Bike for use on self-supported long-distance riding', 45.0, TRUE, 3);
--INSERT INTO BikeRent ( bike_id, user_id, isDebtSettled) VALUES (2, 2, true), (2, 3, true), (1, 1, false)