

INSERT INTO `library`.`authors` (`name`, `date_of_birth`) VALUES ('��� ��������', '1920-08-22');
INSERT INTO `library`.`authors` (`name`, `date_of_birth`) VALUES ('������ ������', '1903-06-25');
INSERT INTO `library`.`authors` (`name`, `date_of_birth`) VALUES ('������� ����� �������', '1952-06-21');
INSERT INTO `library`.`authors` (`name`, `date_of_birth`) VALUES ('������ ����������� ��������', '1891-05-15');
INSERT INTO `library`.`authors` (`name`, `date_of_birth`) VALUES ('���� ����� ������', '1898-06-22');

INSERT INTO `library`.`publishers` (`name`, `city`) VALUES ('������. �����', '������');
INSERT INTO `library`.`publishers` (`name`, `city`) VALUES ('��� Toyota', '��������');
INSERT INTO `library`.`publishers` (`name`, `city`) VALUES ('����', '���������');

INSERT INTO `library`.`genres` (`name`) VALUES ( '����1');
INSERT INTO `library`.`genres` (`name`) VALUES ( '����2');
INSERT INTO `library`.`genres` (`name`) VALUES ( '����3');

INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre_id`, `pages`, `description`,`picture_path`,`copies`) VALUES ('97889527', '��������', '1920-07-22', '1', '1', '1321', '�������� ','/Library/images/books/book.png','5');
INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre_id`, `pages`, `description`,`picture_path`,`copies`) VALUES ('21319527', '���� ����', '1980-05-15', '2', '2', '123', '�������� ','/Library/images/books/book.png','7');
INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre_id`, `pages`, `description`,`picture_path`,`copies`) VALUES ('41241244', '���� ����. �������� �� 2-� ����', '1929-08-30', '3', '2', '123', '�������� ','/Library/images/books/book.png','10');
INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre_id`, `pages`, `description`,`picture_path`,`copies`) VALUES ('63463468', '451� �� ����������', '1925-07-21', '2', '2', '422', '�������� 451� �� ����������','/Library/images/books/book.png','4');
INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre_id`, `pages`, `description`,`picture_path`,`copies`) VALUES ('69607076', '���� �� �����������', '1940-09-23', '2', '1', '1245', '�������� ','/Library/images/books/book.png5','9');
INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre_id`, `pages`, `description`,`picture_path`,`copies`) VALUES ('67963452', '��� ��������', '1970-04-12', '3', '2', '625', '�������� ','/Library/images/books/book.png','6');
INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre_id`, `pages`, `description`,`picture_path`,`copies`) VALUES ('42356347', '������������ ����', '1980-05-05', '1', '3', '563', '�������� ','/Library/images/books/book.png','7');
INSERT INTO `library`.`books` (`isbn`, `name`, `publication_date`, `publishers_id`, `genre_id`, `pages`, `description`,`picture_path`,`copies`) VALUES ('26234745', '1984', '1980-04-09', '1', '3', '523', '�������� ','/Library/images/books/book.png','7');

INSERT INTO `library`.`author_book` (`authors_id`, `books_isbn`) VALUES ('1', '63463468');
INSERT INTO `library`.`author_book` (`authors_id`, `books_isbn`) VALUES ('1', '69607076');
INSERT INTO `library`.`author_book` (`authors_id`, `books_isbn`) VALUES ('3', '97889527');
INSERT INTO `library`.`author_book` (`authors_id`, `books_isbn`) VALUES ('3', '21319527');
INSERT INTO `library`.`author_book` (`authors_id`, `books_isbn`) VALUES ('3', '41241244');
INSERT INTO `library`.`author_book` (`authors_id`, `books_isbn`) VALUES ('5', '67963452');
INSERT INTO `library`.`author_book` (`authors_id`, `books_isbn`) VALUES ('5', '42356347');
INSERT INTO `library`.`author_book` (`authors_id`, `books_isbn`) VALUES ('2', '26234745');

INSERT INTO `library`.`catalogs` (`name`) VALUES ('������� 1');
INSERT INTO `library`.`catalogs` (`name`) VALUES ('������� 2');

INSERT INTO `library`.`book_catalog` (`books_isbn`, `catalogs_id`) VALUES ('21319527', '1');
INSERT INTO `library`.`book_catalog` (`books_isbn`, `catalogs_id`) VALUES ('26234745', '2');
INSERT INTO `library`.`book_catalog` (`books_isbn`, `catalogs_id`) VALUES ('41241244', '1');
INSERT INTO `library`.`book_catalog` (`books_isbn`, `catalogs_id`) VALUES ('42356347', '1');
INSERT INTO `library`.`book_catalog` (`books_isbn`, `catalogs_id`) VALUES ('63463468', '1');
INSERT INTO `library`.`book_catalog` (`books_isbn`, `catalogs_id`) VALUES ('67963452', '2');
INSERT INTO `library`.`book_catalog` (`books_isbn`, `catalogs_id`) VALUES ('69607076', '2');
INSERT INTO `library`.`book_catalog` (`books_isbn`, `catalogs_id`) VALUES ('97889527', '2');

INSERT INTO `library`.`roles` (`name`) VALUES ('�������������');
INSERT INTO `library`.`roles` (`name`) VALUES ('������������');
INSERT INTO `library`.`roles` (`name`) VALUES ('��������');

INSERT INTO `library`.`users` (`roles_id`, `login`, `password_hash`, `first_name`, `last_name`, `is_male`, `email`, `picture_path`) VALUES ('3', 'log1', '$05$FlIKDUzS/s9L09YYS8jyKuxqv.DILKsRP/sWaEN9yggt8vaLkpsKG', 'name1', 'last_name1', '1', 'm1@mail.ru','/Library/images/books/user.png');
INSERT INTO `library`.`users` (`roles_id`, `login`, `password_hash`, `first_name`, `last_name`, `is_male`, `email`, `picture_path`) VALUES ('1', 'log2', '$05$FlIKDUzS/s9L09YYS8jyKuxqv.DILKsRP/sWaEN9yggt8vaLkpsKG', 'name2', 'last_name2', '1', 'm2@mail.ru','/Library/images/books/user.png');
INSERT INTO `library`.`users` (`roles_id`, `login`, `password_hash`, `first_name`, `last_name`, `is_male`, `email`, `picture_path`) VALUES ('2', 'log3', '$05$FlIKDUzS/s9L09YYS8jyKuxqv.DILKsRP/sWaEN9yggt8vaLkpsKG', 'name3', 'last_name3', '1', 'm3@mail.ru','/Library/images/books/user.png');
INSERT INTO `library`.`users` (`roles_id`, `login`, `password_hash`, `first_name`, `last_name`, `is_male`, `email`, `picture_path`) VALUES ('3', 'log4', '$05$FlIKDUzS/s9L09YYS8jyKuxqv.DILKsRP/sWaEN9yggt8vaLkpsKG', 'name4', 'last_name4', '0', 'm4@mail.ru','/Library/images/books/user.png');

INSERT INTO `library`.`loans` ( `users_id`,`books_isbn`,`aquire_date`,`due_date`) VALUES ( '1','97889527', '2017-06-11', '2017-06-12');
INSERT INTO `library`.`loans` ( `users_id`,`books_isbn`,`aquire_date`,`due_date`) VALUES ( '1','69607076', '2017-06-11', '2017-06-12');
INSERT INTO `library`.`loans` ( `users_id`,`books_isbn`,`aquire_date`,`due_date`) VALUES ( '3','97889527', '2017-06-11', '2017-06-12');
INSERT INTO `library`.`loans` ( `users_id`,`books_isbn`,`aquire_date`,`due_date`) VALUES ( '4','69607076', '2017-06-11', '2017-06-12');
INSERT INTO `library`.`loans` ( `users_id`,`books_isbn`,`aquire_date`,`due_date`) VALUES ( '2','41241244', '2017-06-11', '2017-06-12');
INSERT INTO `library`.`loans` ( `users_id`,`books_isbn`,`aquire_date`,`due_date`) VALUES ( '2','21319527', '2017-06-11', '2017-06-12');

