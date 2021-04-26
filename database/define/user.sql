drop table if exists user_group;
CREATE TABLE `user_group` (
                              `user_id` varchar(64) NOT NULL,
                              `user_name` varchar(64) DEFAULT NULL,
                              `user_psw` varchar(64) DEFAULT NULL,
                              `user_permission` char(16) DEFAULT NULL,
                              `customer_id` int DEFAULT NULL,
                              `customer_sex` varchar(64) NOT NULL,
                              `customer_tel` varchar(64) NOT NULL,
                              `customer_address` varchar(64) NOT NULL,
                              PRIMARY KEY (`user_id`)
) engine = innodb character set = utf8mb4;