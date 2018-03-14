# ************************************************************
# Sequel Pro SQL dump
# Version 4135
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.42)
# Database: LemonDataGrab
# Generation Time: 2016-04-14 09:11:04 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table ldg_administrator
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ldg_administrator`;

CREATE TABLE `ldg_administrator` (
  `admi_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `admi_username` varchar(256) NOT NULL DEFAULT '' COMMENT '登录用户名',
  `admi_name` varchar(256) NOT NULL DEFAULT '' COMMENT '用户的真实姓名',
  `admi_password` varchar(512) NOT NULL DEFAULT '' COMMENT '用户密码',
  `admi_identity` varchar(128) NOT NULL DEFAULT '' COMMENT '用户的身份码，注册时由系统生成的UUID，用作用户身份标识',
  PRIMARY KEY (`admi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table ldg_client
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ldg_client`;

CREATE TABLE `ldg_client` (
  `clie_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `clie_identity` varchar(512) NOT NULL DEFAULT '' COMMENT '客户端身份唯一标识',
  `clie_clientFingerprint` varchar(512) NOT NULL DEFAULT '' COMMENT '客户端指纹',
  `clie_system` varchar(256) NOT NULL DEFAULT '' COMMENT '客户端操作系统',
  `clie_terminalType` int(4) unsigned NOT NULL COMMENT '终端的类型，如数据抓取终端、管理员终端等，参照终端类型枚举',
  `clie_device` varchar(512) NOT NULL DEFAULT '' COMMENT '设备信息，如CPU硬盘等信息',
  `clie_version` varchar(256) NOT NULL DEFAULT '' COMMENT '客户端软件版本号',
  `clie_registrationTime` int(11) unsigned NOT NULL COMMENT '客户端注册时间,unix时间戳',
  PRIMARY KEY (`clie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table ldg_session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ldg_session`;

CREATE TABLE `ldg_session` (
  `sess_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sess_sessionFingerprint` varchar(512) NOT NULL DEFAULT '' COMMENT '会话指纹',
  `sess_client` int(11) unsigned NOT NULL COMMENT '会话参与的客户端',
  `sess_user` int(11) unsigned NOT NULL COMMENT '会话参与的用户',
  `sess_setupTime` int(11) unsigned NOT NULL COMMENT '会话的建立时间',
  `sess_lastCommunicationTime` int(11) unsigned NOT NULL COMMENT '上次通过此会话进行通信的时间，unix时间戳',
  `sess_state` int(4) unsigned NOT NULL DEFAULT '0' COMMENT '会话的状态码，记录如是否被激活等状态',
  PRIMARY KEY (`sess_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table ldg_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ldg_user`;

CREATE TABLE `ldg_user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_email` varchar(256) NOT NULL DEFAULT '' COMMENT '电子邮件地址',
  `user_phone` varchar(64) NOT NULL DEFAULT '' COMMENT '电话号码',
  `user_username` varchar(256) NOT NULL DEFAULT '' COMMENT '登录用户名',
  `user_name` varchar(256) NOT NULL DEFAULT '' COMMENT '用户的真实姓名',
  `user_usergroup` int(11) unsigned NOT NULL COMMENT '用户所属用户组的id',
  `user_score` int(11) NOT NULL COMMENT '用户的经验积分，根据指定的规则进行积分计算',
  `user_password` varchar(512) NOT NULL DEFAULT '' COMMENT '用户密码',
  `user_registrationTime` varchar(64) NOT NULL DEFAULT '' COMMENT '用户注册的时间',
  `user_identity` varchar(128) NOT NULL DEFAULT '' COMMENT '用户的身份码，注册时由系统生成的UUID，用作用户身份标识',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table ldg_userGroup
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ldg_userGroup`;

CREATE TABLE `ldg_userGroup` (
  `usgr_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户组id',
  `usgr_name` varchar(256) NOT NULL DEFAULT '' COMMENT '用户组的名称',
  `user_description` varchar(20480) NOT NULL DEFAULT '' COMMENT '用户组的描述',
  PRIMARY KEY (`usgr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ldg_userGroup` WRITE;
/*!40000 ALTER TABLE `ldg_userGroup` DISABLE KEYS */;

INSERT INTO `ldg_userGroup` (`usgr_id`, `usgr_name`, `user_description`)
VALUES
	(1,'普通用户','普通用户组，默认注册之后用户均属于该用户组');

/*!40000 ALTER TABLE `ldg_userGroup` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
