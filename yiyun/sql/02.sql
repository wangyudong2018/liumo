USE `yiyun`;

insert into lm_product (title, content, agreemt, rate, people, state, logo, sort, create_time, update_time) values('银行抵押贷款','提供银行抵押贷款居间服务，为客户匹配最优银行，解决客户资金需求。','居间服务','2%','名下有可抵押房产的客户。','1','/static/img/pro_topic_bg01.4548e6d.png','1','2018-06-23 03:59:33','2018-06-23 04:41:56');
insert into lm_product (title, content, agreemt, rate, people, state, logo, sort, create_time, update_time) values('银行信用贷款','提供银行抵押贷款居间服务，为客户匹配最优银行，解决客户资金需求。','居间服务','2%','名下有可抵押房产的客户。','1','/static/img/pro_topic_bg02.4001beb.png','2','2018-06-23 05:14:16','2018-06-23 05:55:58');
insert into lm_product (title, content, agreemt, rate, people, state, logo, sort, create_time, update_time) values('银行按揭贷款','提供银行抵押贷款居间服务，为客户匹配最优银行，解决客户资金需求。','居间服务','2%','名下有可抵押房产的客户。','1','/static/img/pro_topic_bg03.458b8a4.png','3','2018-06-23 04:50:07','2018-06-23 05:14:39');

insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('256','0','六漠科技','','','0','fa fa-globe','600',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('257','256','产品维护','/liumo/lmProduct','liumo:lmProduct:lmProduct','1','fa fa-clone','10',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('258','257','增加','/liumo/lmProduct/add','liumo:lmProduct:add','2','','1',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('259','257','编辑','/liumo/lmProduct/edit','liumo:lmProduct:edit','2','','2',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('260','257','删除','/liumo/lmProduct/remove','liumo:lmProduct:remove','2','','3',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('261','257','批量删除','/liumo/lmProduct/batchRemove','liumo:lmProduct:batchRemove','2','','4',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('262','256','发布维护','/liumo/lmRelease','liumo:lmRelease:lmRelease','1','fa fa-clone','20',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('263','262','增加','/liumo/lmRelease/add','liumo:lmRelease:add','2','','1',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('264','262','编辑','/liumo/lmRelease/edit','liumo:lmRelease:edit','2','','2',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('265','262','删除','/liumo/lmRelease/remove','liumo:lmRelease:remove','2','','3',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('266','262','批量删除','/liumo/lmRelease/batchRemove','liumo:lmRelease:batchRemove','2','','4',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('267','256','banner维护','/liumo/lmFile','liumo:lmFile:lmFile','1','fa fa-clone','20',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('268','267','增加','/liumo/lmFile/add','liumo:lmFile:add','2','','1',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('269','267','编辑','/liumo/lmFile/edit','liumo:lmFile:edit','2','','2',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('270','267','删除','/liumo/lmFile/remove','liumo:lmFile:remove','2','','3',NULL,NULL);
insert into `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) values('271','267','批量删除','/liumo/lmFile/batchRemove','liumo:lmFile:batchRemove','2','','4',NULL,NULL);

commit;
