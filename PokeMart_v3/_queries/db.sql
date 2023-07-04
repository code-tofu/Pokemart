DROP DATABASE IF EXISTS pokemart;
CREATE DATABASE pokemart;
CREATE user 'testoak'@'localhost' identified by 'passoak5%';
GRANT ALL PRIVILEGES ON pokemart.* TO 'testoak'@'localhost';

SHOW DATABASES;
USE pokemart;
SHOW TABLES;