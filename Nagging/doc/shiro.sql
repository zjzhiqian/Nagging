/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2015-09-21 22:34:32
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='功能权限';

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
INSERT INTO `sys_permission` VALUES ('14', '系统权限管理', 'open', 'icon-lock', null, '1', '1', '13', '0', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('15', '用户管理', 'close', 'icon-book_open', '/system/users', '2', '1', ' 14', '5', null, null, null, null);
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
INSERT INTO `sys_permission` VALUES ('29', '菜单管理', '菜单模块', 'icon-ok', '/system/menus', '2', '1', '14', '0', null, null, null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='角色表（权限表）';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('27', '超级管理员', '拥有全部权限', '1', '2015-09-20 07:09:17', '1', null, null);

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
INSERT INTO `sys_role_permission` VALUES ('27', '13', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '14', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '15', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '5', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '9', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '11', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '12', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '16', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '22', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '23', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '24', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '25', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '26', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '27', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '28', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '17', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '19', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '20', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '21', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '18', '2015-09-20 07:41:58', '1');
INSERT INTO `sys_role_permission` VALUES ('27', '29', '2015-09-30 19:38:20', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '123', 'b0ebbeb6d3377b64a5aae70c28159a175f4a0b12', '管理员', '8f56111183cd49b813fe46ee0e529f0e', '321', '1', '2015-09-05 16:00:24', '1', '2015-09-20 22:02:33', '1', '本地', '2015-09-21 20:19:10', '504');
INSERT INTO `sys_user` VALUES ('45', '2131', '356047cb948b1d89e4eb2bfeb8acf4295bf7640e', '小李啊', '15ea28548b46d78b26c64b177c92733f', '132', '1', '2015-09-20 07:10:31', '1', '2015-09-20 20:31:13', '1', '本地', '2015-09-20 07:19:53', '16');

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
INSERT INTO `sys_user_role` VALUES ('1', '27', '2015-09-20 07:09:32', '1');
INSERT INTO `sys_user_role` VALUES ('45', '27', '2015-09-20 20:23:11', '1');
