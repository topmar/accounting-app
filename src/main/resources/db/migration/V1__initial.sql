CREATE TABLE IF NOT EXISTS `auth_users`
(
    `auth_user_id`            INT          NOT NULL AUTO_INCREMENT,
    `user_name`               VARCHAR(20)  NOT NULL,
    `password`                VARCHAR(255) NOT NULL,
    `role`                    VARCHAR(20)  NOT NULL,
    `enabled`                 BOOLEAN,
    `account_non_expired`     BOOLEAN,
    `account_non_locked`      BOOLEAN,
    `credentials_non_expired` BOOLEAN,
    PRIMARY KEY (`auth_user_id`)
);
CREATE TABLE IF NOT EXISTS `tokens`
(
    `token`        VARCHAR(255) NOT NULL,
    `auth_user_id` INT          NOT NULL,
    PRIMARY KEY (`token`),
    FOREIGN KEY (`auth_user_id`) REFERENCES `auth_users` (`auth_user_id`)
);
CREATE TABLE IF NOT EXISTS `addresses`
(
    `address_id` INT         NOT NULL AUTO_INCREMENT,
    `city`       VARCHAR(45) NULL DEFAULT NULL,
    `country`    VARCHAR(45) NULL DEFAULT 'PL',
    `district`   VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`address_id`)
);
CREATE TABLE IF NOT EXISTS `contacts`
(
    `contact_id` INT         NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(45) NOT NULL,
    `phone`      VARCHAR(15) NULL,
    PRIMARY KEY (`contact_id`)
);
CREATE TABLE IF NOT EXISTS `companies`
(
    `company_id` INT         NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (`company_id`)
);
CREATE TABLE IF NOT EXISTS `users`
(
    `user_id`       INT          NOT NULL AUTO_INCREMENT,
    `first_name`    VARCHAR(100) NOT NULL,
    `last_name`     VARCHAR(100) NOT NULL,
    `address_id`    INT,
    `contact_id`    INT,
    `company_id`    INT,
    `creation_date` VARCHAR(19),
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (`address_id`) REFERENCES `addresses` (`address_id`),
    FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`contact_id`),
    FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`)
);
