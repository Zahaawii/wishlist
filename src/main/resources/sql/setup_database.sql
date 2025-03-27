CREATE DATABASE wishlist;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `wishes`;
DROP TABLE IF EXISTS `wishlists`;


CREATE TABLE wishlists (
userID int NOT NULL,
wishlistName VARCHAR(250) NOT NULL,
wishlistID int PRIMARY KEY NOT NULL auto_increment
);

CREATE TABLE roles (
roleID int PRIMARY KEY NOT NULL auto_increment,
name VARCHAR(25) NOT NULL
);


CREATE TABLE users (
userID int PRIMARY KEY auto_increment,
name VARCHAR(50) NOT NULL,
username VARCHAR(20) NOT NULL,
password VARCHAR(50) NOT NULL,
roleID int NOT NULL,
FOREIGN KEY(roleID) REFERENCES roles(roleID)
);


CREATE TABLE wishes (
wishlistID INT NOT NULL,
wishID int PRIMARY KEY NOT NULL auto_increment,
name VARCHAR(50) NOT NULL,
description VARCHAR(250),
link VARCHAR(500),
isReserved boolean,
FOREIGN KEY(wishlistID) REFERENCES wishlists(wishlistID)
);

insert into wishlist.roles(name) values('admin');
insert into wishlist.roles(name) values('user');

INSERT INTO wishlist.users(username, password, roleID) VALUES ('hannibal', '1234', '1');
INSERT INTO wishlist.users(username, password, roleID) VALUES ('victor', '1234', '1');
INSERT INTO wishlist.users(username, password, roleID) VALUES ('simon', '1234', '1');
INSERT INTO wishlist.users(username, password, roleID) VALUES ('zahaa', '1234', '1');
INSERT INTO wishlist.users(username, password, roleID) VALUES ('bo-test', '4321', '2');
INSERT INTO wishlist.users(username, password, roleID) VALUES ('mette-test', '1234', '2');


INSERT INTO wishlist.wishlists(userID, wishlistName) values('1', 'Hannibal juleønsker');


INSERT INTO wishlist.wishes(name, description, wishlistID) VALUES ('padel bat', 'Et bat til at spille padel tennis', '1');
INSERT INTO wishlist.wishes(name, description, wishlistID) VALUES ('ferrari', 'En bil der kører suuuuper stærkt', '1');



