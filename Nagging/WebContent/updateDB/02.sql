DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `type` char(1) DEFAULT '0' COMMENT '日志类型0其他操作1查询2新增3修改4删除',
  `content` text COMMENT '日志内容',
  `keyname` varchar(50) DEFAULT NULL COMMENT '关键字',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `userid` varchar(20) DEFAULT NULL COMMENT '添加人编号',
  `name` varchar(50) DEFAULT NULL COMMENT '添加人姓名',
  `ip` varchar(30) DEFAULT NULL COMMENT 'ip地址',
  `dataid` varchar(20) DEFAULT '' COMMENT '修改记录编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';
