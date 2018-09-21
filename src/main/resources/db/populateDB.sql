DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USERS (name, email, password) VALUES
  ('User1', 'user@mail.ru', '{noop}qwert'),                   --100000
  ('User2',	'user2@mail.ru',	'{noop}asdfg'),                 --100001
  ('Admin', 'admin@gmail.com', '{noop}lkjh33498yhnbyfgi563'); --100002

INSERT INTO USER_ROLES (USER_ID, ROLE) VALUES
  (100000, 'ROLE_USER'),
  (100001, 'ROLE_USER'),
  (100002, 'ROLE_USER'),
  (100002, 'ROLE_ADMIN');

INSERT INTO RESTAURANTS (name) VALUES
  ('Братья пекари'),        --100003
  ('Столовая №1'),         --100004
  ('Mcdonalds');            --100005

INSERT INTO DISHES (RESTAURANT_ID, NAME, PRICE) VALUES
  (100003,	'Булочки с повидлом',	50),    --100006
  (100003,	'Самса с курицей',	150),     --100007
  (100003,	'Салат от шеф-повара',	200), --100008
  (100003,	'Чай',	90),                  --100009
  (100004,	'Бефстроганов',	230),         --100010
  (100004,	'Рататуй',	195),             --100011
  (100004,	'Колбасные обрезки',	500),   --100012
  (100004,	'Компот',	50),                --100013
  (100004,	'Макарошки',	300),           --100015
  (100005,	'Coca Cola',	150),           --100016
  (100005,	'BigMag',	300),               --100017
  (100005,	'Free',	120);                 --100018

INSERT INTO VOTES (USER_ID, RESTAURANT_ID, DATE, TIME) VALUES
  (100000,	100003,	'2018-07-20',	'10:00:00'),  --100019
  (100001,	100004,	'2018-07-20',	'11:00:00'),  --100020
  (100002,	100005,	'2018-07-20',	'12:00:00'),  --100021
  (100000,	100003,	'2018-07-21',	'10:00:00'),  --100022
  (100001,	100004,	'2018-07-21',	'11:00:00'),  --100023
  (100002,	100005,	'2018-07-21',	'12:00:00'),  --100024
  (100000,	100003,	'2018-07-22',	'10:00:00'),  --100025
  (100001,	100004,	'2018-07-22',	'11:00:00'),  --100026
  (100002,	100005,	'2018-07-22',	'12:00:00');  --100027
