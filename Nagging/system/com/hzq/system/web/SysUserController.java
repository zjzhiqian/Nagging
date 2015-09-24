/**
 * @(#)SysUserController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月1日 huangzhiqian 创建版本
 */
package com.hzq.system.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.base.BaseController;
import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.CommonUtils;
import com.hzq.common.util.SaltGenerator;
import com.hzq.system.constant.Constant;
import com.hzq.system.entity.ShiroUser;
import com.hzq.system.entity.SysUser;
import com.hzq.system.entity.SysUserRole;
import com.hzq.system.service.SysUserService;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("system")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	

	/**
	 * 打开修改密码页面
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月4日
	 */
	@RequestMapping(value = "changePsw", method = RequestMethod.GET)
	public String changePassword() {
		return "system/pswChange";
	}
	
	/**
	 * 启用或者禁用用户
	 * @param id
	 * @param state
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月24日
	 */
	@RequestMapping(value="changeState/{id}/{state}", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("user:edit")
	public Json changeState(@PathVariable("id")int id,@PathVariable("state")int state){
		return sysUserService.changeState(id,state);
	}
	/**
	 * 修改密码
	 * 
	 * @param oldpsw
	 * @param newpsw
	 * @return
	 */
	@RequestMapping(value = "changePsw", method = RequestMethod.POST)
	@ResponseBody
	public Json dochangePassword(@RequestParam("oldpsw") String oldpsw,
			@RequestParam("newpsw") String newpsw) {
		Json json = new Json();
		if (checkPswLength(oldpsw) && checkPswLength(newpsw)) {
			json = sysUserService.changPsw(oldpsw, newpsw);
		} else {
			json.setFlag(false);
			json.setMsg("密码长度不正确");
		}

		return json;
	}

	@RequestMapping(value = "users", method = RequestMethod.GET)
	@RequiresPermissions("user:query")
	public String getUsers() {
		return "system/users";
	}

	@RequestMapping(value = "users", method = RequestMethod.POST)
	@RequiresPermissions("user:query")
	@ResponseBody
	public Grid<SysUser> showUsers(HttpServletRequest request) {
		QueryCondition condition = CommonUtils.parseRequestToCondition(request);
		Grid<SysUser> rs = sysUserService.getDataGridResult(condition);
		return rs;
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月4日
	 */
	@RequestMapping(value = "useradd", method = RequestMethod.POST)
	@RequiresPermissions("user:add")
	@ResponseBody
	public Json addUser(@Valid SysUser user, BindingResult result)
			throws Exception {
		if (result.getErrorCount() > 0) {
			for (FieldError err : result.getFieldErrors()) {
				return new Json(err.getDefaultMessage());
			}
		}
		ShiroUser shiroUser = getShiroUser();
		user.setAddtime(new Date());
		user.setAdduserid(shiroUser.getId() + "");
		String salt = SaltGenerator.getSalt(Constant.SALT_LENGTH_HALF);
		SimpleHash hash = new SimpleHash(Constant.ENCRYPT_TYPE, "123456", salt,
				Constant.ENCRYPT_TIMES);
		String psw = hash.toHex();
		user.setPassword(psw);
		user.setSalt(salt);
		Json json = sysUserService.addUser(user);
		return json;
	}

	/**
	 * 编辑用户的方法
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "useredit", method = RequestMethod.POST)
	@RequiresPermissions("user:edit")
	@ResponseBody
	public Json updateUser(@Valid SysUser sysUser, BindingResult result) {
		if (result.getErrorCount() > 0) {
			for (FieldError err : result.getFieldErrors()) {
				return new Json(err.getDefaultMessage());
			}
		}

		SysUser sysuser = new SysUser();
		Json j = new Json();
		if (sysUser.getUsername() == null) {
			j.setMsg("用户名不能为空");
			return j;
		}


		sysuser.setName(sysUser.getName());
		sysuser.setPhone(sysUser.getPhone());
		//sysuser.setUsername(sysUser.getUsername());
		sysuser.setId(sysUser.getId());
		sysuser.setModifytime(new Date());
		sysuser.setModifyuserid(getShiroUser().getId() + "");
		boolean flag = sysUserService.updateUser(sysuser);
		if (flag) {
			j.setFlag(flag);
			j.setMsg("修改用户成功");
			return j;
		} else {
			return new Json(false);
		}
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "userdelete", method = RequestMethod.POST)
	@RequiresPermissions("user:delete")
	@ResponseBody
	public Json deleteUser(@RequestParam("ids") String ids) {
		Json j = new Json();
		String msg = "";
		String errorIds = "";
		if (ids != null) {
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				boolean flag = sysUserService.deleteUser(idArray[i]);
				if (!flag) {
					errorIds = errorIds + idArray[i] + ",";
				}
			}
			if ("".equals(errorIds)) {
				j.setFlag(true);
				msg = "删除选择用户成功";
			} else {
				msg = "删除id:" + errorIds.substring(0, errorIds.length() - 1)
						+ " 失败";
			}
			j.setMsg(msg);
		} else {
			j.setMsg("id为空,无法删除");
		}
		return j;
	}
	
	@RequestMapping(value="authorization/{id}",method=RequestMethod.GET)
	@RequiresPermissions("user:edit")
	public String authorization(@PathVariable("id")int id,HttpServletRequest req){
		SysUser user=sysUserService.getUserById(id);
		String roleIds="";
		if(user!=null){
			List<SysUserRole> sysuserRoles=sysUserService.getUserRolesByUserId(id);
			if(sysuserRoles!=null&&sysuserRoles.size()>0){
				for(SysUserRole userrole:sysuserRoles){
					roleIds=roleIds+userrole.getRoleId()+",";
				}
				roleIds=roleIds.substring(0, roleIds.length()-1);
			}
			user.setRoleIds(roleIds);
			req.setAttribute("edituser", user);
		}
		return "system/userDetail";
	}
	/**
	 * 修改用户权限
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(value="authorization/{id}",method=RequestMethod.POST)
	@RequiresPermissions("user:edit")
	@ResponseBody
	public Json authorizationSave(@PathVariable("id")int id,HttpServletRequest req){
		String ids=req.getParameter("ids");
		return sysUserService.updateRole(id,ids);
	}
	
	
	
	
	
	
	
	

	/**
	 * 校验密码长度 默认6~10
	 * 
	 * @param psw
	 * @return
	 */
	private boolean checkPswLength(String psw) {
		if (psw.length() <= Constant.PSW_MAX_LENGTH
				&& psw.length() >= Constant.PSW_MIN_LENGTH) {
			return true;
		}
		return false;
	}
}
