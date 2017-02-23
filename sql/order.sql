--创建用户订单表


-----------------------------+
| orders | CREATE TABLE `order` (
  `id` varchar(40) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `price` double NOT NULL,
  `state` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`userId`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--创建用户订单单项表

CREATE TABLE `orderitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL,
  `prices` double NOT NULL,
  `orderId` varchar(40) NOT NULL,
  `bookId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orderId` (`orderId`),
  KEY `bookId` (`bookId`),
  CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id
`),
  CONSTRAINT `orderitem_ibfk_3` FOREIGN KEY (`bookId`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;