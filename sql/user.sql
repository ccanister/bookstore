--����ǰ̨�û�
--username��Ϊ����


CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `phone` varchar(40) NOT NULL,
  `email` varchar(40) DEFAULT NULL,
  `address` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--��������
INSERT INTO `user`(`username`,`password`,`phone`,`email`,`address`) 
	VALUES('�','123456','18883991234','12345678@qq.com','����');