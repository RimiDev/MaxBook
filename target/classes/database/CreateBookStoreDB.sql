DROP DATABASE IF EXISTS BookStore_DB;
CREATE DATABASE BookStore_DB;

USE BookStore_DB;

DROP USER IF EXISTS bookstore_user@localhost;

CREATE USER bookstore_user@'localhost' IDENTIFIED BY 'bookstore_user';

GRANT ALL ON BookStore_DB.* TO bookstore_user@'localhost';
FLUSH PRIVILEGES;
