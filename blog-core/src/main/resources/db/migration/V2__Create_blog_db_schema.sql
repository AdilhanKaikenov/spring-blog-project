DROP TABLE IF EXISTS blog_category_assignment;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS blog;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS unique_id;

CREATE TABLE `user` (
  id INT NOT NULL AUTO_INCREMENT UNIQUE,
  `login` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `email` VARCHAR(40) NOT NULL,
  `date_of_registration` TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE `unique_id` (
  id int NOT NULL AUTO_INCREMENT UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE `roles` (
  id int NOT NULL AUTO_INCREMENT UNIQUE,
  PRIMARY KEY (id),
  `name` VARCHAR(20) NOT NULL
);

CREATE TABLE `user_roles` (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  CONSTRAINT user_roles_assignment_id_pk PRIMARY KEY (user_id, role_id)
);

CREATE TABLE `blog` (
  id int NOT NULL AUTO_INCREMENT UNIQUE,
  `title` VARCHAR(30) NOT NULL,
  `content` VARCHAR(300) NOT NULL,
  `user_id` INT NOT NULL,
  `publication_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE `category` (
  id int NOT NULL AUTO_INCREMENT UNIQUE,
  `genre` VARCHAR(30) NOT NULL,
  `added_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE `blog_category_assignment` (
  `blog_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `date` TIMESTAMP NOT NULL,
  CONSTRAINT blog_category_assignment_id_pk PRIMARY KEY (blog_id, category_id)
);

CREATE TABLE `comment` (
  id int NOT NULL AUTO_INCREMENT UNIQUE,
  `blog_id` INT,
  `category_id` INT,
  `parent_comment_id` INT,
  `user_id` INT NOT NULL,
  `text` VARCHAR(300) NOT NULL,
  `comment_date` TIMESTAMP NOT NULL,
  `comment_type` VARCHAR(5) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE `notification` (
  id int NOT NULL AUTO_INCREMENT UNIQUE,
  `user_id` INT NOT NULL,
  `blog_id` INT NOT NULL,
  `date` TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE `blog` ADD CONSTRAINT `blog_fk0` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);
ALTER TABLE `blog` ADD CONSTRAINT `blog_fk1` FOREIGN KEY (`id`) REFERENCES `unique_id`(`id`);
ALTER TABLE `category` ADD CONSTRAINT `category_fk0` FOREIGN KEY (`id`) REFERENCES `unique_id`(`id`);
ALTER TABLE `blog_category_assignment` ADD CONSTRAINT `blog_category_assignment_fk0` FOREIGN KEY (`blog_id`) REFERENCES `blog`(`id`);
ALTER TABLE `blog_category_assignment` ADD CONSTRAINT `blog_category_assignment_fk1` FOREIGN KEY (`category_id`) REFERENCES `category`(`id`);
ALTER TABLE `user_roles` ADD CONSTRAINT `user_roles_fk0` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);
ALTER TABLE `user_roles` ADD CONSTRAINT `user_roles_fk1` FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`);
ALTER TABLE `comment` ADD CONSTRAINT `comment_fk0` FOREIGN KEY (`blog_id`) REFERENCES `blog`(`id`);
ALTER TABLE `comment` ADD CONSTRAINT `comment_fk1` FOREIGN KEY (`category_id`) REFERENCES `category`(`id`);
ALTER TABLE `comment` ADD CONSTRAINT `comment_fk2` FOREIGN KEY (`parent_comment_id`) REFERENCES `comment`(`id`);
ALTER TABLE `comment` ADD CONSTRAINT `comment_fk3` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);