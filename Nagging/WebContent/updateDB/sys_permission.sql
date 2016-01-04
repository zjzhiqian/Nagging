
SET FOREIGN_KEY_CHECKS=0;


DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '操作id',
  `operatename` varchar(40) DEFAULT NULL COMMENT '菜单(操作)名称',
  `description` varchar(100) DEFAULT NULL COMMENT '菜单(操作)描述  菜单(type=0,1,2)则表示菜单是否展开',
  `auth` varchar(40) DEFAULT NULL COMMENT '按钮权限,如果是菜单(type=0,1,2)则表示菜单图标',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL地址(2级菜单?)',
  `type` char(1) DEFAULT '0' COMMENT '0主菜单1一级子菜单2二级子菜单3操作',
  `state` char(1) DEFAULT '1' COMMENT '状态1有效0无效',
  `pid` varchar(20) DEFAULT '0' COMMENT '父节点',
  `seq` int(5) DEFAULT '0' COMMENT '排序',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `adduserid` varchar(20) DEFAULT NULL COMMENT '添加人用户编号',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifyuserid` varchar(20) DEFAULT NULL COMMENT '修改人用户编号',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8 COMMENT='功能权限';￥


INSERT INTO `sys_permission` VALUES ('5', '用户查询', '用户查询权限', 'user:query', null, '3', '1', '15', '1', null, null, '2015-09-23 17:32:05', '1');
INSERT INTO `sys_permission` VALUES ('9', '用户添加', '用户添加权限', 'user:add', null, '3', '1', '15', '2', null, null, '2015-09-23 17:32:07', '1');
INSERT INTO `sys_permission` VALUES ('11', '用户删除', '用户删除权限', 'user:delete', null, '3', '1', '15', '3', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('12', '用户修改', '用户修改权限', 'user:edit', null, '3', '1', '15', '4', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('13', 'Shiro', '系统管理--主模块', 'icon-kdm_home', 'system/', '0', '1', '-1', '0', null, null, '2015-12-10 14:04:19', '1');
INSERT INTO `sys_permission` VALUES ('14', '系统权限管理', '权限管理--1级菜单', 'icon-klines', null, '1', '1', '13', '0', null, null, '2015-09-24 16:11:22', '1');
INSERT INTO `sys_permission` VALUES ('15', '用户管理', '用户管理--2级菜单', 'icon-agt_multimedia', '/system/users', '2', '1', ' 14', '2', null, null, '2015-09-24 16:11:58', '1');
INSERT INTO `sys_permission` VALUES ('16', '角色管理', '角色管理--2级菜单', 'icon-desktopshare', '/system/roles', '2', '1', '14', '3', null, null, '2015-09-24 16:12:04', '1');
INSERT INTO `sys_permission` VALUES ('22', '角色查询', '角色查询权限', 'role:query', null, '3', '1', '16', '1', null, null, '2015-09-23 17:32:15', '1');
INSERT INTO `sys_permission` VALUES ('23', '角色新增', '角色新增权限', 'role:add', null, '3', '1', '16', '2', null, null, '2015-09-23 17:32:17', '1');
INSERT INTO `sys_permission` VALUES ('24', '角色删除', '角色删除权限', 'role:delete', null, '3', '1', '16', '3', null, null, '2015-09-23 17:32:20', '1');
INSERT INTO `sys_permission` VALUES ('25', '角色修改', '角色修改权限', 'role:edit', null, '3', '1', '16', '4', null, null, '2015-09-23 17:32:23', '1');
INSERT INTO `sys_permission` VALUES ('26', '会话管理', '会话管理--2级菜单', 'icon-agt_member', '/system/sessions', '2', '1', '14', '4', null, null, '2015-09-24 16:12:10', '1');
INSERT INTO `sys_permission` VALUES ('27', '会话查询', '会话查询权限', 'session:query', null, '3', '1', '26', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('28', '会话移除', '会话移除权限', 'session:delete', null, '3', '1', '26', '1', null, null, '2015-09-24 16:49:00', '1');
INSERT INTO `sys_permission` VALUES ('29', '菜单管理', '菜单管理--2级菜单', 'icon-agt_utilities', '/system/menus', '2', '1', '14', '1', null, null, '2015-09-24 16:11:53', '1');
INSERT INTO `sys_permission` VALUES ('32', '菜单查询', '菜单查询权限', 'menu:query', null, '3', '1', '29', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('76', '菜单新增', '菜单新增权限', 'menu:add', null, '3', '1', '29', '1', '2015-09-23 17:33:02', '1', '2015-09-23 17:33:43', '1');
INSERT INTO `sys_permission` VALUES ('77', '菜单删除', '菜单删除权限', 'menu:delete', null, '3', '1', '29', '2', '2015-09-23 17:33:38', '1', null, null);
INSERT INTO `sys_permission` VALUES ('78', '菜单修改', '菜单修改权限', 'menu:edit', null, '3', '1', '29', '3', '2015-09-23 17:34:12', '1', null, null);
INSERT INTO `sys_permission` VALUES ('97', 'Lucene', '索引模块', 'icon-kpilot', null, '0', '1', '-1', '1', '2015-09-25 09:37:16', '1', '2015-12-10 14:04:23', '1');
INSERT INTO `sys_permission` VALUES ('98', '数据生成', '数据生成', 'icon-bug', null, '1', '1', '97', '0', '2015-10-18 07:04:23', '1', '2015-11-24 08:20:33', '1');
INSERT INTO `sys_permission` VALUES ('99', '索引创建', '天涯论坛数据抓取', 'icon-ksmiletris', '/lucene/tianya', '2', '1', '98', '0', '2015-10-18 07:06:55', '1', '2015-11-22 00:08:38', '1');
INSERT INTO `sys_permission` VALUES ('100', '天涯抓取', '天涯抓取', 'lucene:tianyagrab', null, '3', '1', '99', '0', '2015-10-22 22:00:10', '1', null, null);
INSERT INTO `sys_permission` VALUES ('102', '数据索引', '对数据进行索引', 'lucene:tianyaindex', null, '3', '1', '99', '1', '2015-10-24 09:37:40', '1', '2015-10-24 09:41:14', '1');
INSERT INTO `sys_permission` VALUES ('103', '数据检索', 'lucene数据检索', 'icon-search', null, '1', '1', '97', '1', '2015-10-24 16:04:38', '1', null, null);
INSERT INTO `sys_permission` VALUES ('104', '天涯帖子', '天涯帖子数据检索', 'icon-kdvi', '/lucene/tianyaquery', '2', '1', '103', '0', '2015-10-24 16:07:57', '1', null, null);
INSERT INTO `sys_permission` VALUES ('105', '天涯查询', '查询数据的权限', 'lucene:tianyapostquery', null, '3', '1', '99', '2', '2015-10-25 07:49:33', '1', null, null);
INSERT INTO `sys_permission` VALUES ('106', '分词管理', '索引的同义词', 'icon-katomic', null, '1', '1', '97', '2', '2015-11-12 20:12:06', '1', '2015-11-22 00:29:14', '1');
INSERT INTO `sys_permission` VALUES ('107', '同义词查看', '存储在文本文件的同义词', 'icon-kpersonalizer', '/lucene/synonym/1', '2', '1', '106', '0', '2015-11-12 20:13:30', '1', null, null);
INSERT INTO `sys_permission` VALUES ('109', '分词信息查看', '查看分词信息', 'icon-announcements', '/lucene/tokenquery', '2', '1', '106', '0', '2015-11-22 00:27:42', '1', null, null);
INSERT INTO `sys_permission` VALUES ('110', '简单搜索', '简单搜索', 'icon-kmousetool', '/lucene/easyquery', '2', '1', '103', '1', '2015-11-24 08:19:49', '1', null, null);
INSERT INTO `sys_permission` VALUES ('111', '测试100W', '100W数据', 'icon-baobiao_3', '/lucene/complexquery', '2', '1', '103', '2', '2015-11-26 10:23:47', '1', null, null);
INSERT INTO `sys_permission` VALUES ('112', 'Store', '代码仓库', 'icon-kcalc', null, '0', '1', '-1', '2', '2015-12-02 11:05:41', '1', '2015-12-10 14:04:13', '1');
INSERT INTO `sys_permission` VALUES ('113', 'Excel', '通用Excel', 'icon-warehouse_f', null, '1', '1', '112', '0', '2015-12-02 11:08:51', '1', '2015-12-21 21:22:52', '1');
INSERT INTO `sys_permission` VALUES ('114', 'Excel', 'Excel导入导出示例', 'icon-arts', '/store/excel', '2', '1', '113', '0', '2015-12-02 11:12:40', '1', null, null);
INSERT INTO `sys_permission` VALUES ('115', '近实时', 'Lucene近实时测试', 'icon-reload', '/lucene/NRT', '2', '1', '103', '3', '2015-12-11 16:47:56', '1', null, null);
INSERT INTO `sys_permission` VALUES ('116', 'js_function', 'javaScript', 'icon-agt_utilities', null, '1', '1', '112', '1', '2015-12-21 16:26:23', '1', null, null);
INSERT INTO `sys_permission` VALUES ('117', '01_def', 'function', 'icon-agt_multimedia', '/store/js/1/01_def', '2', '1', '116', '0', '2015-12-21 16:27:44', '1', null, null);
INSERT INTO `sys_permission` VALUES ('118', '02_reload', 'function', 'icon-agt_multimedia', '/store/js/1/02_reload', '2', '1', '116', '1', '2015-12-21 20:11:44', '1', null, null);
INSERT INTO `sys_permission` VALUES ('119', '03_tran', 'function', 'icon-agt_multimedia', '/store/js/1/03_tran', '2', '1', '116', '2', '2015-12-21 20:11:45', '1', null, null);
INSERT INTO `sys_permission` VALUES ('120', '04_sort', 'function', 'icon-agt_multimedia', '/store/js/1/04_sort', '2', '1', '116', '3', '2015-12-21 20:11:45', '1', null, null);
INSERT INTO `sys_permission` VALUES ('121', '05_arguments', 'function', 'icon-agt_multimedia', '/store/js/1/05_arguments', '2', '1', '116', '4', '2015-12-21 20:11:45', '1', null, null);
INSERT INTO `sys_permission` VALUES ('122', '06_this', 'function', 'icon-agt_multimedia', '/store/js/1/06_this', '2', '1', '116', '5', '2015-12-21 20:11:45', '1', null, null);
INSERT INTO `sys_permission` VALUES ('123', '07_length', 'function', 'icon-agt_multimedia', '/store/js/1/07_length', '2', '1', '116', '6', '2015-12-21 20:11:46', '1', null, null);
INSERT INTO `sys_permission` VALUES ('124', '08_call_apply', 'function', 'icon-agt_multimedia', '/store/js/1/08_call_apply', '2', '1', '116', '7', '2015-12-21 20:11:46', '1', null, null);
INSERT INTO `sys_permission` VALUES ('125', '09_call', 'function', 'icon-agt_multimedia', '/store/js/1/09_call', '2', '1', '116', '8', '2015-12-21 20:11:47', '1', null, null);
INSERT INTO `sys_permission` VALUES ('127', 'js_object', 'js对象', 'icon-agt_utilities', null, '1', '1', '112', '2', '2015-12-21 20:17:30', '1', null, null);
INSERT INTO `sys_permission` VALUES ('128', '01_simple', 'js对象', 'icon-agt_multimedia', '/store/js/2/01_simple', '2', '1', '127', '0', '2015-12-21 20:18:06', '1', null, null);
INSERT INTO `sys_permission` VALUES ('129', '02_json', 'js对象', 'icon-agt_multimedia', '/store/js/2/02_json', '2', '1', '127', '1', '2015-12-21 20:18:06', '1', null, null);
INSERT INTO `sys_permission` VALUES ('130', '03_factory', 'js对象', 'icon-agt_multimedia', '/store/js/2/03_factory', '2', '1', '127', '2', '2015-12-21 20:18:06', '1', null, null);
INSERT INTO `sys_permission` VALUES ('131', '04_construct', 'js对象', 'icon-agt_multimedia', '/store/js/2/04_construct', '2', '1', '127', '3', '2015-12-21 20:18:06', '1', null, null);
INSERT INTO `sys_permission` VALUES ('132', '05_prototype', 'js对象', 'icon-agt_multimedia', '/store/js/2/05_prototype', '2', '1', '127', '4', '2015-12-21 20:18:06', '1', null, null);
INSERT INTO `sys_permission` VALUES ('133', '06_prototype01_model', 'js对象', 'icon-agt_multimedia', '/store/js/2/06_prototype01_model', '2', '1', '127', '5', '2015-12-21 20:18:07', '1', null, null);
INSERT INTO `sys_permission` VALUES ('134', '07_protitype02_json', 'js对象', 'icon-agt_multimedia', '/store/js/2/07_protitype02_json', '2', '1', '127', '6', '2015-12-21 20:18:07', '1', null, null);
INSERT INTO `sys_permission` VALUES ('135', '08_prototype02_json_problem', 'js对象', 'icon-agt_multimedia', '/store/js/2/08_prototype02_json_problem', '2', '1', '127', '7', '2015-12-21 20:18:07', '1', null, null);
INSERT INTO `sys_permission` VALUES ('136', '09_prototype02', 'js对象', 'icon-agt_multimedia', '/store/js/2/09_prototype02', '2', '1', '127', '8', '2015-12-21 20:18:07', '1', null, null);
INSERT INTO `sys_permission` VALUES ('137', '09_prototype03', 'js对象', 'icon-agt_multimedia', '/store/js/2/09_prototype03', '2', '1', '127', '9', '2015-12-21 20:21:03', '1', null, null);
INSERT INTO `sys_permission` VALUES ('138', '10_prototypeAndcon', 'js对象', 'icon-agt_multimedia', '/store/js/2/10_prototypeAndcon', '2', '1', '127', '10', '2015-12-21 20:21:04', '1', null, null);
INSERT INTO `sys_permission` VALUES ('139', '11_dynamic_prototype', 'js对象', 'icon-agt_multimedia', '/store/js/2/11_dynamic_prototype', '2', '1', '127', '11', '2015-12-21 20:21:04', '1', null, null);
INSERT INTO `sys_permission` VALUES ('140', '12_exntends_prototype01', 'js对象', 'icon-agt_multimedia', '/store/js/2/12_exntends_prototype01', '2', '1', '127', '12', '2015-12-21 20:21:04', '1', null, null);
INSERT INTO `sys_permission` VALUES ('141', '13_exntends_prototype02', 'js对象', 'icon-agt_multimedia', '/store/js/2/13_exntends_prototype02', '2', '1', '127', '13', '2015-12-21 20:22:05', '1', null, null);
INSERT INTO `sys_permission` VALUES ('142', '14_exntends_call01', 'js对象', 'icon-agt_multimedia', '/store/js/2/14_exntends_call01', '2', '1', '127', '14', '2015-12-21 20:22:05', '1', null, null);
INSERT INTO `sys_permission` VALUES ('143', '15_exntends_call02', 'js对象', 'icon-agt_multimedia', '/store/js/2/15_exntends_call02', '2', '1', '127', '15', '2015-12-21 20:22:05', '1', null, null);
INSERT INTO `sys_permission` VALUES ('144', '16_exntends_call03', 'js对象', 'icon-agt_multimedia', '/store/js/2/16_exntends_call03', '2', '1', '127', '16', '2015-12-21 20:22:05', '1', null, null);
INSERT INTO `sys_permission` VALUES ('145', '17_exntends_com', 'js对象', 'icon-agt_multimedia', '/store/js/2/17_exntends_com', '2', '1', '127', '17', '2015-12-21 20:22:06', '1', null, null);
INSERT INTO `sys_permission` VALUES ('146', 'js_closed', 'js闭包', 'icon-agt_utilities', null, '1', '1', '112', '2', '2015-12-23 08:34:02', '1', null, null);
INSERT INTO `sys_permission` VALUES ('147', '18_colsed_seq', 'js闭包', 'icon-agt_multimedia', '/store/js/3/18_colsed_seq', '2', '1', '146', '0', '2015-12-23 08:34:49', '1', null, null);
INSERT INTO `sys_permission` VALUES ('148', '19_colsed_scope', 'js闭包', 'icon-agt_multimedia', '/store/js/3/19_colsed_scope', '2', '1', '146', '1', '2015-12-23 08:38:27', '1', null, null);
INSERT INTO `sys_permission` VALUES ('149', '20_colsed_compare', 'js闭包', 'icon-agt_multimedia', '/store/js/3/20_colsed_compare', '2', '1', '146', '2', '2015-12-23 08:40:57', '1', null, null);
INSERT INTO `sys_permission` VALUES ('150', '21_colsed_var', 'js闭包', 'icon-agt_multimedia', '/store/js/3/21_colsed_var', '2', '1', '146', '3', '2015-12-23 08:40:57', '1', null, null);
INSERT INTO `sys_permission` VALUES ('151', '22_colsed_var2', 'js闭包', 'icon-agt_multimedia', '/store/js/3/22_colsed_var2', '2', '1', '146', '4', '2015-12-23 08:40:57', '1', null, null);
INSERT INTO `sys_permission` VALUES ('152', '23_colsed_this', 'js闭包', 'icon-agt_multimedia', '/store/js/3/23_colsed_this', '2', '1', '146', '5', '2015-12-23 08:40:58', '1', null, null);
INSERT INTO `sys_permission` VALUES ('153', '24_colsed_this2', 'js闭包', 'icon-agt_multimedia', '/store/js/3/24_colsed_this2', '2', '1', '146', '6', '2015-12-23 08:40:58', '1', null, null);
INSERT INTO `sys_permission` VALUES ('154', '25_colsed_blockScope', 'js闭包', 'icon-agt_multimedia', '/store/js/3/25_colsed_blockScope', '2', '1', '146', '7', '2015-12-23 08:40:58', '1', null, null);
INSERT INTO `sys_permission` VALUES ('155', '26_colsed_blockScope2', 'js闭包', 'icon-agt_multimedia', '/store/js/3/26_colsed_blockScope2', '2', '1', '146', '8', '2015-12-23 08:40:58', '1', null, null);
INSERT INTO `sys_permission` VALUES ('156', '27_colsed_private', 'js闭包', 'icon-agt_multimedia', '/store/js/3/27_colsed_private', '2', '1', '146', '9', '2015-12-23 08:40:59', '1', null, null);
INSERT INTO `sys_permission` VALUES ('157', '28_colsed_private2', 'js闭包', 'icon-agt_multimedia', '/store/js/3/28_colsed_private2', '2', '1', '146', '10', '2015-12-23 08:40:59', '1', null, null);


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `authorityname` varchar(30) DEFAULT NULL COMMENT '权限(角色)名称',
  `description` varchar(100) DEFAULT NULL COMMENT '权限(角色)描述',
  `state` char(1) DEFAULT '1' COMMENT '状态1有效0无效',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `adduserid` varchar(20) DEFAULT NULL COMMENT '添加人用户编号',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifyuserid` varchar(20) DEFAULT NULL COMMENT '修改人用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='角色表（权限表）';


INSERT INTO `sys_role` VALUES ('29', '超级管理员角色', '拥有全部权限', '1', '2015-09-23 17:37:14', '1', null, null);


DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` varchar(20) NOT NULL COMMENT '权限id',
  `permission_id` varchar(20) NOT NULL COMMENT '操作id',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `adduserid` varchar(20) NOT NULL COMMENT '添加人用户编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与功能操作关联表';


INSERT INTO `sys_role_permission` VALUES ('29', '13', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '14', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '29', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '32', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '76', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '77', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '78', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '15', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '5', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '9', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '11', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '12', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '16', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '22', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '23', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '24', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '25', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '26', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '27', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '28', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '97', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '98', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '99', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '100', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '102', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '105', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '103', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '104', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '110', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '111', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '115', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '106', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '107', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '109', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '112', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '113', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '114', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '146', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '147', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '148', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '149', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '150', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '151', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '152', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '153', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '154', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '155', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '156', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '157', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '116', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '117', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '118', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '119', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '120', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '121', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '122', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '123', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '124', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '125', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '127', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '128', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '129', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '130', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '131', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '132', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '133', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '134', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '135', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '136', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '137', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '138', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '139', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '140', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '141', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '142', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '143', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '144', '2015-12-23 08:47:45', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '145', '2015-12-23 08:47:45', '1');


DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `password` varchar(40) DEFAULT NULL COMMENT '密码',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `salt` varchar(32) DEFAULT NULL COMMENT '电话',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `state` char(1) NOT NULL DEFAULT '0' COMMENT '0启用1禁用',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `adduserid` varchar(20) DEFAULT NULL COMMENT '添加人用户编号',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifyuserid` varchar(20) DEFAULT NULL COMMENT '修改人用户编号',
  `lastip` varchar(50) DEFAULT '' COMMENT '上次登录IP地址',
  `login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` bigint(20) DEFAULT '0' COMMENT '总登陆次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户表';


INSERT INTO `sys_user` VALUES ('1', '123', '9bb0f71b526ede9a67fc5a667a3cdbc15dab7f39', '智谦', 'f85b3e354a271983e3d88fe26985821d', '321213', '0', '2015-09-05 16:00:24', '1', '2015-11-15 01:36:52', '1', '本地', '2015-12-23 14:47:36', '1426');


DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `userid` int(20) NOT NULL COMMENT '用户编号',
  `role_id` int(20) NOT NULL COMMENT '角色编号',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `adduserid` varchar(20) NOT NULL COMMENT '添加人编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';


INSERT INTO `sys_user_role` VALUES ('1', '29', '2015-09-29 17:44:40', '1');
