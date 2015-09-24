package com.hzq.system.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzq.common.base.BaseController;
import com.hzq.common.entity.Json;
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
	
	private static List<Map<String,String>> Listmap=new ArrayList<Map<String,String>>();
	
	public SysMenuController(){
		//读取图标信息存入内存
		if(Listmap.isEmpty()){
			InputStream in=SysMenuController.class.getResourceAsStream("/icon.css"); 
			if(in==null){
				throw new RuntimeException("类 SysMenuController 需要加载图标 icon.css");
			}
			try{
				String str=IOUtils.toString(in);
				String[] rs=str.split("}");
				List<Map<String,String>> tmpList=new ArrayList<Map<String,String>>();
				for(int i=0;i<rs.length;i++){
					if("".equals(rs[i].trim())){
						continue;
					}
					int index=rs[i].indexOf("{");
					String icon=rs[i].trim().substring(1,index-2);
					int index_url1=rs[i].indexOf("(");
					int index_url2=rs[i].indexOf(")");
					String tmpurl=rs[i].substring(index_url1+2,index_url2);
					String url=tmpurl.replaceAll("'", "");
					Map<String,String> map=new HashMap<String,String>();
					map.put("icon",icon);
					map.put("url",url);
					tmpList.add(map);
				}
				Listmap.addAll(tmpList);
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException("com.hzq.system.web.SysMenuController 加载出错");
			}
		}
	}
	
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
		for(SysPermission p:permissions){
			StringBuffer bf=new StringBuffer();
			bf.append(p.getOperatename()).append("[").append(p.getSeq()).append("]");
			p.setOperatename(bf.toString());
		}
		PermissionUtil.sort(permissions);
		List<MenuTree> treeDataList=PermissionUtil.copyPermissionToTree(permissions,null);
		List<MenuTree> menuTree=TreeUtil.getfatherNode(treeDataList);
		return menuTree;
	}
	
	
	@RequestMapping(value="addmenu/{id}",method=RequestMethod.GET)
	@RequiresPermissions("menu:add")
	public String addmenuPage(@PathVariable("id")String id,@RequestParam("text")String text,@RequestParam("type")int type,HttpServletRequest request){
		request.setAttribute("nodeName", text);
		request.setAttribute("pid", id);
		request.setAttribute("ptype",type);
		request.setAttribute("type",type+1);
		return "system/menuAdd";
	}
	
	
	/**
	 * 新增菜单
	 * @param permission
	 * @param result
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	@RequestMapping(value="addmenu",method=RequestMethod.POST)
	@RequiresPermissions("menu:add")
	@ResponseBody
	public Json addmenu(@Valid SysPermission permission,BindingResult result){
		if(result.getErrorCount()>0){
			for(FieldError err:result.getFieldErrors()){
				return new Json(err.getDefaultMessage());
			}
		}
		ShiroUser user=getShiroUser();
		permission.setAddtime(new Date());
		permission.setAdduserid(user.getId()+"");
		return permissionService.addPermission(permission);
	}
	
	
	
	/**
	 * 删除节点以及节点下所有菜单
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	@RequestMapping(value="delmenu/{id}",method=RequestMethod.POST)
	@RequiresPermissions("menu:delete")
	@ResponseBody
	public Json delmenu(@PathVariable("id")String id){
		List<SysPermission> pList=permissionService.getAllPermissions();
		List<String> childIds=TreeUtil.getChildrenPermissionIds(id, pList);
		childIds.add(id);
		return permissionService.deleteMenu(childIds);
	}
	
	/**
	 * 修改菜单(权限)页面
	 * @param id
	 * @param request
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	@RequestMapping(value="editmenu/{id}",method=RequestMethod.GET)
	@RequiresPermissions("menu:edit")
	public String editmenuPage(@PathVariable("id")int id,HttpServletRequest request){
		SysPermission p=permissionService.getPermissionById(id);
		request.setAttribute("permission", p);
		return "system/menuEdit";
	}
	
	/**
	 * 修改菜单(权限)操作
	 * @param id
	 * @param permission
	 * @param result
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	@RequestMapping(value="editmenu/{id}",method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:edit")
	public Json editmenu(@PathVariable("id")int id,@Valid SysPermission permission,BindingResult result){
		if(result.getErrorCount()>0){
			for(FieldError err:result.getFieldErrors()){
				return new Json(err.getDefaultMessage());
			}
		}
		ShiroUser user=getShiroUser();
		permission.setModifytime(new Date());
		permission.setModifyuserid(user.getId()+"");
		return permissionService.updatePermission(permission);
	}
	
	
	
	
	
	
	
	@RequestMapping(value="menuImageList",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,String>> showMenuImages(){
		return Listmap;
	}
	
	
	
}
