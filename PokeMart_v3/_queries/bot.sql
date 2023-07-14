DROP TABLE IF EXISTS user_telegram;
CREATE TABLE user_telegram (
    user_id VARCHAR(10) NOT NULL,
    telegram_id BIGINT UNSIGNED UNIQUE,
    FOREIGN KEY (user_id) REFERENCES user_details(user_id)
);

-- +-------------+-----------------+------+-----+---------+-------+
-- | Field       | Type            | Null | Key | Default | Extra |
-- +-------------+-----------------+------+-----+---------+-------+
-- | user_id     | varchar(10)     | NO   | MUL | NULL    |       |
-- | telegram_id | bigint unsigned | YES  | UNI | NULL    |       |
-- +-------------+-----------------+------+-----+---------+-------+
-- 2 rows in set (0.02 sec)