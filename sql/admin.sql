--后台登陆用户表

------------+
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adminName` varchar(40) NOT NULL,
  `adminPass` varchar(40) NOT NULL,
  PRIMARY KEY (`adminName`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 |

--测试数据
INSERT INTO `admin` VALUES('admin','123');