/*
Navicat MySQL Data Transfer

Source Server         : Nagging
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2015-10-18 07:37:22
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
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='功能权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('5', '用户查询', '用户查询权限', 'user:query', null, '3', '1', '15', '1', null, null, '2015-09-23 17:32:05', '1');
INSERT INTO `sys_permission` VALUES ('9', '用户添加', '用户添加权限', 'user:add', null, '3', '1', '15', '2', null, null, '2015-09-23 17:32:07', '1');
INSERT INTO `sys_permission` VALUES ('11', '用户删除', '用户删除权限', 'user:delete', null, '3', '1', '15', '3', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('12', '用户修改', '用户修改权限', 'user:edit', null, '3', '1', '15', '4', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('13', '系统管理', '系统管理--主模块', 'icon-kdm_home', 'system/', '0', '1', '-1', '1', null, null, '2015-09-24 16:11:10', '1');
INSERT INTO `sys_permission` VALUES ('14', '系统权限管理', '权限管理--1级菜单', 'icon-klines', null, '1', '1', '13', '0', null, null, '2015-09-24 16:11:22', '1');
INSERT INTO `sys_permission` VALUES ('15', '用户管理', '用户管理--2级菜单', 'icon-agt_multimedia', '/system/users', '2', '1', ' 14', '2', null, null, '2015-09-24 16:11:58', '1');
INSERT INTO `sys_permission` VALUES ('16', '角色管理', '角色管理--2级菜单', 'icon-desktopshare', '/system/roles', '2', '1', '14', '3', null, null, '2015-09-24 16:12:04', '1');
INSERT INTO `sys_permission` VALUES ('17', '系统资源管理', '资源管理--1级菜单', 'icon-kmines', null, '1', '1', '13', '2', null, null, '2015-09-24 16:51:56', '1');
INSERT INTO `sys_permission` VALUES ('20', '系统资源', '系统资源--2级菜单', 'icon-sipphone', '/system/resource2', '2', '1', '17', '1', null, null, '2015-09-24 16:12:30', '1');
INSERT INTO `sys_permission` VALUES ('22', '角色查询', '角色查询权限', 'role:query', null, '3', '1', '16', '1', null, null, '2015-09-23 17:32:15', '1');
INSERT INTO `sys_permission` VALUES ('23', '角色新增', '角色新增权限', 'role:add', null, '3', '1', '16', '2', null, null, '2015-09-23 17:32:17', '1');
INSERT INTO `sys_permission` VALUES ('24', '角色删除', '角色删除权限', 'role:delete', null, '3', '1', '16', '3', null, null, '2015-09-23 17:32:20', '1');
INSERT INTO `sys_permission` VALUES ('25', '角色修改', '角色修改权限', 'role:edit', null, '3', '1', '16', '4', null, null, '2015-09-23 17:32:23', '1');
INSERT INTO `sys_permission` VALUES ('26', '会话管理', '会话管理--2级菜单', 'icon-agt_member', '/system/sessions', '2', '1', '14', '4', null, null, '2015-09-24 16:12:10', '1');
INSERT INTO `sys_permission` VALUES ('27', '会话查询', '会话查询权限', 'session:query', null, '3', '1', '26', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('28', '会话移除', '会话移除权限', 'session:delete', null, '3', '1', '26', '1', null, null, '2015-09-24 16:49:00', '1');
INSERT INTO `sys_permission` VALUES ('29', '菜单管理', '菜单管理--2级菜单', 'icon-agt_utilities', '/system/menus', '2', '1', '14', '1', null, null, '2015-09-24 16:11:53', '1');
INSERT INTO `sys_permission` VALUES ('32', '菜单查询', '菜单查询权限', 'menu:query', null, '3', '1', '29', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('75', '系统资源2', '系统资源--2级菜单', 'icon-kcmdf', '/system/resource', '2', '1', '17', '2', null, null, '2015-09-24 16:12:36', '1');
INSERT INTO `sys_permission` VALUES ('76', '菜单新增', '菜单新增权限', 'menu:add', null, '3', '1', '29', '1', '2015-09-23 17:33:02', '1', '2015-09-23 17:33:43', '1');
INSERT INTO `sys_permission` VALUES ('77', '菜单删除', '菜单删除权限', 'menu:delete', null, '3', '1', '29', '2', '2015-09-23 17:33:38', '1', null, null);
INSERT INTO `sys_permission` VALUES ('78', '菜单修改', '菜单修改权限', 'menu:edit', null, '3', '1', '29', '3', '2015-09-23 17:34:12', '1', null, null);
INSERT INTO `sys_permission` VALUES ('97', '索引管理', '索引模块', 'icon-kpilot', null, '0', '1', '-1', '2', '2015-09-25 09:37:16', '1', '2015-09-25 09:38:03', '1');
INSERT INTO `sys_permission` VALUES ('98', '数据抓取', '数据抓取', 'icon-bug', null, '1', '1', '97', '0', '2015-10-18 07:04:23', '1', null, null);
INSERT INTO `sys_permission` VALUES ('99', '天涯抓取', '天涯论坛数据抓取', 'icon-ksmiletris', '/lucene/tianya', '2', '1', '98', '0', '2015-10-18 07:06:55', '1', null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='角色表（权限表）';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('29', '超级管理员角色', '拥有全部权限', '1', '2015-09-23 17:37:14', '1', null, null);

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
INSERT INTO `sys_role_permission` VALUES ('29', '13', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '14', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '29', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '32', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '76', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '77', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '78', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '15', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '5', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '9', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '11', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '12', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '16', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '22', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '23', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '24', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '25', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '26', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '27', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '28', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '17', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '20', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '75', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '97', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '98', '2015-10-18 07:07:06', '1');
INSERT INTO `sys_role_permission` VALUES ('29', '99', '2015-10-18 07:07:06', '1');

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

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '123', '9bb0f71b526ede9a67fc5a667a3cdbc15dab7f39', '管理员2', 'f85b3e354a271983e3d88fe26985821d', '321213', '0', '2015-09-05 16:00:24', '1', '2015-10-05 13:55:26', '1', '本地', '2015-10-18 07:34:37', '763');

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
INSERT INTO `sys_user_role` VALUES ('1', '29', '2015-09-29 17:44:40', '1');
