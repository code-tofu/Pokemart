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

desc stores;
-- +---------------+---------------+------+-----+---------+----------------+
-- | Field         | Type          | Null | Key | Default | Extra          |
-- +---------------+---------------+------+-----+---------+----------------+
-- | store_id      | int           | NO   | PRI | NULL    | auto_increment |
-- | store_address | varchar(500)  | NO   |     | NULL    |                |
-- | store_name    | varchar(128)  | NO   |     | NULL    |                |
-- | store_phone   | varchar(16)   | NO   |     | NULL    |                |
-- | store_lat     | double(18,16) | NO   |     | NULL    |                |
-- | store_long    | double(18,15) | NO   |     | NULL    |                |
-- +---------------+---------------+------+-----+---------+----------------+
