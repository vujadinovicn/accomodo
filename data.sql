INSERT INTO destination ("name", postal_code) values ('Novi sad', 21000);

INSERT INTO "location" (lat, lng, address, destination_id) values (12.345, 67.890, 'Zmaj ognjena vuka', 1);

INSERT INTO "users" (email, password, name, lastname, role, is_blocked) VALUES ('john.doe@example.com', 'password123', 'John', 'Doe', 2, false);
INSERT INTO "traveler"  (id, is_iressponsible , is_malicious , age_group , level) VALUES (1, false, false, 1, 1);

INSERT INTO "users" (email, password, name, lastname, role, is_blocked) VALUES ('pohn.doe@example.com', 'password123', 'Pohn', 'Doe', 1, false);
INSERT INTO "owner"  (id, is_irresponsible ) VALUES (2, false);

INSERT INTO listing (title, description, price, rating, owner_id, location_id) values ('Lux apartments', 'Prelepo', 100.00, 4.5, 2, 2);

DELETE FROM public."owner" WHERE id=1;
DELETE FROM public."listing" WHERE id=2;
DELETE FROM public."traveler_favorite_listings" WHERE traveler_id=1;
