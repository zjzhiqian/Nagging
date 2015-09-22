/**
 * @(#)PermissionServiceImpl.java
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzq.common.base.BaseService;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.system.dao.SysPermissionMapper;
import com.hzq.system.entity.ShiroUser;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.entity.SysRole;
import com.hzq.system.service.PermissionService;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Service
public class PermissionServiceImpl extends BaseService<SysPermission>implements PermissionService {
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	@Override
	public List<SysPermission> getPermissionBySysRoles(List<SysRole> roles) {
		return sysPermissionMapper.getPermissionBySysRoles(roles);
	}
	
	
	@Override
	public List<SysPermission> getAllPermissions() {
		
		return sysPermissionMapper.conditionQuery(new QueryCondition());
	}


	@Override
	public Json updateRolePermission(String id, String idArray) {
		
		
		ShiroUser user=getShiroUser();
		if(sysPermissionMapper.deleteRolePermission(id)>=0){
			boolean flag=false;String msg="修改权限信息成功";
			if(StringUtils.isNotEmpty(idArray)){
				String[] arrsys=idArray.split(",");
				for(String insertId:arrsys){
					flag=sysPermissionMapper.insertRolePermission(id,insertId,user.getId())>0;
					if(!flag){
						msg="修改权限信息出错";
						break;
					}
				}
				return new Json(flag,msg);
			}else{
				return new Json(true,msg);
			}
		}else{
			return new Json(false);
		}
	}
	

}

