--创建用户总购物车表项
--user_id是user表id的外键

-----------------------------------------+
CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`userId`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--创建用户表购物车单个商品表
--book_id是book表id的外键
--cart_id是cart表id的外键

---------+
CREATE TABLE `cartitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `prices` double NOT NULL,
  `cartId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `book_id` (`bookId`),
  KEY `cart_id` (`cartId`),
  CONSTRAINT `cartitem_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `book` (`id`),
  CONSTRAINT `cartitem_ibfk_2` FOREIGN KEY (`cartId`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

