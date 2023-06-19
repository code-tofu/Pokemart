DROP TABLE IF EXISTS product_data;
CREATE TABLE product_data (
    product_id VARCHAR(8) NOT NULL,
    api_id INT NOT NULL,
    name_id VARCHAR(50) NOT NULL,
    category VARCHAR(50),
    cost DOUBLE(8,2) NOT NULL,
    details VARCHAR(500) NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (product_id)
);

DESC product_data;
-- +--------------+--------------+------+-----+---------+-------+
-- | Field        | Type         | Null | Key | Default | Extra |
-- +--------------+--------------+------+-----+---------+-------+
-- | product_id   | varchar(8)   | NO   |     | NULL    |       |
-- | api_id       | int          | NO   |     | NULL    |       |
-- | name_id      | varchar(50)  | NO   |     | NULL    |       |
-- | category     | varchar(50)  | YES  |     | NULL    |       |
-- | cost         | double(8,2)  | NO   |     | NULL    |       |
-- | details      | varchar(500) | NO   |     | NULL    |       |
-- | product_name | varchar(50)  | NO   |     | NULL    |       |
-- +--------------+--------------+------+-----+---------+-------+


-- note that BLOB holds up to 65,535 bytes and MEDIUMBLOB holds up to 16,777,215 bytes

    INSERT INTO product_data (product_id, api_id, name_id, category,cost,details,product_name) VALUES  (?, ?, ?, ?, ?, ?, ?)

SELECT product_id, api_id, name_id, category,cost,product_name FROM product_data;
SELECT details FROM product_data;

SELECT * FROM product_data WHERE api_id = ?;
SELECT * FROM product_data WHERE product_id = ?;
SELECT product_id FROM product_data LIMIT ? OFFSET ?;