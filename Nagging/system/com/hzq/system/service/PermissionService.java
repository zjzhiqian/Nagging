/**
 * @(#)PermissionService.java
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

import com.hzq.common.entity.Json;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.entity.SysRole;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface PermissionService {
	/**
	 * 根据用户拥有的角色查询出用户权限
	 * @param roles
	 * @return 
	 * @author huangzhiqian
	 * @date 2015年9月2日
	 */
	List<SysPermission> getPermissionBySysRoles(List<SysRole> roles);

	/**
	 * @return
	 */
	List<SysPermission> getAllPermissions();

	/**更新权限信息
	 * @param id  角色id
	 * @param idArray 角色拥有的权限id
	 * @return
	 */
	Json updateRolePermission(String id, String idArray);
	/**
	 * 新增权限(菜单)
	 * @param permission
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	Json addPermission(SysPermission permission);
	/**
	 * 删除菜单以及所有子菜单
	 * @param childIds
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	Json deleteMenu(List<String> childIds);
	/**
	 * 根据id查询获取权限
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	SysPermission getPermissionById(int id);
	/**
	 * 更新权限信息
	 * @param permission
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	Json updatePermission(SysPermission permission);



}

