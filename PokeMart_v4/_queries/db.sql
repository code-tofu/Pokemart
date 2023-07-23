DROP DATABASE IF EXISTS pokemart;
CREATE DATABASE pokemart;
CREATE user 'testoak'@'localhost' identified by 'passoak5%';
GRANT ALL PRIVILEGES ON pokemart.* TO 'testoak'@'localhost';

SHOW DATABASES;
USE pokemart;
SHOW TABLES;

-- +--------------------+
-- | Tables_in_pokemart |
-- +--------------------+
-- | inventory          |
-- | product_attributes |
-- | product_data       |
-- | stores             |
-- | user_details       |
-- | user_profiles      |
-- | user_telegram      |
-- +--------------------+
-- 7 rows in set (0.00 sec)