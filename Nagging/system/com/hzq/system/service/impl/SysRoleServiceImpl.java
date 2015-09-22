/**
 * @(#)RoleServiceImpl.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月2日 huangzhiqian 创建版本
 */
package com.hzq.system.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzq.common.base.BaseService;
import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.system.dao.SysRoleMapper;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.entity.SysRole;
import com.hzq.system.service.SysRoleService;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Service
public class SysRoleServiceImpl extends BaseService<SysRole> implements SysRoleService{
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	
	@Override
	public List<SysRole> getRolesByUserId(Integer id){
		return sysRoleMapper.getRolesByUserId(id);
	}


	@Override
	public Grid<SysRole> getDataGridResult(QueryCondition con) {
		
		return new Grid<SysRole>(sysRoleMapper.conditionQuery(con),sysRoleMapper.conditionCountQuery(con));
	}


	@Override
	public Json addRole(SysRole role) {
		
		if(insert(role)){
			return new Json(true,"添加成功");
		}else{
			return new Json(false);
		}
	}


	@Override
	public Json updateRole(SysRole editrole) {
		if(sysRoleMapper.update(editrole)>0){
			return new Json(true, "修改成功");
		}
		return new Json(false);
	}


	@Override
	public List<SysRole> getAllRoles() {
		
		return sysRoleMapper.conditionQuery(new QueryCondition());
	}


	@Override
	public Json deleteRole(String id) {
		QueryCondition con=new QueryCondition();
		con.getCondition().put("id", id);
		//删除角色表与关联表所有信息
		if(sysRoleMapper.deleteCondition(con)>=0&&sysRoleMapper.deleteUserRole(id)>=0&&sysRoleMapper.deleteRolePermission(id)>=0){
			
			return new Json(true,"删除角色成功");
		}else{
			return new Json(false);
		}
	}


	@Override
	public SysRole getRoleByRoleId(String id) {
		QueryCondition con=new QueryCondition();
		con.getCondition().put("id", id);
		List<SysRole> sysroles=sysRoleMapper.conditionQuery(con);
		if(sysroles!=null&&sysroles.size()>0){
			return sysroles.get(0);
		}
		return null;
	}


	@Override
	public List<SysPermission> getPermissionsByRoleId(String id) {
		List<SysPermission> permissions = sysRoleMapper.getPermissionsByRoleId(id);
		return permissions;
	}


	
}

