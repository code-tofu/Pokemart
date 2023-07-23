DROP TABLE IF EXISTS product_attributes;
CREATE TABLE product_attributes (
    product_id VARCHAR(10) NOT NULL,
    attribute VARCHAR(50) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product_data(product_id)
);

desc product_attributes;
-- +------------+-------------+------+-----+---------+-------+
-- | Field      | Type        | Null | Key | Default | Extra |
-- +------------+-------------+------+-----+---------+-------+
-- | product_id | varchar(10) | NO   | MUL | NULL    |       |
-- | attribute  | varchar(50) | NO   |     | NULL    |       |
-- +------------+-------------+------+-----+---------+-------+