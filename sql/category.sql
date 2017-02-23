--书本种类
--iscommond代表种类是否出现在首页

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `iscommond` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--测试数据
INSERT INTO `category`(`name`,`description`,`iscommond`) values('心理学','发现内心',1);