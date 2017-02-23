--�����鱾���ݱ�
--category_id��category������

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

--��������
--category_id������category�����Ѿ����ڵ�id
--image����ͼƬ��(��������Ϊ����)
INSERT INTO `book`(`name`,`category_id`,`price`,`image`,`author`,`description`)
	VALUES('ʮ�쿴������ѧ',9,30.0,'2017/1/15','�','ʮ����ܶ��ܶ�');