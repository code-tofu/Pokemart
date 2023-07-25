DROP TABLE IF EXISTS product_data;
CREATE TABLE product_data (
    product_id VARCHAR(10) NOT NULL,
    api_id INT,-- NOT NULL,
    name_id VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    cost DOUBLE(8,2) NOT NULL,
    description VARCHAR(500) NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (product_id)
);

DESC product_data;
-- +--------------+--------------+------+-----+---------+-------+
-- | Field        | Type         | Null | Key | Default | Extra |
-- +--------------+--------------+------+-----+---------+-------+
-- | product_id   | varchar(10)  | NO   | PRI | NULL    |       |
-- | api_id       | int          | NO   |     | NULL    |       |
-- | name_id      | varchar(50)  | NO   |     | NULL    |       |
-- | category     | varchar(50)  | YES  |     | NULL    |       |
-- | cost         | double(8,2)  | NO   |     | NULL    |       |
-- | description  | varchar(500) | NO   |     | NULL    |       |
-- | product_name | varchar(50)  | NO   |     | NULL    |       |
-- +--------------+--------------+------+-----+---------+-------+

-- note that BLOB holds up to 65,535 bytes and MEDIUMBLOB holds up to 16,777,215 bytes
