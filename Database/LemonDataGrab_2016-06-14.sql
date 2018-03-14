# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.42)
# Database: LemonDataGrab
# Generation Time: 2016-06-14 12:54:05 +0000
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

LOCK TABLES `ldg_administrator` WRITE;
/*!40000 ALTER TABLE `ldg_administrator` DISABLE KEYS */;

INSERT INTO `ldg_administrator` (`admi_id`, `admi_username`, `admi_name`, `admi_password`, `admi_identity`)
VALUES
	(1,'liuri','刘日','19950121112liuri','alskdfjlakjbvlzkjfhaoiweur2318rydra9wn8r');

/*!40000 ALTER TABLE `ldg_administrator` ENABLE KEYS */;
UNLOCK TABLES;


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

LOCK TABLES `ldg_client` WRITE;
/*!40000 ALTER TABLE `ldg_client` DISABLE KEYS */;

INSERT INTO `ldg_client` (`clie_id`, `clie_identity`, `clie_clientFingerprint`, `clie_system`, `clie_terminalType`, `clie_device`, `clie_version`, `clie_registrationTime`)
VALUES
	(1,'9z8ghq9ncraq238c7wa8nuaowuf','q823nr7coa8isdzrulsiovw34nucru4','iOS',1,'iPhone6sPlus','1.0',1451231231),
	(2,'987z98f7a98sdf7na98sdf7asdfasdf8a','d13d74ce-665b-4f05-9b2a-b7f85ce6ca9b','windows10',1,'iPhone6s','1.0',1464419707),
	(3,'018a7fc2-1003-416d-bb32-b738497d0622','42b6d0b8-25a5-450b-acda-1b14294b2f31','Mac OS X 10.11.5',1,'LemonsoftdeMacBook-Pro.local','1.0',1465006047),
	(4,'09a7489a-29a3-47ab-bc29-2956978f7932','6bdb2a7e-7543-48cd-86ed-34cd24e1cf46','Mac OS X 10.11.5',1,'LemonsoftdeMacBook-Pro.local','1.0',1465826903),
	(5,'4f317e0f-7617-4e8f-a810-48c409f1cd1b','aa961fc1-7f02-434d-96a3-6c361530f00f','Mac OS X 10.11.5',1,'LemonsoftdeMacBook-Pro.local','1.0',1465904752);

/*!40000 ALTER TABLE `ldg_client` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ldg_result
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ldg_result`;

CREATE TABLE `ldg_result` (
  `resu_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '序号',
  `resu_fingerprint` varchar(512) NOT NULL DEFAULT '' COMMENT '结果指纹',
  `resu_recoveryTime` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '回收时间',
  `resu_data` text NOT NULL COMMENT '数据',
  `resu_sessionFingerprint` varchar(512) NOT NULL DEFAULT '' COMMENT '通信会话指纹',
  PRIMARY KEY (`resu_id`),
  KEY `fingerprint` (`resu_fingerprint`(255)),
  KEY `sessionFingerprint` (`resu_sessionFingerprint`(255)),
  KEY `recoveryTime` (`resu_recoveryTime`)
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

LOCK TABLES `ldg_session` WRITE;
/*!40000 ALTER TABLE `ldg_session` DISABLE KEYS */;

INSERT INTO `ldg_session` (`sess_id`, `sess_sessionFingerprint`, `sess_client`, `sess_user`, `sess_setupTime`, `sess_lastCommunicationTime`, `sess_state`)
VALUES
	(1,'aweohfa238rs08dfcq0r238udsoz8fuszd',1,1,1451231231,1451231231,1),
	(2,'401c0800-edf3-454e-8858-3f996e01e08d',2,2,1464523946,1464523946,0),
	(3,'70c0fb18-dea0-4a46-a48b-ff910d8b9a84',3,2,1465006768,1465006768,0),
	(4,'04a76372-1754-4929-9b59-501d1ff5f7d0',4,2,1465826975,1465826975,0),
	(5,'ffb9fbc4-a366-4016-8897-c573c03b9888',5,2,1465904753,1465904753,0);

/*!40000 ALTER TABLE `ldg_session` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ldg_task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ldg_task`;

CREATE TABLE `ldg_task` (
  `task_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务的编号',
  `task_name` varchar(512) NOT NULL DEFAULT '' COMMENT '任务的名称',
  `task_fingerprint` varchar(512) NOT NULL DEFAULT '' COMMENT '任务的唯一指纹',
  `task_description` varchar(4096) NOT NULL DEFAULT '' COMMENT '任务的简介',
  `task_createTime` int(11) unsigned NOT NULL COMMENT '任务的创建时间',
  `task_publishTime` int(11) unsigned NOT NULL COMMENT '任务的发布时间',
  `task_distributeRepeatInterval` int(11) unsigned NOT NULL COMMENT '任务的重复派发时间',
  `task_distributeRepeatCount` int(11) unsigned NOT NULL COMMENT '任务的重复派发次数',
  `task_distributedNumber` int(11) unsigned NOT NULL COMMENT '任务分发序号',
  `task_executeScript` text NOT NULL COMMENT '任务执行脚本',
  `task_loadUrlTimeout` int(11) unsigned NOT NULL COMMENT '页面加载的超时时间',
  `task_state` int(4) NOT NULL COMMENT '任务状态',
  `task_theTop` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '任务是否置顶',
  PRIMARY KEY (`task_id`),
  KEY `name` (`task_name`(255)),
  KEY `fingerprint` (`task_fingerprint`(255)),
  KEY `publishTime` (`task_publishTime`)
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
  `user_registrationTime` int(11) unsigned NOT NULL COMMENT '用户注册的时间',
  `user_identity` varchar(128) NOT NULL DEFAULT '' COMMENT '用户的身份码，注册时由系统生成的UUID，用作用户身份标识',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ldg_user` WRITE;
/*!40000 ALTER TABLE `ldg_user` DISABLE KEYS */;

INSERT INTO `ldg_user` (`user_id`, `user_email`, `user_phone`, `user_username`, `user_name`, `user_usergroup`, `user_score`, `user_password`, `user_registrationTime`, `user_identity`)
VALUES
	(1,'188443213@qq.com','18842656880','liuri','liuri',1,1000,'19950121112liuri',1451231231,'shfcia7wr29a37ry9qcr'),
	(2,'liuri@lemonsoft.net','','','åæ¥',2,0,'eca7a808bb7bb2bce406900208b1eb5b',1464486539,'9b978ce0-75ba-4d18-936f-549aab3f212b'),
	(3,'liuri1@lemonsoft.net','','','åæ¥',1,0,'eca7a808bb7bb2bce406900208b1eb5b',1464486639,'3b8e4361-72e5-488c-9ba7-df12b71c6e65'),
	(4,'liuri2@lemonsoft.net','','','åæ¥',1,0,'eca7a808bb7bb2bce406900208b1eb5b',1464486748,'e804c0fb-f2b6-422e-a3bb-f512ba2112bc'),
	(5,'liuri3@lemonsoft.net','','','刘日',1,0,'eca7a808bb7bb2bce406900208b1eb5b',1464487266,'e7b4ca07-836a-4994-bd2f-6c664c6db33d');

/*!40000 ALTER TABLE `ldg_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ldg_userGroup
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ldg_userGroup`;

CREATE TABLE `ldg_userGroup` (
  `usgr_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户组id',
  `usgr_name` varchar(256) NOT NULL DEFAULT '' COMMENT '用户组的名称',
  `usgr_description` varchar(20480) NOT NULL DEFAULT '' COMMENT '用户组的描述',
  PRIMARY KEY (`usgr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ldg_userGroup` WRITE;
/*!40000 ALTER TABLE `ldg_userGroup` DISABLE KEYS */;

INSERT INTO `ldg_userGroup` (`usgr_id`, `usgr_name`, `usgr_description`)
VALUES
	(1,'普通用户','普通用户组，默认注册之后用户均属于该用户组'),
	(2,'管理员用户','所有的管理员用户都归于该用户组');

/*!40000 ALTER TABLE `ldg_userGroup` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
