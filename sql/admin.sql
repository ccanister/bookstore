--��̨��½�û���

------------+
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adminName` varchar(40) NOT NULL,
  `adminPass` varchar(40) NOT NULL,
  PRIMARY KEY (`adminName`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 |

--��������
INSERT INTO `admin` VALUES('admin','123');