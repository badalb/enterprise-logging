SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `logging` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;

USE `logging`;

CREATE TABLE `tbluser` (
  `user_id` char(38) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `is_inactive` char(1) DEFAULT NULL,
  `is_locked` char(1) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `created_by` char(38) DEFAULT NULL,
  `last_modified_by` char(38) DEFAULT NULL,
  `is_deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
