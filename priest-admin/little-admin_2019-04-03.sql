# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 192.168.2.101 (MySQL 5.5.40-MariaDB-wsrep)
# Database: little-admin
# Generation Time: 2019-04-03 04:51:28 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `area_code` varchar(64) DEFAULT NULL,
  `city_code` varchar(64) DEFAULT NULL,
  `prov_code` varchar(64) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `user_type` int(1) DEFAULT NULL,
  `join_time` varchar(64) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `email` varchar(32) DEFAULT '',
  `openId` varchar(64) DEFAULT '',
  `avatar` varchar(64) DEFAULT '',
  `position` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `admin_user` WRITE;
/*!40000 ALTER TABLE `admin_user` DISABLE KEYS */;

INSERT INTO `admin_user` (`id`, `username`, `password`, `status`, `area_code`, `city_code`, `prov_code`, `mobile`, `address`, `create_time`, `update_time`, `user_type`, `join_time`, `role_id`, `email`, `openId`, `avatar`, `position`)
VALUES
	(27,'lengligang','e10adc3949ba59abbe56e057f20f883e',0,'东城区','市辖区','北京','15201008961','test',1480391237170,1500516620702,2,'2',1,'','','','');

/*!40000 ALTER TABLE `admin_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table logger
# ------------------------------------------------------------

DROP TABLE IF EXISTS `logger`;

CREATE TABLE `logger` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `action_id` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `log_type` int(11) DEFAULT NULL,
  `table_description` varchar(255) DEFAULT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table logger_action
# ------------------------------------------------------------

DROP TABLE IF EXISTS `logger_action`;

CREATE TABLE `logger_action` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `staff_name` varchar(255) DEFAULT NULL COMMENT '操作人',
  `comment` varchar(1024) DEFAULT NULL COMMENT '操作描述',
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `operation_description` varchar(255) DEFAULT NULL COMMENT '操作类型描述',
  `operation_type` int(11) DEFAULT NULL COMMENT '操作类型',
  `content` text COMMENT '详细内容',
  `user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `logger_action` WRITE;
/*!40000 ALTER TABLE `logger_action` DISABLE KEYS */;

INSERT INTO `logger_action` (`id`, `staff_name`, `comment`, `module_name`, `operation_description`, `operation_type`, `content`, `user_id`, `gmt_create`, `gmt_modified`)
VALUES
	(1,'lengligang','【lengligang】于【2019年04月03日 10时06分18秒】在【后台用户管理】模块执行了【用户列表查询】的操作','后台用户管理','用户列表查询',1,'[1]',27,1554257178399,NULL),
	(2,'lengligang','【lengligang】于【2019年04月03日 11时27分26秒】在【后台用户管理】模块执行了【用户列表查询】的操作','后台用户管理','用户列表查询',1,'[1]',27,1554262046853,NULL),
	(3,'lengligang','【lengligang】于【2019年04月03日 11时32分36秒】在【后台用户管理】模块执行了【用户列表查询】的操作','后台用户管理','用户列表查询',1,'[1]',27,1554262357017,NULL),
	(4,'lengligang','【lengligang】于【2019年04月03日 12时38分54秒】在【后台用户管理】模块执行了【用户列表查询】的操作','后台用户管理','用户列表查询',1,'[1]',27,1554266335344,NULL),
	(5,'lengligang','【lengligang】于【2019年04月03日 12时50分25秒】在【后台用户管理】模块执行了【用户列表查询】的操作','后台用户管理','用户列表查询',1,'[1]',27,1554267025255,NULL);

/*!40000 ALTER TABLE `logger_action` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table logger_content
# ------------------------------------------------------------

DROP TABLE IF EXISTS `logger_content`;

CREATE TABLE `logger_content` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `column_description` varchar(255) DEFAULT NULL,
  `column_name` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `logger_id` bigint(20) DEFAULT NULL,
  `new_value` varchar(255) DEFAULT NULL,
  `old_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table sys_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  `class_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图标样式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`id`, `name`, `url`, `p_id`, `class_name`)
VALUES
	(1,X'E7B3BBE7BB9FE7AEA1E79086',X'23',0,X'676C79706869636F6E20676C79706869636F6E2D636F67'),
	(11,X'E8B584E6BA90E7AEA1E79086',X'2F61646D696E2F6D656E752F6C697374',1,NULL),
	(12,X'E8A792E889B2E7AEA1E79086',X'2F61646D696E2F726F6C652F6C697374',1,NULL),
	(19,X'E7AEA1E79086E59198E7AEA1E79086',X'2F61646D696E2F757365722F6C697374',1,NULL),
	(45,X'E7B3BBE7BB9FE697A5E5BF97',X'2F61646D696E2F6C6F67676572416374696F6E2F6C697374',1,X'');

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `menus` varchar(1024) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `name`, `menus`)
VALUES
	(1,X'E8B685E7BAA7E7AEA1E79086E59198',X'312C31312C31322C31392C34322C34352C36312C36322C37302C37392C38312C36332C36342C36352C36372C36382C36392C37362C37312C37322C37332C37342C37372C37382C38302C38322C3833'),
	(2,X'E5AEA2E69C8DE7AEA1E79086',X'312C322C392C31382C34332C34342C35342C32332C33312C33362C32312C33332C34302C34382C35322C35332C3535'),
	(3,X'E6B58BE8AF95E8A792E889B2',X'312C322C392C32332C33362C3333');

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
