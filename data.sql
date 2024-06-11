INSERT INTO destination ("name", postal_code) values ('Novi sad', 21000);

INSERT INTO "location" (lat, lng, address, destination_id) values (12.345, 67.890, 'Zmaj ognjena vuka', 1);

INSERT INTO "users" (email, password, name, lastname, role, is_blocked) VALUES ('john.doe@example.com', 'password123', 'John', 'Doe', 2, false);
INSERT INTO "traveler"  (id, is_iressponsible , is_malicious , age_group , level) VALUES (1, false, false, 1, 1);

INSERT INTO "users" (email, password, name, lastname, role, is_blocked) VALUES ('pohn.doe@example.com', 'password123', 'Pohn', 'Doe', 1, false);
INSERT INTO "owner"  (id, is_irresponsible ) VALUES (2, false);

INSERT INTO listing (title, description, price, rating, owner_id, location_id) values ('Lux apartments', 'Prelepo', 100.00, 4.5, 2, 2);
INSERT INTO listing (title, description, price, rating, owner_id, location_id) values ('Beach home', 'Dvospratna kuca na obali Dunava.', 150.00, 5, 2, 2);

DELETE FROM public."owner" WHERE id=1;
DELETE FROM public."listing" WHERE id=2;
DELETE FROM public."traveler_favorite_listings" WHERE traveler_id=1;

DELETE FROM public.listing_tags WHERE listing_id=3 AND tags_id=1;
DELETE FROM public.tag WHERE id=2;

INSERT INTO public.tag
("name")
VALUES('sauna');

INSERT INTO public.tag
("name")
VALUES('pool');

insert into public.listing_tags 
("listing_id", "tags_id")
values (3, 1);

insert into public.booking ("created_at", "end_date", "is_reservation", "start_date", "status", "listing_id", "traveler_id") values ('2024-05-16 15:21:48.500', '2024-06-16 15:21:48.500', false, '2024-06-06 15:21:48.500', 1, 3, 3);
insert into public.booking ("created_at", "end_date", "is_reservation", "start_date", "status", "listing_id", "traveler_id") values ('2024-06-8 15:21:48.500', '2024-07-16 15:21:48.500', false, '2024-07-06 15:21:48.500', 1, 3, 3);
insert into public.booking ("created_at", "end_date", "is_reservation", "start_date", "status", "listing_id", "traveler_id") values ('2024-06-8 15:21:48.500', '2024-06-09 15:21:48.500', false, '2024-06-01 15:21:48.500', 1, 4, 3);
insert into public.booking ("created_at", "end_date", "is_reservation", "start_date", "status", "listing_id", "traveler_id") values ('2024-06-8 15:21:48.500', '2025-06-09 15:21:48.500', false, '2025-06-01 15:21:48.500', 1, 4, 3);
