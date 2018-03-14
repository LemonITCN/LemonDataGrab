/*
Navicat MySQL Data Transfer

Source Server         : LemonDataGrab
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : lemondatagrab

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2016-08-11 07:12:14
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

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
INSERT INTO `ldg_session` VALUES ('26', 'ee533376-06d3-4bfb-bb40-af5f4292d562', '9', '2', '1470836064', '1470836064', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=415 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ldg_task
-- ----------------------------
INSERT INTO `ldg_task` VALUES ('252', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_0', '晚上的测试任务的描述', '1470565869', '1470566100', '120', '60', '0', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470566220');
INSERT INTO `ldg_task` VALUES ('253', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_1', '晚上的测试任务的描述', '1470565869', '1470566220', '120', '60', '1', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470566340');
INSERT INTO `ldg_task` VALUES ('254', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_2', '晚上的测试任务的描述', '1470565869', '1470566340', '120', '60', '2', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470566460');
INSERT INTO `ldg_task` VALUES ('255', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_3', '晚上的测试任务的描述', '1470565869', '1470566460', '120', '60', '3', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470566580');
INSERT INTO `ldg_task` VALUES ('256', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_4', '晚上的测试任务的描述', '1470565869', '1470566580', '120', '60', '4', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470566700');
INSERT INTO `ldg_task` VALUES ('257', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_5', '晚上的测试任务的描述', '1470565869', '1470566700', '120', '60', '5', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470566820');
INSERT INTO `ldg_task` VALUES ('258', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_6', '晚上的测试任务的描述', '1470565869', '1470566820', '120', '60', '6', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470566940');
INSERT INTO `ldg_task` VALUES ('259', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_7', '晚上的测试任务的描述', '1470565869', '1470566940', '120', '60', '7', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470567060');
INSERT INTO `ldg_task` VALUES ('260', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_8', '晚上的测试任务的描述', '1470565869', '1470567060', '120', '60', '8', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470567180');
INSERT INTO `ldg_task` VALUES ('261', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_9', '晚上的测试任务的描述', '1470565869', '1470567180', '120', '60', '9', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470567300');
INSERT INTO `ldg_task` VALUES ('262', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_10', '晚上的测试任务的描述', '1470565869', '1470567300', '120', '60', '10', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470567420');
INSERT INTO `ldg_task` VALUES ('263', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_11', '晚上的测试任务的描述', '1470565869', '1470567420', '120', '60', '11', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470567540');
INSERT INTO `ldg_task` VALUES ('264', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_12', '晚上的测试任务的描述', '1470565869', '1470567540', '120', '60', '12', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470567660');
INSERT INTO `ldg_task` VALUES ('265', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_13', '晚上的测试任务的描述', '1470565869', '1470567660', '120', '60', '13', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470567780');
INSERT INTO `ldg_task` VALUES ('266', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_14', '晚上的测试任务的描述', '1470565869', '1470567780', '120', '60', '14', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470567900');
INSERT INTO `ldg_task` VALUES ('267', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_15', '晚上的测试任务的描述', '1470565869', '1470567900', '120', '60', '15', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568020');
INSERT INTO `ldg_task` VALUES ('268', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_16', '晚上的测试任务的描述', '1470565869', '1470568020', '120', '60', '16', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568140');
INSERT INTO `ldg_task` VALUES ('269', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_17', '晚上的测试任务的描述', '1470565869', '1470568140', '120', '60', '17', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568260');
INSERT INTO `ldg_task` VALUES ('270', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_18', '晚上的测试任务的描述', '1470565869', '1470568260', '120', '60', '18', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568380');
INSERT INTO `ldg_task` VALUES ('271', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_19', '晚上的测试任务的描述', '1470565869', '1470568380', '120', '60', '19', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568500');
INSERT INTO `ldg_task` VALUES ('272', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_20', '晚上的测试任务的描述', '1470565869', '1470568500', '120', '60', '20', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568620');
INSERT INTO `ldg_task` VALUES ('273', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_21', '晚上的测试任务的描述', '1470565869', '1470568620', '120', '60', '21', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568740');
INSERT INTO `ldg_task` VALUES ('274', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_22', '晚上的测试任务的描述', '1470565869', '1470568740', '120', '60', '22', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568860');
INSERT INTO `ldg_task` VALUES ('275', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_23', '晚上的测试任务的描述', '1470565869', '1470568860', '120', '60', '23', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470568980');
INSERT INTO `ldg_task` VALUES ('276', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_24', '晚上的测试任务的描述', '1470565869', '1470568980', '120', '60', '24', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470569100');
INSERT INTO `ldg_task` VALUES ('277', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_25', '晚上的测试任务的描述', '1470565869', '1470569100', '120', '60', '25', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470569220');
INSERT INTO `ldg_task` VALUES ('278', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_26', '晚上的测试任务的描述', '1470565869', '1470569220', '120', '60', '26', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470569340');
INSERT INTO `ldg_task` VALUES ('279', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_27', '晚上的测试任务的描述', '1470565869', '1470569340', '120', '60', '27', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470569460');
INSERT INTO `ldg_task` VALUES ('280', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_28', '晚上的测试任务的描述', '1470565869', '1470569460', '120', '60', '28', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470569580');
INSERT INTO `ldg_task` VALUES ('281', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_29', '晚上的测试任务的描述', '1470565869', '1470569580', '120', '60', '29', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470569700');
INSERT INTO `ldg_task` VALUES ('282', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_30', '晚上的测试任务的描述', '1470565869', '1470569700', '120', '60', '30', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470569820');
INSERT INTO `ldg_task` VALUES ('283', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_31', '晚上的测试任务的描述', '1470565869', '1470569820', '120', '60', '31', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470569940');
INSERT INTO `ldg_task` VALUES ('284', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_32', '晚上的测试任务的描述', '1470565869', '1470569940', '120', '60', '32', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470570060');
INSERT INTO `ldg_task` VALUES ('285', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_33', '晚上的测试任务的描述', '1470565869', '1470570060', '120', '60', '33', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470570180');
INSERT INTO `ldg_task` VALUES ('286', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_34', '晚上的测试任务的描述', '1470565869', '1470570180', '120', '60', '34', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470570300');
INSERT INTO `ldg_task` VALUES ('287', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_35', '晚上的测试任务的描述', '1470565869', '1470570300', '120', '60', '35', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470570420');
INSERT INTO `ldg_task` VALUES ('288', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_36', '晚上的测试任务的描述', '1470565869', '1470570420', '120', '60', '36', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470570540');
INSERT INTO `ldg_task` VALUES ('289', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_37', '晚上的测试任务的描述', '1470565869', '1470570540', '120', '60', '37', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470570660');
INSERT INTO `ldg_task` VALUES ('290', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_38', '晚上的测试任务的描述', '1470565869', '1470570660', '120', '60', '38', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470570780');
INSERT INTO `ldg_task` VALUES ('291', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_39', '晚上的测试任务的描述', '1470565869', '1470570780', '120', '60', '39', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470570900');
INSERT INTO `ldg_task` VALUES ('292', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_40', '晚上的测试任务的描述', '1470565869', '1470570900', '120', '60', '40', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571020');
INSERT INTO `ldg_task` VALUES ('293', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_41', '晚上的测试任务的描述', '1470565869', '1470571020', '120', '60', '41', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571140');
INSERT INTO `ldg_task` VALUES ('294', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_42', '晚上的测试任务的描述', '1470565869', '1470571140', '120', '60', '42', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571260');
INSERT INTO `ldg_task` VALUES ('295', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_43', '晚上的测试任务的描述', '1470565869', '1470571260', '120', '60', '43', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571380');
INSERT INTO `ldg_task` VALUES ('296', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_44', '晚上的测试任务的描述', '1470565869', '1470571380', '120', '60', '44', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571500');
INSERT INTO `ldg_task` VALUES ('297', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_45', '晚上的测试任务的描述', '1470565869', '1470571500', '120', '60', '45', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571620');
INSERT INTO `ldg_task` VALUES ('298', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_46', '晚上的测试任务的描述', '1470565869', '1470571620', '120', '60', '46', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571740');
INSERT INTO `ldg_task` VALUES ('299', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_47', '晚上的测试任务的描述', '1470565869', '1470571740', '120', '60', '47', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571860');
INSERT INTO `ldg_task` VALUES ('300', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_48', '晚上的测试任务的描述', '1470565869', '1470571860', '120', '60', '48', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470571980');
INSERT INTO `ldg_task` VALUES ('301', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_49', '晚上的测试任务的描述', '1470565869', '1470571980', '120', '60', '49', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470572100');
INSERT INTO `ldg_task` VALUES ('302', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_50', '晚上的测试任务的描述', '1470565869', '1470572100', '120', '60', '50', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470572220');
INSERT INTO `ldg_task` VALUES ('303', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_51', '晚上的测试任务的描述', '1470565869', '1470572220', '120', '60', '51', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470572340');
INSERT INTO `ldg_task` VALUES ('304', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_52', '晚上的测试任务的描述', '1470565869', '1470572340', '120', '60', '52', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470572460');
INSERT INTO `ldg_task` VALUES ('305', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_53', '晚上的测试任务的描述', '1470565869', '1470572460', '120', '60', '53', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470572580');
INSERT INTO `ldg_task` VALUES ('306', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_54', '晚上的测试任务的描述', '1470565869', '1470572580', '120', '60', '54', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470572700');
INSERT INTO `ldg_task` VALUES ('307', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_55', '晚上的测试任务的描述', '1470565869', '1470572700', '120', '60', '55', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470572820');
INSERT INTO `ldg_task` VALUES ('308', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_56', '晚上的测试任务的描述', '1470565869', '1470572820', '120', '60', '56', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470572940');
INSERT INTO `ldg_task` VALUES ('309', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_57', '晚上的测试任务的描述', '1470565869', '1470572940', '120', '60', '57', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470573060');
INSERT INTO `ldg_task` VALUES ('310', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_58', '晚上的测试任务的描述', '1470565869', '1470573060', '120', '60', '58', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470573180');
INSERT INTO `ldg_task` VALUES ('311', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_59', '晚上的测试任务的描述', '1470565869', '1470573180', '120', '60', '59', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470573300');
INSERT INTO `ldg_task` VALUES ('312', '晚上的测试任务', '28477e0e-b929-49d6-ba6d-a87be06f9614_60', '晚上的测试任务的描述', '1470565869', '1470573300', '120', '60', '60', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470573420');
INSERT INTO `ldg_task` VALUES ('313', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_0', '810测试任务的说明', '1470835248', '1470835500', '120', '50', '0', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470835620');
INSERT INTO `ldg_task` VALUES ('314', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_1', '810测试任务的说明', '1470835248', '1470835620', '120', '50', '1', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470835740');
INSERT INTO `ldg_task` VALUES ('315', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_2', '810测试任务的说明', '1470835248', '1470835740', '120', '50', '2', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470835860');
INSERT INTO `ldg_task` VALUES ('316', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_3', '810测试任务的说明', '1470835248', '1470835860', '120', '50', '3', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470835980');
INSERT INTO `ldg_task` VALUES ('317', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_4', '810测试任务的说明', '1470835248', '1470835980', '120', '50', '4', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470836100');
INSERT INTO `ldg_task` VALUES ('318', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_5', '810测试任务的说明', '1470835248', '1470836100', '120', '50', '5', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470836220');
INSERT INTO `ldg_task` VALUES ('319', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_6', '810测试任务的说明', '1470835248', '1470836220', '120', '50', '6', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470836340');
INSERT INTO `ldg_task` VALUES ('320', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_7', '810测试任务的说明', '1470835248', '1470836340', '120', '50', '7', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470836460');
INSERT INTO `ldg_task` VALUES ('321', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_8', '810测试任务的说明', '1470835248', '1470836460', '120', '50', '8', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470836580');
INSERT INTO `ldg_task` VALUES ('322', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_9', '810测试任务的说明', '1470835248', '1470836580', '120', '50', '9', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470836700');
INSERT INTO `ldg_task` VALUES ('323', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_10', '810测试任务的说明', '1470835248', '1470836700', '120', '50', '10', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470836820');
INSERT INTO `ldg_task` VALUES ('324', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_11', '810测试任务的说明', '1470835248', '1470836820', '120', '50', '11', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470836940');
INSERT INTO `ldg_task` VALUES ('325', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_12', '810测试任务的说明', '1470835248', '1470836940', '120', '50', '12', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470837060');
INSERT INTO `ldg_task` VALUES ('326', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_13', '810测试任务的说明', '1470835248', '1470837060', '120', '50', '13', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470837180');
INSERT INTO `ldg_task` VALUES ('327', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_14', '810测试任务的说明', '1470835248', '1470837180', '120', '50', '14', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470837300');
INSERT INTO `ldg_task` VALUES ('328', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_15', '810测试任务的说明', '1470835248', '1470837300', '120', '50', '15', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470837420');
INSERT INTO `ldg_task` VALUES ('329', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_16', '810测试任务的说明', '1470835248', '1470837420', '120', '50', '16', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470837540');
INSERT INTO `ldg_task` VALUES ('330', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_17', '810测试任务的说明', '1470835248', '1470837540', '120', '50', '17', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470837660');
INSERT INTO `ldg_task` VALUES ('331', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_18', '810测试任务的说明', '1470835248', '1470837660', '120', '50', '18', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470837780');
INSERT INTO `ldg_task` VALUES ('332', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_19', '810测试任务的说明', '1470835248', '1470837780', '120', '50', '19', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470837900');
INSERT INTO `ldg_task` VALUES ('333', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_20', '810测试任务的说明', '1470835248', '1470837900', '120', '50', '20', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470838020');
INSERT INTO `ldg_task` VALUES ('334', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_21', '810测试任务的说明', '1470835248', '1470838020', '120', '50', '21', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470838140');
INSERT INTO `ldg_task` VALUES ('335', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_22', '810测试任务的说明', '1470835248', '1470838140', '120', '50', '22', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470838260');
INSERT INTO `ldg_task` VALUES ('336', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_23', '810测试任务的说明', '1470835248', '1470838260', '120', '50', '23', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470838380');
INSERT INTO `ldg_task` VALUES ('337', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_24', '810测试任务的说明', '1470835248', '1470838380', '120', '50', '24', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470838500');
INSERT INTO `ldg_task` VALUES ('338', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_25', '810测试任务的说明', '1470835248', '1470838500', '120', '50', '25', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470838620');
INSERT INTO `ldg_task` VALUES ('339', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_26', '810测试任务的说明', '1470835248', '1470838620', '120', '50', '26', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470838740');
INSERT INTO `ldg_task` VALUES ('340', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_27', '810测试任务的说明', '1470835248', '1470838740', '120', '50', '27', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470838860');
INSERT INTO `ldg_task` VALUES ('341', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_28', '810测试任务的说明', '1470835248', '1470838860', '120', '50', '28', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470838980');
INSERT INTO `ldg_task` VALUES ('342', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_29', '810测试任务的说明', '1470835248', '1470838980', '120', '50', '29', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470839100');
INSERT INTO `ldg_task` VALUES ('343', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_30', '810测试任务的说明', '1470835248', '1470839100', '120', '50', '30', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470839220');
INSERT INTO `ldg_task` VALUES ('344', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_31', '810测试任务的说明', '1470835248', '1470839220', '120', '50', '31', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470839340');
INSERT INTO `ldg_task` VALUES ('345', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_32', '810测试任务的说明', '1470835248', '1470839340', '120', '50', '32', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470839460');
INSERT INTO `ldg_task` VALUES ('346', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_33', '810测试任务的说明', '1470835248', '1470839460', '120', '50', '33', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470839580');
INSERT INTO `ldg_task` VALUES ('347', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_34', '810测试任务的说明', '1470835248', '1470839580', '120', '50', '34', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470839700');
INSERT INTO `ldg_task` VALUES ('348', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_35', '810测试任务的说明', '1470835248', '1470839700', '120', '50', '35', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470839820');
INSERT INTO `ldg_task` VALUES ('349', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_36', '810测试任务的说明', '1470835248', '1470839820', '120', '50', '36', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470839940');
INSERT INTO `ldg_task` VALUES ('350', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_37', '810测试任务的说明', '1470835248', '1470839940', '120', '50', '37', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470840060');
INSERT INTO `ldg_task` VALUES ('351', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_38', '810测试任务的说明', '1470835248', '1470840060', '120', '50', '38', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470840180');
INSERT INTO `ldg_task` VALUES ('352', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_39', '810测试任务的说明', '1470835248', '1470840180', '120', '50', '39', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470840300');
INSERT INTO `ldg_task` VALUES ('353', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_40', '810测试任务的说明', '1470835248', '1470840300', '120', '50', '40', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470840420');
INSERT INTO `ldg_task` VALUES ('354', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_41', '810测试任务的说明', '1470835248', '1470840420', '120', '50', '41', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470840540');
INSERT INTO `ldg_task` VALUES ('355', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_42', '810测试任务的说明', '1470835248', '1470840540', '120', '50', '42', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470840660');
INSERT INTO `ldg_task` VALUES ('356', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_43', '810测试任务的说明', '1470835248', '1470840660', '120', '50', '43', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470840780');
INSERT INTO `ldg_task` VALUES ('357', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_44', '810测试任务的说明', '1470835248', '1470840780', '120', '50', '44', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470840900');
INSERT INTO `ldg_task` VALUES ('358', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_45', '810测试任务的说明', '1470835248', '1470840900', '120', '50', '45', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470841020');
INSERT INTO `ldg_task` VALUES ('359', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_46', '810测试任务的说明', '1470835248', '1470841020', '120', '50', '46', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470841140');
INSERT INTO `ldg_task` VALUES ('360', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_47', '810测试任务的说明', '1470835248', '1470841140', '120', '50', '47', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470841260');
INSERT INTO `ldg_task` VALUES ('361', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_48', '810测试任务的说明', '1470835248', '1470841260', '120', '50', '48', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470841380');
INSERT INTO `ldg_task` VALUES ('362', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_49', '810测试任务的说明', '1470835248', '1470841380', '120', '50', '49', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470841500');
INSERT INTO `ldg_task` VALUES ('363', '810测试任务', 'e9f16e4d-8ee0-49aa-b865-764fa75f186c_50', '810测试任务的说明', '1470835248', '1470841500', '120', '50', '50', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470841620');
INSERT INTO `ldg_task` VALUES ('364', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_0', '811早上测试任务', '1470869887', '1470870000', '60', '50', '0', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870060');
INSERT INTO `ldg_task` VALUES ('365', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_1', '811早上测试任务', '1470869887', '1470870060', '60', '50', '1', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870120');
INSERT INTO `ldg_task` VALUES ('366', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_2', '811早上测试任务', '1470869887', '1470870120', '60', '50', '2', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870180');
INSERT INTO `ldg_task` VALUES ('367', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_3', '811早上测试任务', '1470869887', '1470870180', '60', '50', '3', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870240');
INSERT INTO `ldg_task` VALUES ('368', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_4', '811早上测试任务', '1470869887', '1470870240', '60', '50', '4', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870300');
INSERT INTO `ldg_task` VALUES ('369', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_5', '811早上测试任务', '1470869887', '1470870300', '60', '50', '5', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870360');
INSERT INTO `ldg_task` VALUES ('370', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_6', '811早上测试任务', '1470869887', '1470870360', '60', '50', '6', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870420');
INSERT INTO `ldg_task` VALUES ('371', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_7', '811早上测试任务', '1470869887', '1470870420', '60', '50', '7', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870480');
INSERT INTO `ldg_task` VALUES ('372', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_8', '811早上测试任务', '1470869887', '1470870480', '60', '50', '8', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870540');
INSERT INTO `ldg_task` VALUES ('373', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_9', '811早上测试任务', '1470869887', '1470870540', '60', '50', '9', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '4', '0', '1470870600');
INSERT INTO `ldg_task` VALUES ('374', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_10', '811早上测试任务', '1470869887', '1470870600', '60', '50', '10', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470870660');
INSERT INTO `ldg_task` VALUES ('375', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_11', '811早上测试任务', '1470869887', '1470870660', '60', '50', '11', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '1', '0', '1470870720');
INSERT INTO `ldg_task` VALUES ('376', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_12', '811早上测试任务', '1470869887', '1470870720', '60', '50', '12', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470870780');
INSERT INTO `ldg_task` VALUES ('377', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_13', '811早上测试任务', '1470869887', '1470870780', '60', '50', '13', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470870840');
INSERT INTO `ldg_task` VALUES ('378', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_14', '811早上测试任务', '1470869887', '1470870840', '60', '50', '14', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470870900');
INSERT INTO `ldg_task` VALUES ('379', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_15', '811早上测试任务', '1470869887', '1470870900', '60', '50', '15', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470870960');
INSERT INTO `ldg_task` VALUES ('380', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_16', '811早上测试任务', '1470869887', '1470870960', '60', '50', '16', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871020');
INSERT INTO `ldg_task` VALUES ('381', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_17', '811早上测试任务', '1470869887', '1470871020', '60', '50', '17', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871080');
INSERT INTO `ldg_task` VALUES ('382', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_18', '811早上测试任务', '1470869887', '1470871080', '60', '50', '18', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871140');
INSERT INTO `ldg_task` VALUES ('383', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_19', '811早上测试任务', '1470869887', '1470871140', '60', '50', '19', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871200');
INSERT INTO `ldg_task` VALUES ('384', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_20', '811早上测试任务', '1470869887', '1470871200', '60', '50', '20', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871260');
INSERT INTO `ldg_task` VALUES ('385', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_21', '811早上测试任务', '1470869887', '1470871260', '60', '50', '21', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871320');
INSERT INTO `ldg_task` VALUES ('386', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_22', '811早上测试任务', '1470869887', '1470871320', '60', '50', '22', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871380');
INSERT INTO `ldg_task` VALUES ('387', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_23', '811早上测试任务', '1470869887', '1470871380', '60', '50', '23', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871440');
INSERT INTO `ldg_task` VALUES ('388', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_24', '811早上测试任务', '1470869887', '1470871440', '60', '50', '24', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871500');
INSERT INTO `ldg_task` VALUES ('389', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_25', '811早上测试任务', '1470869887', '1470871500', '60', '50', '25', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871560');
INSERT INTO `ldg_task` VALUES ('390', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_26', '811早上测试任务', '1470869887', '1470871560', '60', '50', '26', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871620');
INSERT INTO `ldg_task` VALUES ('391', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_27', '811早上测试任务', '1470869887', '1470871620', '60', '50', '27', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871680');
INSERT INTO `ldg_task` VALUES ('392', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_28', '811早上测试任务', '1470869887', '1470871680', '60', '50', '28', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871740');
INSERT INTO `ldg_task` VALUES ('393', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_29', '811早上测试任务', '1470869887', '1470871740', '60', '50', '29', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871800');
INSERT INTO `ldg_task` VALUES ('394', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_30', '811早上测试任务', '1470869887', '1470871800', '60', '50', '30', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871860');
INSERT INTO `ldg_task` VALUES ('395', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_31', '811早上测试任务', '1470869887', '1470871860', '60', '50', '31', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871920');
INSERT INTO `ldg_task` VALUES ('396', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_32', '811早上测试任务', '1470869887', '1470871920', '60', '50', '32', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470871980');
INSERT INTO `ldg_task` VALUES ('397', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_33', '811早上测试任务', '1470869887', '1470871980', '60', '50', '33', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872040');
INSERT INTO `ldg_task` VALUES ('398', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_34', '811早上测试任务', '1470869887', '1470872040', '60', '50', '34', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872100');
INSERT INTO `ldg_task` VALUES ('399', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_35', '811早上测试任务', '1470869887', '1470872100', '60', '50', '35', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872160');
INSERT INTO `ldg_task` VALUES ('400', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_36', '811早上测试任务', '1470869887', '1470872160', '60', '50', '36', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872220');
INSERT INTO `ldg_task` VALUES ('401', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_37', '811早上测试任务', '1470869887', '1470872220', '60', '50', '37', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872280');
INSERT INTO `ldg_task` VALUES ('402', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_38', '811早上测试任务', '1470869887', '1470872280', '60', '50', '38', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872340');
INSERT INTO `ldg_task` VALUES ('403', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_39', '811早上测试任务', '1470869887', '1470872340', '60', '50', '39', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872400');
INSERT INTO `ldg_task` VALUES ('404', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_40', '811早上测试任务', '1470869887', '1470872400', '60', '50', '40', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872460');
INSERT INTO `ldg_task` VALUES ('405', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_41', '811早上测试任务', '1470869887', '1470872460', '60', '50', '41', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872520');
INSERT INTO `ldg_task` VALUES ('406', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_42', '811早上测试任务', '1470869887', '1470872520', '60', '50', '42', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872580');
INSERT INTO `ldg_task` VALUES ('407', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_43', '811早上测试任务', '1470869887', '1470872580', '60', '50', '43', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872640');
INSERT INTO `ldg_task` VALUES ('408', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_44', '811早上测试任务', '1470869887', '1470872640', '60', '50', '44', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872700');
INSERT INTO `ldg_task` VALUES ('409', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_45', '811早上测试任务', '1470869887', '1470872700', '60', '50', '45', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872760');
INSERT INTO `ldg_task` VALUES ('410', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_46', '811早上测试任务', '1470869887', '1470872760', '60', '50', '46', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872820');
INSERT INTO `ldg_task` VALUES ('411', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_47', '811早上测试任务', '1470869887', '1470872820', '60', '50', '47', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872880');
INSERT INTO `ldg_task` VALUES ('412', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_48', '811早上测试任务', '1470869887', '1470872880', '60', '50', '48', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470872940');
INSERT INTO `ldg_task` VALUES ('413', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_49', '811早上测试任务', '1470869887', '1470872940', '60', '50', '49', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470873000');
INSERT INTO `ldg_task` VALUES ('414', '811早上测试任务', '988354cc-232c-40f8-8132-abc7b4968a13_50', '811早上测试任务', '1470869887', '1470873000', '60', '50', '50', 'var grabber=new Grabber();grabber.operate.loadURL(\'http://www.taobao.com\',1000,function(){Log.success(\'加载页面成功\')},function(){Log.error(\'加载页面失败\')});', '0', '0', '1470873060');

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
