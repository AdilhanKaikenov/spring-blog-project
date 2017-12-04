INSERT INTO user(id, login, password, email, date_of_registration) VALUES (1, 'User1', 'Password', 'adilhan_kai@mail.ru','2017-10-31 16:40:31');
INSERT INTO user(id, login, password, email, date_of_registration) VALUES (2, 'User2', 'Password', 'adilhan_kai@mail.ru','2017-10-31 16:40:31');

INSERT INTO unique_id(id) VALUES (1);
INSERT INTO unique_id(id) VALUES (2);
INSERT INTO unique_id(id) VALUES (3);

INSERT INTO blog (id, title, content, user_id, publication_date)
VALUES (1, 'Blog#1', 'Content Text', 1, '2017-10-31 16:40:31');

INSERT INTO category(id, genre, added_date) VALUES (2, 'News', '2017-10-31 16:40:31');
INSERT INTO category(id, genre, added_date) VALUES (3, 'Advertisement', '2017-10-31 16:40:31');

INSERT INTO blog_category_assignment(blog_id, category_id, date) VALUES (1, 2, '2017-10-31 16:40:31');