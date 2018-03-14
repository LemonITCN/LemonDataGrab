/*
Navicat MySQL Data Transfer

Source Server         : LemonDataGrab
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : lemondatagrab

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2016-07-24 16:45:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ldg_administrator
-- ----------------------------
DROP TABLE IF EXISTS `ldg_administrator`;
CREATE TABLE `ldg_administrator` (
  `admi_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `admi_username` varchar(256) NOT NULL DEFAULT '' COMMENT '登录用户名',
  `admi_name` varchar(256) NOT NULL DEFAULT '' COMMENT '用户的真实姓名',
  `admi_password` varchar(512) NOT NULL DEFAULT '' COMMENT '用户密码',
  `admi_identity` varchar(128) NOT NULL DEFAULT '' COMMENT '用户的身份码，注册时由系统生成的UUID，用作用户身份标识',
  PRIMARY KEY (`admi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_administrator
-- ----------------------------
INSERT INTO `ldg_administrator` VALUES ('1', 'liuri', '刘日', '19950121112liuri', 'alskdfjlakjbvlzkjfhaoiweur2318rydra9wn8r');

-- ----------------------------
-- Table structure for ldg_client
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_client
-- ----------------------------
INSERT INTO `ldg_client` VALUES ('1', '9z8ghq9ncraq238c7wa8nuaowuf', 'q823nr7coa8isdzrulsiovw34nucru4', 'iOS', '1', 'iPhone6sPlus', '1.0', '1451231231');
INSERT INTO `ldg_client` VALUES ('2', '987z98f7a98sdf7na98sdf7asdfasdf8a', 'd13d74ce-665b-4f05-9b2a-b7f85ce6ca9b', 'windows10', '1', 'iPhone6s', '1.0', '1464419707');
INSERT INTO `ldg_client` VALUES ('3', '018a7fc2-1003-416d-bb32-b738497d0622', '42b6d0b8-25a5-450b-acda-1b14294b2f31', 'Mac OS X 10.11.5', '1', 'LemonsoftdeMacBook-Pro.local', '1.0', '1465006047');
INSERT INTO `ldg_client` VALUES ('4', '09a7489a-29a3-47ab-bc29-2956978f7932', '6bdb2a7e-7543-48cd-86ed-34cd24e1cf46', 'Mac OS X 10.11.5', '1', 'LemonsoftdeMacBook-Pro.local', '1.0', '1465826903');
INSERT INTO `ldg_client` VALUES ('5', '4f317e0f-7617-4e8f-a810-48c409f1cd1b', 'aa961fc1-7f02-434d-96a3-6c361530f00f', 'Mac OS X 10.11.5', '1', 'LemonsoftdeMacBook-Pro.local', '1.0', '1465904752');
INSERT INTO `ldg_client` VALUES ('6', 'd613cf47-5a0b-4d10-9bd5-f9cc11e98158', '2f6eccd5-7527-42d3-9576-dbb0a7e7d06b', 'Mac OS X 10.11.5', '1', 'liuris-MacBook-Pro.local', '1.0', '1466575607');
INSERT INTO `ldg_client` VALUES ('7', '94a7c798-989f-4049-9c43-bea73e529f23', '0b572a38-86ec-40df-86ca-6b11354118e6', 'Mac OS X 10.11.5', '1', 'liuris-MacBook-Pro.local', '1.0', '1466637727');
INSERT INTO `ldg_client` VALUES ('8', '43af87f9-9c4c-4d5a-9514-d970a6578bed', 'db9b2304-ce79-4bf7-8386-0ac84b9d1342', 'Windows 7 6.1', '1', '1em0nsOft-PC', '1.0', '1468760556');
INSERT INTO `ldg_client` VALUES ('9', 'd9fafb52-d70a-4b12-836d-0caadc12e189', 'ee59c56c-3be1-48ef-9242-fa953dd21374', 'Windows 7 6.1', '1', '1em0nsOft-PC', '1.0', '1469261316');
INSERT INTO `ldg_client` VALUES ('10', '2b1762f0-d4b7-4eca-81b9-688a8975aedc', '455f8258-0ce7-41d5-ac54-572f66fb83a2', 'Windows 7 6.1', '1', '1em0nsOft-PC', '1.0', '1469347668');

-- ----------------------------
-- Table structure for ldg_errorresult
-- ----------------------------
DROP TABLE IF EXISTS `ldg_errorresult`;
CREATE TABLE `ldg_errorresult` (
  `erre_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `erre_taskFingerprint` varchar(256) NOT NULL DEFAULT '' COMMENT '错误的所属任务指纹',
  `erre_log` text NOT NULL COMMENT '错误任务中产生的所有日志',
  `erre_sessionFingerprint` varchar(512) NOT NULL DEFAULT '' COMMENT '错误产生的会话对应的会话指纹',
  `erre_recoveryTime` int(11) unsigned NOT NULL COMMENT '错误信息回收的时间',
  PRIMARY KEY (`erre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_errorresult
-- ----------------------------

-- ----------------------------
-- Table structure for ldg_result
-- ----------------------------
DROP TABLE IF EXISTS `ldg_result`;
CREATE TABLE `ldg_result` (
  `resu_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '序号',
  `resu_fingerprint` varchar(512) NOT NULL DEFAULT '' COMMENT '结果指纹',
  `resu_recoveryTime` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '回收时间',
  `resu_data` text NOT NULL COMMENT '数据，JSON格式',
  `resu_log` text NOT NULL COMMENT '客户端在采集数据过程中产生的日志，JSON格式',
  `resu_sessionFingerprint` varchar(512) NOT NULL DEFAULT '' COMMENT '通信会话指纹',
  PRIMARY KEY (`resu_id`),
  KEY `fingerprint` (`resu_fingerprint`(255)),
  KEY `sessionFingerprint` (`resu_sessionFingerprint`(255)),
  KEY `recoveryTime` (`resu_recoveryTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_result
-- ----------------------------

-- ----------------------------
-- Table structure for ldg_session
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_session
-- ----------------------------
INSERT INTO `ldg_session` VALUES ('1', 'aweohfa238rs08dfcq0r238udsoz8fuszd', '1', '1', '1451231231', '1451231231', '1');
INSERT INTO `ldg_session` VALUES ('2', '401c0800-edf3-454e-8858-3f996e01e08d', '2', '2', '1464523946', '1464523946', '0');
INSERT INTO `ldg_session` VALUES ('3', '70c0fb18-dea0-4a46-a48b-ff910d8b9a84', '3', '2', '1465006768', '1465006768', '0');
INSERT INTO `ldg_session` VALUES ('4', '04a76372-1754-4929-9b59-501d1ff5f7d0', '4', '2', '1465826975', '1465826975', '0');
INSERT INTO `ldg_session` VALUES ('5', 'ffb9fbc4-a366-4016-8897-c573c03b9888', '5', '2', '1465904753', '1465904753', '0');
INSERT INTO `ldg_session` VALUES ('7', 'beda6372-0acf-45c8-bc4c-8b81fbb1a645', '7', '2', '1466637727', '1466637727', '0');
INSERT INTO `ldg_session` VALUES ('11', 'bc0d3717-f56d-4268-b9be-23a87461cb28', '6', '2', '1466836936', '1466836936', '0');
INSERT INTO `ldg_session` VALUES ('13', '63649009-d726-44ee-8fec-bc972c46acf3', '7', '3', '1467462743', '1467462743', '0');
INSERT INTO `ldg_session` VALUES ('14', '353084be-2ec5-4500-a187-c7b84a654897', '8', '2', '1468760556', '1468760556', '0');
INSERT INTO `ldg_session` VALUES ('15', 'b5aef14b-6405-4762-b3d5-8b0b5b80d358', '10', '2', '1469347669', '1469347669', '0');

-- ----------------------------
-- Table structure for ldg_task
-- ----------------------------
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
  `task_state` int(4) NOT NULL COMMENT '任务状态',
  `task_theTop` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '任务是否置顶',
  `task_expired` int(11) unsigned NOT NULL COMMENT '过期时间，当任务未能在指定时间发布出去，那么最迟的过期时间段，该字段为时间段，而非时间点',
  PRIMARY KEY (`task_id`),
  KEY `name` (`task_name`(255)),
  KEY `fingerprint` (`task_fingerprint`(255)),
  KEY `publishTime` (`task_publishTime`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_task
-- ----------------------------
INSERT INTO `ldg_task` VALUES ('57', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_0', '这是一个测试任务', '1467421773', '1467444600', '86400', '9', '0', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('58', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_1', '这是一个测试任务', '1467421773', '1467531000', '86400', '9', '1', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('59', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_2', '这是一个测试任务', '1467421773', '1467617400', '86400', '9', '2', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('60', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_3', '这是一个测试任务', '1467421773', '1467703800', '86400', '9', '3', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('61', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_4', '这是一个测试任务', '1467421773', '1467790200', '86400', '9', '4', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('62', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_5', '这是一个测试任务', '1467421773', '1467876600', '86400', '9', '5', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('63', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_6', '这是一个测试任务', '1467421773', '1467963000', '86400', '9', '6', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('64', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_7', '这是一个测试任务', '1467421773', '1468049400', '86400', '9', '7', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('65', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_8', '这是一个测试任务', '1467421773', '1468135800', '86400', '9', '8', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('66', '测试任务', '312eff5d-6a27-4bac-b81f-bf4d499e5a60_9', '这是一个测试任务', '1467421773', '1468222200', '86400', '9', '9', 'console.log(\"successful\");', '4', '0', '0');
INSERT INTO `ldg_task` VALUES ('67', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_0', '任务简单的说明哦', '1467463809', '1467466200', '21600', '20', '0', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('68', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_1', '任务简单的说明哦', '1467463809', '1467487800', '21600', '20', '1', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('69', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_2', '任务简单的说明哦', '1467463809', '1467509400', '21600', '20', '2', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('70', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_3', '任务简单的说明哦', '1467463809', '1467531000', '21600', '20', '3', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('71', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_4', '任务简单的说明哦', '1467463809', '1467552600', '21600', '20', '4', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('72', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_5', '任务简单的说明哦', '1467463809', '1467574200', '21600', '20', '5', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('73', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_6', '任务简单的说明哦', '1467463809', '1467595800', '21600', '20', '6', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('74', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_7', '任务简单的说明哦', '1467463809', '1467617400', '21600', '20', '7', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('75', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_8', '任务简单的说明哦', '1467463809', '1467639000', '21600', '20', '8', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('76', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_9', '任务简单的说明哦', '1467463809', '1467660600', '21600', '20', '9', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('77', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_10', '任务简单的说明哦', '1467463809', '1467682200', '21600', '20', '10', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('78', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_11', '任务简单的说明哦', '1467463809', '1467703800', '21600', '20', '11', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('79', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_12', '任务简单的说明哦', '1467463809', '1467725400', '21600', '20', '12', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('80', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_13', '任务简单的说明哦', '1467463809', '1467747000', '21600', '20', '13', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('81', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_14', '任务简单的说明哦', '1467463809', '1467768600', '21600', '20', '14', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('82', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_15', '任务简单的说明哦', '1467463809', '1467790200', '21600', '20', '15', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('83', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_16', '任务简单的说明哦', '1467463809', '1467811800', '21600', '20', '16', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('84', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_17', '任务简单的说明哦', '1467463809', '1467833400', '21600', '20', '17', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('85', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_18', '任务简单的说明哦', '1467463809', '1467855000', '21600', '20', '18', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('86', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_19', '任务简单的说明哦', '1467463809', '1467876600', '21600', '20', '19', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('87', '测试任务哦', '05b1d7bd-543d-4541-b01f-5c3ef6ce0976_20', '任务简单的说明哦', '1467463809', '1467898200', '21600', '20', '20', 'console.log(\'ts\');', '4', '0', '7200');
INSERT INTO `ldg_task` VALUES ('88', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_0', '这是一个测试任务', '1469349123', '1469349123', '259200', '10', '0', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('89', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_1', '这是一个测试任务', '1469349123', '1469608323', '259200', '10', '1', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('90', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_2', '这是一个测试任务', '1469349123', '1469867523', '259200', '10', '2', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('91', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_3', '这是一个测试任务', '1469349123', '1470126723', '259200', '10', '3', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('92', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_4', '这是一个测试任务', '1469349123', '1470385923', '259200', '10', '4', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('93', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_5', '这是一个测试任务', '1469349123', '1470645123', '259200', '10', '5', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('94', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_6', '这是一个测试任务', '1469349123', '1470904323', '259200', '10', '6', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('95', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_7', '这是一个测试任务', '1469349123', '1471163523', '259200', '10', '7', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('96', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_8', '这是一个测试任务', '1469349123', '1471422723', '259200', '10', '8', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('97', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_9', '这是一个测试任务', '1469349123', '1471681923', '259200', '10', '9', 'console.log(\'1\');', '0', '0', '86400');
INSERT INTO `ldg_task` VALUES ('98', '测试任务', '7c51a4e6-0809-45fc-bcce-0fd1ce6cc218_10', '这是一个测试任务', '1469349123', '1471941123', '259200', '10', '10', 'console.log(\'1\');', '0', '0', '86400');

-- ----------------------------
-- Table structure for ldg_user
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_user
-- ----------------------------
INSERT INTO `ldg_user` VALUES ('1', '188443213@qq.com', '18842656880', 'liuri', 'liuri', '1', '1000', '19950121112liuri', '1451231231', 'shfcia7wr29a37ry9qcr');
INSERT INTO `ldg_user` VALUES ('2', 'liuri@lemonsoft.net', '', '', 'æ¥', '2', '0', 'eca7a808bb7bb2bce406900208b1eb5b', '1464486539', '9b978ce0-75ba-4d18-936f-549aab3f212b');
INSERT INTO `ldg_user` VALUES ('3', 'liuri1@lemonsoft.net', '', '', 'æ¥', '2', '0', 'eca7a808bb7bb2bce406900208b1eb5b', '1464486639', '3b8e4361-72e5-488c-9ba7-df12b71c6e65');
INSERT INTO `ldg_user` VALUES ('4', 'liuri2@lemonsoft.net', '', '', 'æ¥', '1', '0', 'eca7a808bb7bb2bce406900208b1eb5b', '1464486748', 'e804c0fb-f2b6-422e-a3bb-f512ba2112bc');
INSERT INTO `ldg_user` VALUES ('5', 'liuri3@lemonsoft.net', '', '', '刘日', '1', '0', 'eca7a808bb7bb2bce406900208b1eb5b', '1464487266', 'e7b4ca07-836a-4994-bd2f-6c664c6db33d');

-- ----------------------------
-- Table structure for ldg_usergroup
-- ----------------------------
DROP TABLE IF EXISTS `ldg_usergroup`;
CREATE TABLE `ldg_usergroup` (
  `usgr_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户组id',
  `usgr_name` varchar(256) NOT NULL DEFAULT '' COMMENT '用户组的名称',
  `usgr_description` varchar(20480) NOT NULL DEFAULT '' COMMENT '用户组的描述',
  PRIMARY KEY (`usgr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_usergroup
-- ----------------------------
INSERT INTO `ldg_usergroup` VALUES ('1', '普通用户', '普通用户组，默认注册之后用户均属于该用户组');
INSERT INTO `ldg_usergroup` VALUES ('2', '管理员用户', '所有的管理员用户都归于该用户组');
