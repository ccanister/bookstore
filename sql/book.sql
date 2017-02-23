--创建书本数据表
--category_id是category表的外键

------------------------------+
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `category_id` int(11) NOT NULL,
  `price` double NOT NULL,
  `image` varchar(40) DEFAULT NULL,
  `author` varchar(40) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `category_id_fk` (`category_id`),
  CONSTRAINT `category_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category`
(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--测试数据
--category_id必须是category表中已经存在的id
--image代表图片名(以年月日为名称)
INSERT INTO `book`(`name`,`category_id`,`price`,`image`,`author`,`description`)
	VALUES('十天看懂心理学',9,30.0,'2017/1/15','李华','十天就能懂很多');