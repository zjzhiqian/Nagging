/*
 * SysUserRoleMapper.java
 * Copyright (huangzhiqian)
 * All rights reserved.
 * -----------------------------------------------
 * 2015-09-10 Created
 */
package com.hzq.system.dao;

import com.hzq.system.entity.SysUserRole;
import com.hzq.common.base.BaseMapper;


public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	/** 根据用户id删除表信息
	 * @param id
	 * @return
	 */
	int deleteByUserId(String id);

	
	



}