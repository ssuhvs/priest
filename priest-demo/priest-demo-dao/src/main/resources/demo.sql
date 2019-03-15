CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
`my_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名字',
`age` int(11) DEFAULT NULL COMMENT '年龄',
`mobile` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
`create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;