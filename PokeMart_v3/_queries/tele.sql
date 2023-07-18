DROP TABLE IF EXISTS user_telegram;
CREATE TABLE user_telegram (
    user_id VARCHAR(10) NOT NULL,
    telegram_id BIGINT UNSIGNED UNIQUE,
    authenticated BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES user_details(user_id)
);

DESC user_telegram;
-- +---------------+-----------------+------+-----+---------+-------+
-- | Field         | Type            | Null | Key | Default | Extra |
-- +---------------+-----------------+------+-----+---------+-------+
-- | user_id       | varchar(10)     | NO   | MUL | NULL    |       |
-- | telegram_id   | bigint unsigned | YES  | UNI | NULL    |       |
-- | authenticated | tinyint(1)      | YES  |     | NULL    |       |
-- +---------------+-----------------+------+-----+---------+-------+

INSERT INTO user_telegram VALUES ('u00000000',695741883)