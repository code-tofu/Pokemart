DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory (
    product_id VARCHAR(8) NOT NULL,
    quantity INT NOT NULL,
    discount DOUBLE(3,1),
    deduct DOUBLE(8,2),
    FOREIGN KEY (product_id) REFERENCES product_data(product_id)
);


DESC inventory;
-- +------------+-------------+------+-----+---------+-------+
-- | Field      | Type        | Null | Key | Default | Extra |
-- +------------+-------------+------+-----+---------+-------+
-- | product_id | varchar(8)  | NO   |     | NULL    |       |
-- | quantity   | int         | NO   |     | NULL    |       |
-- | discount   | double(3,1) | YES  |     | NULL    |       |
-- | deduct     | double(8,2) | YES  |     | NULL    |       |
-- +------------+-------------+------+-----+---------+-------+

INSERT INTO inventory (product_id,quantity) VALUES (?,?)
UPDATE inventory SET discount = ? WHERE product_id = ?;
UPDATE inventory SET deduct = ? WHERE product_id = ?;

SELECT * FROM inventory LIMIT ? OFFSET ?;


INSERT INTO inventory (product_id,quantity) VALUES ('0556cdb0',0)

SELECT product_data.product_id, product_data.product_name,product_data.cost, inventory.quantity, inventory.discount, inventory.deduct
FROM product_data
JOIN inventory ON product_data.product_id = inventory.product_id
WHERE inventory.quantity>0
LIMIT 99 OFFSET 0;