/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2015-09-21 17:27:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '操作id',
  `operatename` varchar(40) DEFAULT NULL COMMENT '菜单(操作)名称',
  `description` varchar(100) DEFAULT NULL COMMENT '菜单(操作)描述  菜单(type=0,1,2)则表示菜单是否展开',
  `auth` varchar(20) DEFAULT NULL COMMENT '按钮权限,如果是菜单(type=0,1,2)则表示菜单图标',
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='功能权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '', '权限1', null, null, '', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('2', '', '权限2', 'item:sele', null, '0', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('3', '', '权限3', 'item:query', null, '3', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('4', '', '权限4', 'item:sele', null, '3', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('5', '用户查询权限', '用户查询权限', 'user:query', null, '3', '1', '15', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('6', null, '权限6', null, null, '3', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('7', null, '权限7', null, null, '3', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('8', null, '权限8', null, null, '3', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('9', '用户添加权限', '用户添加权限', 'user:add', null, '3', '1', '15', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('11', '用户删除权限', '用户删除权限', 'user:delete', null, '3', '1', '15', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('12', '用户修改权限', '用户修改权限', 'user:edit', null, '3', '1', '15', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('13', '系统管理模块', '系统管理模块', null, 'system/', '0', '1', '-1', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('14', '系统权限管理', 'closed', 'icon-lock', null, '1', '1', '13', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('15', '用户管理', 'open', 'icon-ok', '/system/users', '2', '1', ' 14', '5', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('16', '角色管理', 'open', 'icon-ok', '/system/roles', '2', '1', '14', '6', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('17', '系统资源管理', 'closed', 'icon-lock', null, '1', '1', '13', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('18', 'lucence模块', '索引管理模块', null, null, '0', '1', '-1', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('19', '系统资源1', null, 'icon-ok', '/system/resource1', '2', '1', '17', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('20', '系统资源2', null, 'icon-ok', '/system/resource2', '2', '1', '17', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('21', '其他系统管理', 'open', 'icon-lock', null, '1', '1', '13', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('22', '角色查询', null, 'role:query', null, '3', '1', '16', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('23', '角色新增', null, 'role:add', null, '3', '1', '16', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('24', '角色删除', null, 'role:delete', null, '3', '1', '16', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('25', '角色修改', null, 'role:edit', null, '3', '1', '16', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('26', 'session管理', null, null, '/system/sessions', '2', '1', '14', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('27', '会话查询', null, 'session:query', null, '3', '1', '26', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('28', '会话移除', null, 'session:delete', null, '3', '1', '26', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('29', '菜单管理', '菜单管理', 'icon-ok', '/system/menus', '2', '1', '14', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('30', '自定义模块', '索引管理模块', null, null, '0', '1', '-1', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('31', '子节点', '子节点1', 'icon-ok', null, '1', '1', '30', '0', null, null, null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='角色表（权限表）';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管41231', '3123123', '1', '2012-12-13 08:09:47', '', '2015-09-10 22:57:06', '1');
INSERT INTO `sys_role` VALUES ('2', '超级管理员角色', '管理员角色。系统最高级权限', '1', '2012-12-13 08:09:47', '', '2015-08-31 10:09:15', '96076867072775230');
INSERT INTO `sys_role` VALUES ('3', '基础角色', '系统基本操作权限。包括OA、工作流等', '1', '2013-03-28 14:45:13', '10000000', '2015-08-24 13:22:53', '23265749359460343');
INSERT INTO `sys_role` VALUES ('4', '人力资源部经理', '人力资源部经理', '1', '2013-04-08 08:53:47', '22884202852651358', '2015-05-12 15:30:55', '23265749359417997');
INSERT INTO `sys_role` VALUES ('5', '总经理角色', '总经理角色', '1', '2013-04-08 08:57:27', '22884202852651358', '2015-01-26 13:09:38', '23265749359417997');
INSERT INTO `sys_role` VALUES ('6', '部门领导角色', '', '1', '2013-07-31 20:51:07', '22884202852653001', '2015-05-22 11:24:55', '22884202852651358');
INSERT INTO `sys_role` VALUES ('7', '销售内勤', '销售内勤', '1', '2013-12-19 20:10:43', '22884202852651358', '2015-04-27 08:51:49', '23265749359460343');
INSERT INTO `sys_role` VALUES ('9', '31231', '23123123', '1', '2015-09-14 00:13:31', '1', null, null);
INSERT INTO `sys_role` VALUES ('10', '31231', '231231', '1', '2015-09-14 00:13:39', '1', null, null);
INSERT INTO `sys_role` VALUES ('12', '3213', '3123123', '1', '2015-09-14 00:13:55', '1', null, null);
INSERT INTO `sys_role` VALUES ('13', '112', '111', '1', '2015-09-14 00:14:01', '1', null, null);
INSERT INTO `sys_role` VALUES ('14', '3333332', '3333333333', '1', '2015-09-14 00:14:07', '1', null, null);
INSERT INTO `sys_role` VALUES ('15', '3123', '31231', '1', '2015-09-14 00:14:09', '1', null, null);
INSERT INTO `sys_role` VALUES ('16', '312312', '3123123', '1', '2015-09-14 00:14:12', '1', null, null);
INSERT INTO `sys_role` VALUES ('17', '312312', '3123123', '1', '2015-09-14 00:14:15', '1', null, null);
INSERT INTO `sys_role` VALUES ('18', '31231', '2312312', '1', '2015-09-14 00:14:17', '1', null, null);
INSERT INTO `sys_role` VALUES ('19', '312312', '3123', '1', '2015-09-14 00:14:20', '1', null, null);
INSERT INTO `sys_role` VALUES ('20', '123123', '123123', '1', '2015-09-14 00:14:22', '1', null, null);
INSERT INTO `sys_role` VALUES ('21', '31231', '23123', '1', '2015-09-14 00:14:25', '1', null, null);
INSERT INTO `sys_role` VALUES ('22', '31231', '3123123', '1', '2015-09-14 00:14:28', '1', null, null);
INSERT INTO `sys_role` VALUES ('23', '3123', '123123', '1', '2015-09-14 00:14:30', '1', null, null);
INSERT INTO `sys_role` VALUES ('24', '312313', '31231', '1', '2015-09-14 00:14:33', '1', null, null);
INSERT INTO `sys_role` VALUES ('25', '角色1', '角色1', '1', '2015-09-16 21:04:06', '1', null, null);
INSERT INTO `sys_role` VALUES ('26', '213', '312', '1', '2015-09-16 21:04:10', '1', null, null);
INSERT INTO `sys_role` VALUES ('27', '3213', '3123123', '1', '2015-09-16 21:04:13', '1', null, null);
INSERT INTO `sys_role` VALUES ('28', '3333333', '3333333', '1', '2015-09-16 21:04:16', '1', null, null);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` varchar(20) NOT NULL COMMENT '权限id',
  `permission_id` varchar(20) NOT NULL COMMENT '操作id',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `adduserid` varchar(20) NOT NULL COMMENT '添加人用户编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与功能操作关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('6', '4', '2015-09-14 16:28:02', '4');
INSERT INTO `sys_role_permission` VALUES ('6', '5', '2015-09-15 16:28:10', '4');
INSERT INTO `sys_role_permission` VALUES ('5', '26', '2015-09-15 22:52:24', '1');
INSERT INTO `sys_role_permission` VALUES ('5', '17', '2015-09-15 22:52:24', '1');
INSERT INTO `sys_role_permission` VALUES ('7', '23', '2015-09-15 22:56:14', '1');
INSERT INTO `sys_role_permission` VALUES ('7', '24', '2015-09-15 22:56:14', '1');
INSERT INTO `sys_role_permission` VALUES ('7', '26', '2015-09-15 22:56:14', '1');
INSERT INTO `sys_role_permission` VALUES ('7', '17', '2015-09-15 22:56:14', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '14', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '15', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '22', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '24', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '25', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '26', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '17', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '19', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '20', '2015-09-15 22:59:56', '1');
INSERT INTO `sys_role_permission` VALUES ('4', '15', '2015-09-15 23:27:14', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '13', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '14', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '15', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '5', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '9', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '11', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '12', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '16', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '22', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '23', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '24', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '25', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '26', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '27', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '28', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '17', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '19', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '20', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '21', '2015-09-15 23:34:18', '1');
INSERT INTO `sys_role_permission` VALUES ('8', '9', '2015-09-15 23:35:59', '1');
INSERT INTO `sys_role_permission` VALUES ('8', '11', '2015-09-15 23:35:59', '1');
INSERT INTO `sys_role_permission` VALUES ('8', '22', '2015-09-15 23:35:59', '1');
INSERT INTO `sys_role_permission` VALUES ('8', '23', '2015-09-15 23:35:59', '1');
INSERT INTO `sys_role_permission` VALUES ('8', '24', '2015-09-15 23:35:59', '1');
INSERT INTO `sys_role_permission` VALUES ('8', '25', '2015-09-15 23:35:59', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '29', '2015-09-15 16:53:43', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `password` varchar(40) DEFAULT NULL COMMENT '密码',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `salt` varchar(32) DEFAULT NULL COMMENT '电话',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `state` char(1) NOT NULL DEFAULT '1' COMMENT '0禁用1启用',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `adduserid` varchar(20) DEFAULT NULL COMMENT '添加人用户编号',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifyuserid` varchar(20) DEFAULT NULL COMMENT '修改人用户编号',
  `lastip` varchar(50) DEFAULT '' COMMENT '上次登录IP地址',
  `login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` bigint(20) DEFAULT '0' COMMENT '总登陆次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '123', 'dd4ad1a31ca72d5365c3c4612dce63fea6e43114', '管理员', 'dd7aaa7862db275141107d67463f0046', '321', '1', '2015-09-05 16:00:24', '1', '2015-09-10 22:58:40', '1', '本地', '2015-09-21 17:11:50', '464');
INSERT INTO `sys_user` VALUES ('11', '213', '93475fc53abf76545be419c7bafd78f7e1252e14', '7897879', '55e420cb6935b91d421ec0890c0fef61', '897987', '1', null, null, '2015-09-13 11:29:31', '1', '本地', '2015-09-15 23:20:00', '29');
INSERT INTO `sys_user` VALUES ('27', '32131', 'd9197bf34b50f53d58a0d9c6f378fc0ea021dfdc', '31231', '13c17767373843c5720c6c93d5e240ac', '231232131', '1', '2015-09-05 14:36:16', '1', '2015-09-13 11:24:45', '1', '', null, '1');
INSERT INTO `sys_user` VALUES ('32', '1231231', 'fa29c558d7b2cd4310d1b47e009dbcfeebb60bdb', '12312213', '55bd066aab47bef9f6ccc61e981a5123', '123123', '1', '2015-09-05 15:02:26', '1', '2015-09-13 11:23:47', '1', '', null, '1');
INSERT INTO `sys_user` VALUES ('33', '123456', '8ab1e7eafbcb9b7a4a6df814fd22c6953aa5f05a', '123456', '1b0ef5f3bf51841775a3a41aee279304', '111111', '1', '2015-09-05 15:59:26', '1', null, null, '', null, '0');
INSERT INTO `sys_user` VALUES ('36', '31231312', '72b625a8cc28ec4fafafc499e64477749d57c5e1', '12312332', '90c25c25eca4b1196a78a6e4a971641e', '1231233', '1', '2015-09-06 01:25:41', '1', '2015-09-09 20:50:54', '1', '', null, '0');
INSERT INTO `sys_user` VALUES ('37', '332', 'c529d32558a6d9b38cd231b67b9a381aa1c6154c', '3213', '2f6aefe1f04b7a6573804c43cdad4598', '31231', '1', '2015-09-08 21:12:27', '1', null, null, '', null, '0');
INSERT INTO `sys_user` VALUES ('38', '321312', '920fd48300be831da3ec3965aa84e472191e6906', '312313', 'd84edeb057aeae0226769c1ddba463bb', '123123123', '1', '2015-09-08 21:12:45', '1', null, null, '', null, '0');
INSERT INTO `sys_user` VALUES ('39', '1234', '7d6c61b26363d00442f325393e43055f5a83e9ca', '12345', 'ba4b57cba5c0d39fba9d6fe40b00b59d', '123456', '1', '2015-09-08 22:13:20', '1', null, null, '', null, '0');
INSERT INTO `sys_user` VALUES ('40', '666', '699bb364eaa15c971876eae4af777db13c47879e', '312313', 'a452e7ff18994d5a6a9bde55844ff962', '123123', '1', '2015-09-08 22:13:58', '1', null, null, '', null, '0');
INSERT INTO `sys_user` VALUES ('42', '312312', '670a520d314122ddc6d510fee6d610cbad18557d', '31231', '119c763a074af693480e30d5b47940a7', '312312', '1', '2015-09-08 22:16:37', '1', null, null, '', null, '0');
INSERT INTO `sys_user` VALUES ('43', '321', 'ad14d2affb818d59b3c92fb796e901f6c21a373f', '11111', '895632c670db382d69f7d0a480eb2b20', '11111', '1', '2015-09-09 21:57:26', '1', null, null, '', null, '0');
INSERT INTO `sys_user` VALUES ('44', '3123', 'b8c5dfca99d26d06ea41f1a7323a950b4dcc9743', '312312', '3c78e92d3ef1f638dc6e1262cd793da7', '3123123', '1', '2015-09-10 21:19:00', '1', null, null, '', null, '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `userid` int(20) NOT NULL COMMENT '用户编号',
  `role_id` int(20) NOT NULL COMMENT '角色编号',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `adduserid` varchar(20) NOT NULL COMMENT '添加人编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('11', '1', '2015-09-16 23:10:46', '1');
INSERT INTO `sys_user_role` VALUES ('11', '2', '2015-09-16 23:10:46', '1');
INSERT INTO `sys_user_role` VALUES ('33', '24', '2015-09-16 23:11:49', '1');
INSERT INTO `sys_user_role` VALUES ('1', '3', '2015-09-16 23:17:16', '1');
