INSERT INTO USER (id, username, password, firstname, lastname, email, enabled, lastpasswordresetdate) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 'admin@admin.com', 1, '2018-01-03 19:50:00');
INSERT INTO USER (id, username, password, firstname, lastname, email, enabled, lastpasswordresetdate) VALUES (2, 'user', '$2a$10$xlowcDFQLzvooiAiwLMBK.vKDZkMNZawisa4bkawMxiZAJY0Q5IbW', 'user', 'user', 'enabled@user.com', 1, '2018-01-03 19:50:00');
INSERT INTO USER (id, username, password, firstname, lastname, email, enabled, lastpasswordresetdate) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', 0, '2018-01-03 19:50:00');
 
INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_ADMIN');
 
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (3, 1);

INSERT INTO category (id, name) VALUES (1, 'Laptopy');
INSERT INTO category (id, name) VALUES (2, 'Komputery');
INSERT INTO category (id, name) VALUES (3, 'Monitory');
INSERT INTO category (id, name) VALUES (4, 'Urzadzenia peryferyjne');
INSERT INTO category (id, name) VALUES (5, 'Podzespoly komputerowe');

INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (1, 'Gamingowe', 1);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (2, 'Biurowe', 1);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (3, 'Komputery Gamingowe', 2);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (4, 'Komputery Biurowe', 2);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (5, 'LED 22"', 3);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (6, 'LED 24"', 3);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (7, 'Drukarki', 4);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (8, 'Myszki', 4);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (9, 'Karty graficzne', 5);
INSERT INTO SUB_CATEGORY (id, name, category_id) VALUES (10, 'Dyski SSD', 5);

INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (1, 'ASUS ROG GL553VE', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'ASUS', '12', 'ROG GL553VE', 4400, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (2, 'DELL Latitude 5480', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'DELL', '6', 'Latitude 5480', 4800, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (3, 'MSI Apache Pro GE60', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'MSI', '24', 'Apache Pro GE60', 3400, 2);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (4, 'ASUS ROG GL553VE', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'ASUS', '12', 'ROG GL553VE', 4400, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (5, 'DELL Latitude 5480', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'DELL', '6', 'Latitude 5480', 4800, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (6, 'MSI Apache Pro GE60', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'MSI', '24', 'Apache Pro GE60', 3400, 2);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (7, 'ASUS ROG GL553VE', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'ASUS', '12', 'ROG GL553VE', 4400, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (8, 'DELL Latitude 5480', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'DELL', '6', 'Latitude 5480', 4800, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (9, 'MSI Apache Pro GE60', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'MSI', '24', 'Apache Pro GE60', 3400, 2);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (10, 'ASUS ROG GL553VE', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'ASUS', '12', 'ROG GL553VE', 4400, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (11, 'DELL Latitude 5480', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'DELL', '6', 'Latitude 5480', 4800, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (12, 'MSI Apache Pro GE60', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'MSI', '24', 'Apache Pro GE60', 3400, 2);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (13, 'ASUS ROG GL553VE', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'ASUS', '12', 'ROG GL553VE', 4400, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (14, 'DELL Latitude 5480', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'DELL', '6', 'Latitude 5480', 4800, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (15, 'MSI Apache Pro GE60', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'MSI', '24', 'Apache Pro GE60', 3400, 2);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (16, 'ASUS ROG GL553VE', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'ASUS', '12', 'ROG GL553VE', 4400, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (17, 'DELL Latitude 5480', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'DELL', '6', 'Latitude 5480', 4800, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (18, 'MSI Apache Pro GE60', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'MSI', '24', 'Apache Pro GE60', 3400, 2);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (19, 'ASUS ROG GL553VE', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'ASUS', '12', 'ROG GL553VE', 4400, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (20, 'DELL Latitude 5480', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'DELL', '6', 'Latitude 5480', 4800, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (21, 'MSI Apache Pro GE60', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'MSI', '24', 'Apache Pro GE60', 3400, 2);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (22, 'ASUS ROG GL553VE', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'ASUS', '12', 'ROG GL553VE', 4400, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (23, 'DELL Latitude 5480', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'DELL', '6', 'Latitude 5480', 4800, 1);
INSERT INTO PRODUCT (id, name, short_description, long_description, brand, in_stock, model, price, sub_category_id) VALUES (24, 'MSI Apache Pro GE60', 'krotki opis przedmiotu, jesli bedzie potrzebny', 'dlugi opis przedmiotu, jesli bedzie potrzebny', 'MSI', '24', 'Apache Pro GE60', 3400, 2);