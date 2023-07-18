DROP TABLE IF EXISTS user_details;
CREATE TABLE user_details (
    user_id VARCHAR(10) NOT NULL, 
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    role ENUM('ROLE_DEVELOPER','ROLE_ADMINISTRATOR','ROLE_SELLER','ROLE_CUSTOMER'),
    PRIMARY KEY (user_id)
)

DESC user_details
-- +-------------------------+---------------------------------------------------------------------------+------+-----+---------+-------+
-- | Field                   | Type                                                                      | Null | Key | Default | Extra |
-- +-------------------------+---------------------------------------------------------------------------+------+-----+---------+-------+
-- | username                | varchar(50)                                                               | NO   |     | NULL    |       |
-- | password                | varchar(255)                                                              | NO   |     | NULL    |       |
-- | user_id                 | varchar(10)                                                               | NO   | PRI | NULL    |       |
-- | role                    | enum('ROLE_DEVELOPER','ROLE_ADMINISTRATOR','ROLE_SELLER','ROLE_CUSTOMER') | YES  |     | NULL    |       |
-- | enabled                 | tinyint(1)                                                                | NO   |     | NULL    |       |
-- | account_non_expired     | tinyint(1)                                                                | NO   |     | NULL    |       |
-- | credentials_non_expired | tinyint(1)                                                                | NO   |     | NULL    |       |
-- | account_non_locked      | tinyint(1)                                                                | NO   |     | NULL    |       |
-- +-------------------------+---------------------------------------------------------------------------+------+-----+---------+-------+

-- +----------+------------+-----------+----------------+---------+---------------------+-------------------------+--------------------+
-- | username | password   | user_id   | role           | enabled | account_non_expired | credentials_non_expired | account_non_locked |
-- +----------+------------+-----------+----------------+---------+---------------------+-------------------------+--------------------+
-- | profOak  | pallettown | u00000000 | ROLE_DEVELOPER |       1 |                   1 |                       1 |                  1 |
-- +----------+------------+-----------+----------------+---------+---------------------+-------------------------+--------------------+