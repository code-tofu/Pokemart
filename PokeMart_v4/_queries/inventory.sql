DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory (
    product_id VARCHAR(10) NOT NULL UNIQUE,
    stock INT NOT NULL,
    discount DOUBLE(8,2),
    comments VARCHAR(500),
    FOREIGN KEY (product_id) REFERENCES product_data(product_id)
);

desc inventory;
-- +------------+--------------+------+-----+---------+-------+
-- | Field      | Type         | Null | Key | Default | Extra |
-- +------------+--------------+------+-----+---------+-------+
-- | product_id | varchar(10)  | NO   | PRI | NULL    |       |
-- | stock      | int          | NO   |     | NULL    |       |
-- | discount   | double(8,2)  | YES  |     | NULL    |       |
-- | comments   | varchar(500) | YES  |     | NULL    |       |
-- +------------+--------------+------+-----+---------+-------+


-- | Warning | 1681 | Specifying number of digits for floating point data types is deprecated and will be removed in a future release. |
