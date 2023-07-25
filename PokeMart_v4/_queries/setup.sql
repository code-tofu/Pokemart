DROP DATABASE IF EXISTS pokemart;
CREATE DATABASE pokemart;
CREATE user 'testoak'@'localhost' identified by 'passoak5%';
GRANT ALL PRIVILEGES ON pokemart.* TO 'testoak'@'localhost';

SHOW DATABASES;
USE pokemart;

DROP TABLE IF EXISTS product_data;
CREATE TABLE product_data (
    product_id VARCHAR(10) NOT NULL,
    api_id INT,
    name_id VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    cost DOUBLE(8,2) NOT NULL,
    description VARCHAR(500) NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (product_id)
);

DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory (
    product_id VARCHAR(10) NOT NULL UNIQUE,
    stock INT NOT NULL,
    discount DOUBLE(8,2),
    comments VARCHAR(500),
    FOREIGN KEY (product_id) REFERENCES product_data(product_id)
);

DROP TABLE IF EXISTS user_details;
CREATE TABLE user_details (
    user_id VARCHAR(10) NOT NULL, 
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    role ENUM('ROLE_DEVELOPER','ROLE_ADMINISTRATOR','ROLE_SELLER','ROLE_CUSTOMER'),
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS user_profiles;
CREATE TABLE user_profiles (
    user_id VARCHAR(10) UNIQUE,
    customer_name VARCHAR(50),
    customer_email VARCHAR(50) UNIQUE,
    customer_phone VARCHAR(15),
    shipping_address VARCHAR(255),
    birthdate DATE,
    gender VARCHAR(1),
    member_level ENUM('BRONZE','SILVER','GOLD'),
    member_since DATE,
    FOREIGN KEY (user_id) REFERENCES user_details(user_id)
);


DROP TABLE IF EXISTS product_attributes;
CREATE TABLE product_attributes (
    product_id VARCHAR(10) NOT NULL,
    attribute VARCHAR(50) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product_data(product_id)
);


DROP TABLE IF EXISTS stores;
CREATE TABLE stores (
    store_id int NOT NULL AUTO_INCREMENT,
    store_address VARCHAR(500) NOT NULL,
    store_name VARCHAR(128) NOT NULL,
    store_phone VARCHAR(16) NOT NULL,
    store_lat DOUBLE(18,16) NOT NULL,
    store_long DOUBLE(18,15) NOT NULL,
    PRIMARY KEY (store_id)
);

DROP TABLE IF EXISTS user_telegram;
CREATE TABLE user_telegram (
    user_id VARCHAR(10) NOT NULL,
    telegram_id BIGINT UNSIGNED UNIQUE,
    authenticated BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES user_details(user_id)
);

INSERT INTO user_details (user_id, username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked,role)
VALUES ('u00000000','engineer1', '$2a$10$6qT8MuYVRldYQ9ik.eqj8eRrK2Zm/q8XUDnSeM5VCoMmQSCOPscG2', TRUE, TRUE,TRUE,TRUE,'ROLE_DEVELOPER');

INSERT INTO user_profiles (user_id,customer_name,customer_email,customer_phone,shipping_address,birthdate,gender,member_level,member_since) VALUES ('u00000000','developer','pokemart.tofucode@gmail.com','00000000','20 Clementi Ave 1, Singapore 129957', '1990-06-01','M','GOLD','2023-06-01');

INSERT INTO user_details (user_id, username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked,role)
VALUES ('u00000001','admin1', '$2a$10$dznwdbdQKJopzKbjafph.u33mt6UbzoUaBzFNWIAt5/XMtODJ1nTG', TRUE, TRUE,TRUE,TRUE,'ROLE_ADMINISTRATOR');

INSERT INTO user_profiles (user_id,customer_name,customer_email,customer_phone,shipping_address,birthdate,gender,member_level,member_since) VALUES ('u00000001','administrator','pokemail@pokemail.com','00000000','20 Clementi Ave 1, Singapore 129957', '1990-06-02','M','GOLD','2023-06-02');

INSERT INTO user_details (user_id, username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked,role)
VALUES ('u00000002','seller_1','$2a$10$qkA03b62.6egE.2pgSVyDe7fNHdWdgah9pWs4lqfZyH4iIFeLKs/q', TRUE, TRUE,TRUE,TRUE, 'ROLE_SELLER');

INSERT INTO user_profiles (user_id,customer_name,customer_email,customer_phone,shipping_address,birthdate,gender,member_level,member_since) VALUES ('u00000002','seller','pokemart@pokemail.com','00000000','20 Clementi Ave 1, Singapore 129957', '1990-06-02','M','GOLD','2023-06-02');

SHOW TABLES;



