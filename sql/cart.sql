--�����û��ܹ��ﳵ����
--user_id��user��id�����

-----------------------------------------+
CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`userId`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--�����û����ﳵ������Ʒ��
--book_id��book��id�����
--cart_id��cart��id�����

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

