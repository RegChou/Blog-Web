/*
MySQL Backup
Database: muxui_blog
Backup Time: 2020-08-20 14:45:27
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_article`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_article_attribute`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_article_comments`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_auth_token`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_auth_user`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_auth_user_log`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_category`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_category_tags`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_config`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_friendship_link`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_in_tags`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_menu`;
DROP TABLE IF EXISTS `muxui_blog`.`muxui_tags`;
CREATE TABLE `muxui_article` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `author_id` bigint DEFAULT NULL COMMENT '文章创建人',
  `title` varchar(64) NOT NULL COMMENT '文章标题',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '封面图',
  `comments` int DEFAULT '0' COMMENT '评论数',
  `is_comment` smallint DEFAULT '1' COMMENT '是否打开评论 (0 不打开 1 打开 )',
  `category_id` bigint DEFAULT NULL COMMENT '分类主键',
  `status` int DEFAULT '1' COMMENT '状态 1 草稿 2 发布',
  `summary` longtext NOT NULL COMMENT '摘要',
  `views` int DEFAULT '0' COMMENT '浏览次数',
  `weight` int DEFAULT '0' COMMENT '文章权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='文章表';
CREATE TABLE `muxui_article_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` longtext NOT NULL COMMENT '内容',
  `article_id` bigint NOT NULL COMMENT '文章表主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文章中间表';
CREATE TABLE `muxui_article_comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  `parent_id` bigint NOT NULL DEFAULT '0',
  `status` int NOT NULL DEFAULT '0',
  `article_id` bigint NOT NULL,
  `tree_path` varchar(128) DEFAULT NULL COMMENT '层级结构',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='评论表';
CREATE TABLE `muxui_auth_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(256) NOT NULL COMMENT 'token',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `user_id` bigint NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='token表';
CREATE TABLE `muxui_auth_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `social_id` varchar(255) DEFAULT NULL COMMENT '社交账户ID',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `name` varchar(255) DEFAULT NULL COMMENT '别名',
  `role_id` bigint NOT NULL COMMENT '角色主键 1 普通用户 2 admin',
  `introduction` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int DEFAULT '0' COMMENT '0 正常 1 锁定 ',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_email` (`email`) COMMENT '邮箱唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='用户表';
CREATE TABLE `muxui_auth_user_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(20) NOT NULL COMMENT '记录用户id(游客取系统id：-1)',
  `ip` varchar(32) NOT NULL COMMENT 'ip地址',
  `url` varchar(255) NOT NULL COMMENT '请求的url',
  `parameter` varchar(5000) DEFAULT NULL COMMENT '需要记录的参数',
  `device` varchar(255) DEFAULT NULL COMMENT '来自于哪个设备 eg 手机 型号 电脑浏览器',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `code` varchar(10) DEFAULT NULL COMMENT '日志类型',
  `run_time` bigint NOT NULL COMMENT '执行时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `browser_name` varchar(100) DEFAULT NULL COMMENT '浏览器名称',
  `browser_version` varchar(100) DEFAULT NULL COMMENT '浏览器版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='用户行为日志记录表';
CREATE TABLE `muxui_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='分类表';
CREATE TABLE `muxui_category_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tags_id` bigint NOT NULL COMMENT '名称',
  `category_id` bigint NOT NULL COMMENT '分类的主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='分类中间表';
CREATE TABLE `muxui_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `config_key` varchar(128) NOT NULL COMMENT '配置key',
  `config_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置值',
  `type` smallint NOT NULL DEFAULT '0' COMMENT '配置类型',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_99vo6d7ci4wlxruo3gd0q2jq8` (`config_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='配置表';
CREATE TABLE `muxui_friendship_link` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(30) DEFAULT NULL COMMENT '标题',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `logo` varchar(255) NOT NULL COMMENT '文件',
  `href` varchar(255) NOT NULL COMMENT '跳转的路径',
  `sort` smallint NOT NULL DEFAULT '0' COMMENT '排序',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='友情链接表';
CREATE TABLE `muxui_in_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tags_id` bigint NOT NULL COMMENT '标签主键',
  `article_id` bigint NOT NULL COMMENT '文章主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='标签中间表';
CREATE TABLE `muxui_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单Id',
  `title` varchar(32) NOT NULL COMMENT '名称',
  `icon` varchar(255) DEFAULT NULL COMMENT 'icon图标',
  `url` varchar(255) NOT NULL COMMENT '跳转路径',
  `sort` smallint DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单表';
CREATE TABLE `muxui_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='标签表';
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_article` WRITE;
DELETE FROM `muxui_blog`.`muxui_article`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_article_attribute` WRITE;
DELETE FROM `muxui_blog`.`muxui_article_attribute`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_article_comments` WRITE;
DELETE FROM `muxui_blog`.`muxui_article_comments`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_auth_token` WRITE;
DELETE FROM `muxui_blog`.`muxui_auth_token`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_auth_user` WRITE;
DELETE FROM `muxui_blog`.`muxui_auth_user`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_auth_user_log` WRITE;
DELETE FROM `muxui_blog`.`muxui_auth_user_log`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_category` WRITE;
DELETE FROM `muxui_blog`.`muxui_category`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_category_tags` WRITE;
DELETE FROM `muxui_blog`.`muxui_category_tags`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_config` WRITE;
DELETE FROM `muxui_blog`.`muxui_config`;
INSERT INTO `muxui_blog`.`muxui_config` (`id`,`config_key`,`config_value`,`type`) VALUES (1, 'name', '沐旭i', 0),(2, 'domain', 'https://muxui.com/', 0),(3, 'keywords', 'MuxuiBlog', 0),(4, 'description', '一个Java开发的前后端个人博客\n', 0),(5, 'metas', '1.1.1', 0),(6, 'copyright', '', 0),(7, 'icp', '蜀ICP备20017878号', 0),(8, 'qiniu_access_key', '', 1),(9, 'qiniu_secret_key', '', 1),(10, 'qiniu_bucket', '', 1),(11, 'qiniu_image_domain', '', 1),(12, 'cloud_music_id', '', 2),(14, 'store_type', 'cos', 3),(15, 'aliyun_oss_access_key', '', 4),(16, 'aliyun_oss_secret_key', '', 4),(17, 'aliyun_oss_bucket', '', 4),(18, 'aliyun_oss_endpoint', '', 4),(19, 'aliyun_oss_path', '', 4),(20, 'aliyun_oss_image_domain', '', 4),(21, 'cos_access_key', '', 5),(22, 'cos_secret_key', '', 5),(23, 'cos_bucket', '', 5),(24, 'cos_region', '', 5),(25, 'cos_image_domain', '', 5),(26, 'cos_path', '', 5),(27, 'default_path', 'D://muxui', 6),(28, 'default_image_domain', 'https://muxui.com:8085/api/rearinfo', 6);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_friendship_link` WRITE;
DELETE FROM `muxui_blog`.`muxui_friendship_link`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_in_tags` WRITE;
DELETE FROM `muxui_blog`.`muxui_in_tags`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_menu` WRITE;
DELETE FROM `muxui_blog`.`muxui_menu`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `muxui_blog`.`muxui_tags` WRITE;
DELETE FROM `muxui_blog`.`muxui_tags`;
UNLOCK TABLES;
COMMIT;
