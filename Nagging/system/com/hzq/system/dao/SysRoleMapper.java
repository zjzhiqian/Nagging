package com.hzq.system.dao;

import java.util.List;

import com.hzq.common.base.BaseMapper;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.entity.SysRole;


public interface SysRoleMapper extends BaseMapper<SysRole>{
	/**
	 * 根据user_id查询用户拥有角色
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月2日
	 */
	List<SysRole> getRolesByUserId(Integer id);
	/**
	 * 根据RoleId删除User,Role关联表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月14日
	 */
	int deleteUserRole(String id);
	/**
	 * 根据RoleId删除Permission,Role关联表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月14日
	 */
	int deleteRolePermission(String id);
	/**
	 * @param id
	 * @return
	 */
	List<SysPermission> getPermissionsByRoleId(String id);
}