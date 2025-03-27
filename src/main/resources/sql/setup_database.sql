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
isReserved tinyint(1) DEFAULT 0,
FOREIGN KEY(wishlistID) REFERENCES wishlists(wishlistID)
);

