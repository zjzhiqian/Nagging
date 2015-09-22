/**
 * @(#)SysRoleController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月10日 huangzhiqian 创建版本
 */
package com.hzq.system.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.base.BaseController;
import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.MenuTree;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.CommonUtils;
import com.hzq.system.entity.ComboRole;
import com.hzq.system.entity.ShiroUser;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.entity.SysRole;
import com.hzq.system.service.PermissionService;
import com.hzq.system.service.SysRoleService;
import com.hzq.system.util.PermissionUtil;
import com.hzq.system.util.TreeUtil;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("system")
public class SysRoleController extends BaseController{
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value="roles",method=RequestMethod.GET)
	@RequiresPermissions("role:query")
	public String goRolesPage(){
		return "system/roles";
	}
	
	
	@RequestMapping(value="roles",method=RequestMethod.POST)
	@RequiresPermissions("role:query")
	@ResponseBody
	public Grid<SysRole> getRoles(HttpServletRequest request){
			QueryCondition con=CommonUtils.parseRequestToCondition(request);
			Grid<SysRole> grid=sysRoleService.getDataGridResult(con);
		return grid;
	}
	
	
	@RequestMapping(value="roleadd",method=RequestMethod.POST)
	@RequiresPermissions("role:add")
	@ResponseBody
	public Json addRole(@Valid SysRole role,BindingResult result){
		if(result.getErrorCount()>0){
			for(FieldError err:result.getFieldErrors()){
				return new Json(err.getDefaultMessage());
			}
		}
		ShiroUser user=getShiroUser();
		SysRole addrole=new SysRole();
		addrole.setAddtime(new Date());
		addrole.setAdduserid(user.getId()+"");
		addrole.setAuthorityname(role.getAuthorityname());
		addrole.setDescription(role.getDescription());
		addrole.setState("1");
		Json json=sysRoleService.addRole(addrole);
		
		return  json;
	}

	@RequestMapping(value="roleedit",method=RequestMethod.POST)
	@RequiresPermissions("role:edit")
	@ResponseBody
	public Json editRole(@Valid SysRole role ,BindingResult rs){
		if(rs.getErrorCount()>0){
			for(FieldError err:rs.getFieldErrors()){
				return new Json(err.getDefaultMessage());
			}
		}
		ShiroUser user=getShiroUser();
		SysRole editrole=new SysRole();
		editrole.setId(role.getId());
		editrole.setModifytime(new Date());
		editrole.setModifyuserid((user.getId()+""));
		editrole.setAuthorityname(role.getAuthorityname());
		editrole.setDescription(role.getDescription());
		editrole.setState("1");
		Json json=sysRoleService.updateRole(editrole);
		
		return  json;
	}
	
	
	@RequestMapping(value="roledelete/{id}",method=RequestMethod.POST)
	@RequiresPermissions("role:delete")
	@ResponseBody
	public Json deleteRole(@PathVariable("id") String id){
		
		Json json=sysRoleService.deleteRole(id);
		
		return  json;
	}
	
	
	@RequestMapping(value="comborole",method=RequestMethod.POST)
	@RequiresPermissions("user:edit")
	@ResponseBody
	public List<ComboRole> ComboRole(){
		
		List<SysRole> roles=sysRoleService.getAllRoles();
		List<ComboRole> comboroles=new ArrayList<ComboRole>();
		if(roles!=null&&roles.size()>0){
			
			for(SysRole role:roles){
				ComboRole cbrole=new ComboRole();
				cbrole.setId(role.getId()+"");
				cbrole.setText(role.getAuthorityname());
				comboroles.add(cbrole);
			}
		}
		return  comboroles;
	}
	
	@RequestMapping(value="roleDetail/{id}",method=RequestMethod.GET)
	@RequiresPermissions("role:edit")
	public String showRoleDetail(@PathVariable("id")String id,HttpServletRequest req){
		SysRole sysrole=sysRoleService.getRoleByRoleId(id);
		
		if(sysrole!=null){
			req.setAttribute("sysrole", sysrole);
		}
		return  "system/roleDetail";
	}

	/**
	 * 查询角色拥有的权限Tree
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping(value="rolePermissions/{id}",method=RequestMethod.GET)
	@RequiresPermissions("role:edit")
	@ResponseBody

	public List<MenuTree> getPermissionTree(HttpServletRequest req,@PathVariable("id")String id){
		//查询表中所有数据
		List<SysPermission> permissions=permissionService.getAllPermissions();
		//把该角色拥有的权限Checked指定前台选中
		List<SysPermission> permissionList=sysRoleService.getPermissionsByRoleId(id);
		List<String> checkIds=new LinkedList<String>();
		if(permissionList!=null&&permissionList.size()>0){
			for(SysPermission checkedPermission:permissionList){
				checkIds.add(checkedPermission.getId()+"");
			}
		}
		List<MenuTree> treeDataList=PermissionUtil.copyPermissionToTree(permissions,checkIds);
		List<MenuTree> menuTree=TreeUtil.getfatherNode(treeDataList);
		
		return menuTree;
	}
	
	
	
	@RequestMapping(value="rolePermissions/{id}",method=RequestMethod.POST)
	@RequiresPermissions("role:edit")
	@ResponseBody
	public Json updatePermissionTree(HttpServletRequest req,@PathVariable("id")String id){
		String idArray=req.getParameter("idArr");
		
		if(StringUtils.isNotEmpty(id)){
			return permissionService.updateRolePermission(id,idArray);
		}else{
			return new Json("角色id为空");
		}
	}

	
	
}

