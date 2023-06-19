DROP TABLE IF EXISTS user_profiles;
CREATE TABLE user_profiles (
    user_id VARCHAR(10),
    customer_name VARCHAR(50),
    customer_email VARCHAR(50),
    customer_phone VARCHAR(15),
    shipping_address VARCHAR(255),
    birthday DATE,
    gender VARCHAR(10),
    member_level ENUM('BRONZE','SILVER','GOLD'),
    member_since DATE,
    PRIMARY KEY (customer_email),
    FOREIGN KEY (user_id) REFERENCES user_details(user_id)
);
    -- social_media VARCHAR(255)
    -- country VARCHAR(50)

desc user_profiles;
-- +------------------+--------------------------------+------+-----+---------+-------+
-- | Field            | Type                           | Null | Key | Default | Extra |
-- +------------------+--------------------------------+------+-----+---------+-------+
-- | user_id          | varchar(10)                    | YES  | MUL | NULL    |       |
-- | customer_name    | varchar(50)                    | YES  |     | NULL    |       |
-- | customer_email   | varchar(50)                    | NO   | PRI | NULL    |       |
-- | customer_phone   | varchar(15)                    | YES  |     | NULL    |       |
-- | shipping_address | varchar(255)                   | YES  |     | NULL    |       |
-- | birthday         | date                           | YES  |     | NULL    |       |
-- | gender           | varchar(10)                    | YES  |     | NULL    |       |
-- | member_level     | enum('BRONZE','SILVER','GOLD') | YES  |     | NULL    |       |
-- | member_since     | date                           | YES  |     | NULL    |       |
-- +------------------+--------------------------------+------+-----+---------+-------+
