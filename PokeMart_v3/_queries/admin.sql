INSERT INTO user_details (user_id, username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked,role)
VALUES ('u00000000','engineer1', '$2a$10$6qT8MuYVRldYQ9ik.eqj8eRrK2Zm/q8XUDnSeM5VCoMmQSCOPscG2', TRUE, TRUE,TRUE,TRUE,'ROLE_DEVELOPER');

INSERT INTO user_profiles (user_id,customer_name,customer_email,customer_phone,shipping_address,birthdate,gender,member_level,member_since) VALUES ('u00000000','developer','pokemart.tofucode@gmail.com','00000000','20 Clementi Ave 1, Singapore 129957', '1990-06-01','M','GOLD','2023-06-01');

INSERT INTO user_details (user_id, username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked,role)
VALUES ('u00000001','admin1', '$2a$10$dznwdbdQKJopzKbjafph.u33mt6UbzoUaBzFNWIAt5/XMtODJ1nTG', TRUE, TRUE,TRUE,TRUE,'ROLE_ADMINISTRATOR');

INSERT INTO user_profiles (user_id,customer_name,customer_email,customer_phone,shipping_address,birthdate,gender,member_level,member_since) VALUES ('u00000001','administrator','pokemail@pokemail.com','00000000','20 Clementi Ave 1, Singapore 129957', '1990-06-02','M','GOLD','2023-06-02');

INSERT INTO user_details (user_id, username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked,role)
VALUES ('u00000002','seller_1','$2a$10$qkA03b62.6egE.2pgSVyDe7fNHdWdgah9pWs4lqfZyH4iIFeLKs/q', TRUE, TRUE,TRUE,TRUE, 'ROLE_SELLER');

INSERT INTO user_profiles (user_id,customer_name,customer_email,customer_phone,shipping_address,birthdate,gender,member_level,member_since) VALUES ('u00000002','seller','pokemart@pokemail.com','00000000','20 Clementi Ave 1, Singapore 129957', '1990-06-02','M','GOLD','2023-06-02');

