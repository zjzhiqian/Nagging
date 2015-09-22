/**
 * @(#)RoleService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月2日 huangzhiqian 创建版本
 */
package com.hzq.system.service;

import java.util.List;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.entity.SysRole;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface SysRoleService {
	/**
	 * 根据user_id查询用户拥有角色
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月2日
	 */
	List<SysRole> getRolesByUserId(Integer id);


	Grid<SysRole> getDataGridResult(QueryCondition con);


	Json addRole(SysRole role);


	/**
	 * @param editrole
	 * @return
	 */
	Json updateRole(SysRole editrole);



	/**
	 * @return
	 */
	List<SysRole> getAllRoles();


	Json deleteRole(String id);


	SysRole getRoleByRoleId(String id);


	/**
	 * @param id
	 * @return
	 */
	List<SysPermission> getPermissionsByRoleId(String id);

}

