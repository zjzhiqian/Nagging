package com.hzq.system.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hzq.common.base.BaseController;
import com.hzq.system.entity.ShiroUser;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.util.PermissionUtil;

/**
 * @author hzq
 *
 * 2015年9月6日 上午8:01:13 
 */
@Controller
@RequestMapping("system")
public class IndexController extends BaseController{
	
	
	@RequestMapping(value="navigation",method=RequestMethod.GET)
	public String getNavigation(HttpServletRequest req){
		ShiroUser user=getShiroUser();
		List<SysPermission> menus=user.getMenus();
		List<SysPermission> permissions=PermissionUtil.FilterMenus("-1", menus,null);
		req.setAttribute("navs", permissions);
		return "index/navigation";
	}
	
	
	@RequestMapping(value="welcome",method=RequestMethod.GET)
	public String goWelcomePage(){
		return "index/welcome";
	}
}
