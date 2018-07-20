
/*Table structure for table `lm_app` */

DROP TABLE IF EXISTS `lm_app`;

CREATE TABLE `lm_app` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `version` varchar(32) DEFAULT NULL COMMENT '版本号',
  `file_url` varchar(500) DEFAULT NULL COMMENT '文件地址',
  `log` varchar(1000) DEFAULT NULL COMMENT '更新日志',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `md5` varchar(32) DEFAULT NULL COMMENT '文件的MD5',
  `constraint` char(1) DEFAULT NULL COMMENT '是否强制更新(1是0否)',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `lm_banner` */

DROP TABLE IF EXISTS `lm_banner`;

CREATE TABLE `lm_banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel` varchar(10) DEFAULT NULL COMMENT '渠道',
  `page` varchar(20) DEFAULT NULL COMMENT '属于哪个页面的banner',
  `file_id` varchar(32) DEFAULT NULL COMMENT '文件表ID',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='六漠banner表';

/*Table structure for table `lm_file` */

DROP TABLE IF EXISTS `lm_file`;

CREATE TABLE `lm_file` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `lm_type` varchar(50) DEFAULT NULL COMMENT '类型',
  `lm_file` mediumblob COMMENT '文件',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠文件表';

/*Table structure for table `lm_order` */

DROP TABLE IF EXISTS `lm_order`;

CREATE TABLE `lm_order` (
  `id` varchar(32) NOT NULL COMMENT '订单编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `username` varchar(100) DEFAULT NULL COMMENT '借款人姓名',
  `cert_type` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `cert_no` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `order_type` varchar(10) DEFAULT NULL COMMENT '订单类型',
  `order_amt` decimal(18,2) DEFAULT NULL COMMENT '订单金额',
  `state` varchar(2) DEFAULT NULL COMMENT '状态01在线咨询02门店申请03匹配最优银行04银行审批放款05已放款06失败',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `order_date01` datetime DEFAULT NULL COMMENT '状态更新时间',
  `order_date02` datetime DEFAULT NULL COMMENT '状态更新时间',
  `order_date03` datetime DEFAULT NULL COMMENT '状态更新时间',
  `order_date04` datetime DEFAULT NULL COMMENT '状态更新时间',
  `order_date05` datetime DEFAULT NULL COMMENT '状态更新时间',
  `order_date06` datetime DEFAULT NULL COMMENT '状态更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `opr_update_time` datetime DEFAULT NULL COMMENT '操作员更新时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠订单表';

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
  `thumbnail` varchar(32) DEFAULT NULL COMMENT '缩略图ID',
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

/*Table structure for table `lm_sms_log` */

DROP TABLE IF EXISTS `lm_sms_log`;

CREATE TABLE `lm_sms_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `sms_type` varchar(2) DEFAULT NULL COMMENT '短信类型',
  `content` varchar(500) DEFAULT NULL COMMENT '短信内容',
  `send_date` date DEFAULT NULL COMMENT '发送日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠短信流水表';

/*Table structure for table `lm_user` */

DROP TABLE IF EXISTS `lm_user`;

CREATE TABLE `lm_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `username` varchar(100) DEFAULT NULL COMMENT '姓名',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `cert_type` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `cert_no` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `cert_sign` char(1) DEFAULT NULL COMMENT '实名认证标志（1是0否）',
  `cert_remark` varchar(500) DEFAULT NULL COMMENT '实名认证审核评语',
  `cert_positive` varchar(32) DEFAULT NULL COMMENT '证件正面ID',
  `cert_reverse` varchar(32) DEFAULT NULL COMMENT '证件反面ID',
  `cert_hand` varchar(32) DEFAULT NULL COMMENT '手持证件照ID',
  `cert_date` datetime DEFAULT NULL COMMENT '实名认证通过时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `usr_update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
  `opr_update_time` datetime DEFAULT NULL COMMENT '操作员更新时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠用户信息表';

/*Table structure for table `lm_user_his` */

DROP TABLE IF EXISTS `lm_user_his`;

CREATE TABLE `lm_user_his` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户主键',
  `his_type` varchar(10) NOT NULL COMMENT '修改类型（01认证历史02手机号历史）',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `username` varchar(100) DEFAULT NULL COMMENT '姓名',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `cert_type` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `cert_no` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `cert_sign` char(1) DEFAULT NULL COMMENT '实名认证标志（1是0否）',
  `cert_remark` varchar(500) DEFAULT NULL COMMENT '实名认证审核评语',
  `cert_positive` varchar(32) DEFAULT NULL COMMENT '证件正面ID',
  `cert_reverse` varchar(32) DEFAULT NULL COMMENT '证件反面ID',
  `cert_hand` varchar(32) DEFAULT NULL COMMENT '手持证件照ID',
  `cert_date` datetime DEFAULT NULL COMMENT '实名认证通过时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `usr_update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
  `opr_update_time` datetime DEFAULT NULL COMMENT '操作员更新时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='六漠用户信息历史表';

