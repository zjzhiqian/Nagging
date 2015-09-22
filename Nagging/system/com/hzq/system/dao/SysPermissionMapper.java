package com.hzq.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hzq.common.base.BaseMapper;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.entity.SysRole;


public interface SysPermissionMapper extends BaseMapper<SysPermission>{
	/**
	 * 根据用户拥有的角色查询出用户权限
	 * @param roles  
	 * @return 
	 * @author huangzhiqian
	 * @date 2015年9月2日
	 */
	List<SysPermission> getPermissionBySysRoles(List<SysRole> roles);

	/**角色,权限关联表添加数据
	 * @param id	角色id
	 * @param insertId	权限id
	 * @param id2	添加用户id
	 * @return
	 */
	int insertRolePermission(@Param("id")String id, @Param("permissionId")String permissionId, @Param("adduserid")Integer id2);

	/**角色,权限关联表删除数据
	 * @param id 角色id
	 * @return
	 */
	int deleteRolePermission(@Param("id")String id);
	
	
	
}