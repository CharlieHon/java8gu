DROP DATABASE IF EXISTS `coding_mysql`;
CREATE DATABASE IF NOT EXISTS `coding_mysql`;
USE `coding_mysql`;

CREATE TABLE `tb_product` (
	`id` INT(11) NOT NULL,
	`product_no` VARCHAR(20) UNIQUE DEFAULT NULL,
	`name` VARCHAR(255) DEFAULT NULL,
	`price` DECIMAL(10, 2) DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE
) CHARACTER SET=utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `tb_product`(`id`, `product_no`, `name`) VALUES
	(1, '0001', 'apple'),
	(2, '0002', 'banana'),
	(3, '0003', 'orange'),
	(4, '0004', 'iphone13'),
	(5, '0005', 'ipad8'),
	(6, '0006', 'macbookpro'),
	(7, '0007', 'ps5'),
	(8, '0008', 'grape'),
	(9, '0009', 'watermelon'),
	(10, '0010', 'mango')
	;

SELECT * FROM `tb_product` WHERE `id` + 1 = 2;

EXPLAIN SELECT * FROM `tb_product` WHERE `id` = 2;

EXPLAIN SELECT COUNT(1) FROM `tb_product`;

EXPLAIN SELECT COUNT(`id`) FROM `tb_product`;

EXPLAIN SELECT COUNT(`product_no`) FROM `tb_product`;
