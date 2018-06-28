USE `yiyun`;

DROP TABLE IF EXISTS `lm_file`;

CREATE TABLE `lm_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lm_type` varchar(50) DEFAULT NULL COMMENT '类型',
  `lm_file` mediumblob COMMENT '文件',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠文件表';;

DROP TABLE IF EXISTS `lm_product`;

CREATE TABLE `lm_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) DEFAULT NULL COMMENT '产品名称',
  `content` varchar(2000) DEFAULT NULL COMMENT '产品介绍',
  `agreemt` varchar(100) DEFAULT NULL COMMENT '居间服务',
  `rate` varchar(50) DEFAULT NULL COMMENT '服务费率',
  `people` varchar(200) DEFAULT NULL COMMENT '适合人群',
  `state` char(1) DEFAULT NULL COMMENT '是否有效：1有效0无效',
  `logo` varchar(100) DEFAULT NULL COMMENT 'logo',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠产品介绍';

DROP TABLE IF EXISTS `lm_release`;

CREATE TABLE `lm_release` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category` varchar(50) DEFAULT NULL COMMENT '种类',
  `brief` varchar(100) DEFAULT NULL COMMENT '发布简介',
  `title` varchar(100) DEFAULT NULL COMMENT '发布标题',
  `content` varchar(2000) DEFAULT NULL COMMENT '发布内容',
  `logo` mediumblob COMMENT 'logo',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠新闻媒体发布';
