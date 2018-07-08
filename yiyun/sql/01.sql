USE `yiyun`;

/*Table structure for table `lm_banner` */

DROP TABLE IF EXISTS `lm_banner`;

CREATE TABLE `lm_banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel` varchar(10) DEFAULT NULL COMMENT '渠道',
  `page` varchar(20) DEFAULT NULL COMMENT '属于哪个页面的banner',
  `file_id` bigint(20) DEFAULT NULL COMMENT '文件表ID',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠banner表';

/*Table structure for table `lm_file` */

DROP TABLE IF EXISTS `lm_file`;

CREATE TABLE `lm_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lm_type` varchar(50) DEFAULT NULL COMMENT '类型',
  `lm_file` mediumblob COMMENT '文件',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠文件表';

/*Table structure for table `lm_product` */

DROP TABLE IF EXISTS `lm_product`;

CREATE TABLE `lm_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `prd_type` varchar(10) DEFAULT NULL COMMENT '产品类型',
  `title` varchar(100) DEFAULT NULL COMMENT '产品名称',
  `content` varchar(2000) DEFAULT NULL COMMENT '产品介绍',
  `agreemt` varchar(200) DEFAULT NULL COMMENT '居间服务和费率',
  `people` varchar(200) DEFAULT NULL COMMENT '适合人群',
  `state` char(1) DEFAULT NULL COMMENT '是否有效：1有效0无效',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='六漠产品介绍';

/*Table structure for table `lm_recruit` */

DROP TABLE IF EXISTS `lm_recruit`;

CREATE TABLE `lm_recruit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `workplace` varchar(50) DEFAULT NULL COMMENT '工作地点',
  `rec_type` varchar(10) DEFAULT NULL COMMENT '招聘类型（01社会招聘02校园招聘）',
  `category` varchar(10) DEFAULT NULL COMMENT '职位分类',
  `job_title` varchar(100) DEFAULT NULL COMMENT '职位名称',
  `job_nature` varchar(100) DEFAULT NULL COMMENT '工作性质',
  `salary_range` varchar(100) DEFAULT NULL COMMENT '薪资范围',
  `hiring` varchar(50) DEFAULT NULL COMMENT '招聘人数',
  `job_duty` varchar(4000) DEFAULT NULL COMMENT '工作职责',
  `job_quality` varchar(4000) DEFAULT NULL COMMENT '任职资格',
  `state` char(1) DEFAULT NULL COMMENT '是否有效（1有效0无效）',
  `rel_date` date DEFAULT NULL COMMENT '发布时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠招聘';

/*Table structure for table `lm_release` */

DROP TABLE IF EXISTS `lm_release`;

CREATE TABLE `lm_release` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category` varchar(10) DEFAULT NULL COMMENT '发布类型（01新闻发布；02媒体报道）',
  `thumbnail` bigint(20) DEFAULT NULL COMMENT '缩略图ID',
  `title` varchar(150) DEFAULT NULL COMMENT '发布标题',
  `brief` varchar(300) DEFAULT NULL COMMENT '发布简介',
  `original` varchar(100) DEFAULT NULL COMMENT '原创者',
  `stick` char(1) DEFAULT NULL COMMENT '是否置顶（1是；0否）',
  `state` char(1) DEFAULT NULL COMMENT '是否有效（1是；0否）',
  `out_chain` varchar(300) DEFAULT NULL COMMENT '外部链接',
  `content` mediumtext COMMENT '新闻内容',
  `terminal` varchar(10) DEFAULT NULL COMMENT '发布终端（00全部；01PC；02MB）',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `release_date` date DEFAULT NULL COMMENT '发布日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠新闻媒体发布';
