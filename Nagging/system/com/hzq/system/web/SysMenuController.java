package com.hzq.system.web;

import java.util.List;




import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.base.BaseController;
import com.hzq.common.entity.MenuTree;
import com.hzq.system.entity.ShiroUser;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.service.PermissionService;
import com.hzq.system.util.PermissionUtil;
import com.hzq.system.util.TreeUtil;

/**
 * @author hzq
 *
 * 2015年9月5日 下午10:57:51 
 */
@Controller
@RequestMapping("system")
public class SysMenuController extends BaseController{
	
	@Autowired
	private PermissionService permissionService;
	
	
	/**
	 * 根据用户拥有的权限查询Menu
	 * @param pid
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月21日
	 */
	@RequestMapping(value="showMenus/{id}",method=RequestMethod.POST)
	@ResponseBody
	public List<MenuTree>  showMenus(@PathVariable("id")String pid){
		ShiroUser user=getShiroUser();
		List<SysPermission> menus=user.getMenus();
		//根据用户权限树  找出1级Tree   pid为顶级tree的id
		List<SysPermission> permissions=PermissionUtil.FilterMenus(pid, menus,"1");
		List<MenuTree> trees=PermissionUtil.copyPermissionToTree(permissions,"1",menus);
		
		//为每个Tree找到Children
		for(MenuTree tree:trees){
			List<SysPermission> children=PermissionUtil.FilterMenus(tree.getId()+"", menus,"2");
			List<MenuTree> childrenTrees=PermissionUtil.copyPermissionToTree(children,"2",menus);
			tree.setChildren(childrenTrees);
		}
		
		return trees;
	}
	
	/**
	 * 跳转菜单页面
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月22日
	 */
	@RequestMapping(value="menus",method=RequestMethod.GET)
	@RequiresPermissions("menu:query")
	public String menusPage(){
		return "system/menus";
	}
	
	
	/**
	 * 查看所有菜单
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月22日
	 */
	@RequestMapping(value="menus",method=RequestMethod.POST)
	@RequiresPermissions("menu:query")
	@ResponseBody
	public List<MenuTree>  menus(){
		//查询表中所有数据
		List<SysPermission> permissions=permissionService.getAllPermissions();
		List<MenuTree> treeDataList=PermissionUtil.copyPermissionToTree(permissions,null);
		List<MenuTree> menuTree=TreeUtil.getfatherNode(treeDataList);
		return menuTree;
	}
	
	
	@RequestMapping(value="addmenu/{id}",method=RequestMethod.GET)
	public String addmenu(@PathVariable("id")String id,@RequestParam("text")String text,@RequestParam("type")String type,HttpServletRequest request){
		request.setAttribute("nodeName", text);
		request.setAttribute("pid", id);
		request.setAttribute("type",type);
		return "system/menuAdd";
	}
	
}
