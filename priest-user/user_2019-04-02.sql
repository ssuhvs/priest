# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 192.168.2.101 (MySQL 5.5.40-MariaDB-wsrep)
# Database: little_g
# Generation Time: 2019-04-02 01:55:36 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table info_send_sms
# ------------------------------------------------------------

DROP TABLE IF EXISTS `info_send_sms`;

CREATE TABLE `info_send_sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `channel_type` varchar(20) DEFAULT NULL COMMENT '运营商类别',
  `mobile` varchar(128) DEFAULT NULL COMMENT '发送的手机号码',
  `msg_id` varchar(128) DEFAULT NULL COMMENT '消息id',
  `send_status` tinyint(5) DEFAULT NULL COMMENT '发送状态 0：发送成功 1：发送失败',
  `ext_id` bigint(20) DEFAULT NULL COMMENT '扩展id 预留字段',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `sub_seq` tinyint(255) DEFAULT NULL COMMENT '如果是长短信的话 此短信应该所在的序号 0，1，2....n，分别表示第1，2，3....n条',
  `content` varchar(128) DEFAULT NULL COMMENT '发送短信的内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='三方登录表';



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` bigint(20) unsigned NOT NULL,
  `avatar` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birthday` bigint(20) DEFAULT NULL COMMENT '生日',
  `status` tinyint(4) DEFAULT NULL COMMENT '用户状态',
  `mobile` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `create_time` bigint(18) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(18) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UNI_MOBILE` (`avatar`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# Dump of table user_device_token
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_device_token`;

CREATE TABLE `user_device_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` bigint(20) NOT NULL COMMENT '用户的唯一标识',
  `device_id` varchar(45) DEFAULT NULL COMMENT '设备编号',
  `device_type` tinyint(4) DEFAULT NULL,
  `os` varchar(128) DEFAULT NULL,
  `access_token` varchar(128) DEFAULT NULL COMMENT '用户访问的凭证',
  `access_expires_in` bigint(20) DEFAULT NULL,
  `refresh_token` varchar(128) DEFAULT NULL COMMENT '用来刷新access_token的refresh_token',
  `refresh_expires_in` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL COMMENT '记录创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNI_UID_DEVICE` (`uid`,`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备表';



# Dump of table user_third_party_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_third_party_info`;

CREATE TABLE `user_third_party_info` (
  `uid` bigint(20) DEFAULT NULL,
  `provider` tinyint(4) DEFAULT NULL,
  `openid` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `access_token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expires_in` bigint(20) DEFAULT NULL,
  `refresh_token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `re_expires_in` bigint(20) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `unionid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  UNIQUE KEY `u_provider_openid` (`provider`,`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
