insert into wishlist.roles(roleName) values('admin');
insert into wishlist.roles(roleName) values('user');

INSERT INTO wishlist.users(name, username, password, roleID) VALUES ('hannibal','hannibal', '1234', '1');
INSERT INTO wishlist.users(name, username, password, roleID) VALUES ('Victor','victor', '1234', '1');
INSERT INTO wishlist.users(name, username, password, roleID) VALUES ('simon','simon', '1234', '1');
INSERT INTO wishlist.users(name, username, password, roleID) VALUES ('zahaa','zahaa', '1234', '1');
INSERT INTO wishlist.users(name, username, password, roleID) VALUES ('bo-test','bo-test', '4321', '2');
INSERT INTO wishlist.users(name, username, password, roleID) VALUES ('mette','mette-test', '1234', '2');


INSERT INTO wishlist.wishlists(userID, wishlistName) values('1', 'Hannibal juleønsker');


INSERT INTO wishlist.wishes(wishName, description, wishlistID, price, quantity) VALUES ('padel bat', 'Et bat til at spille padel tennis', '1', 1000, 1);
INSERT INTO wishlist.wishes(wishName, description, wishlistID, price, quantity) VALUES ('ferrari', 'En bil der kører suuuuper stærkt', '1', 1000000, 2);


