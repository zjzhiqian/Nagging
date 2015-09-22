package com.hzq.system.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hzq.common.entity.MenuTree;
import com.hzq.system.entity.SysPermission;

/**
 * @author hzq
 *
 * 2015年9月3日 下午12:06:33 
 */
public class PermissionUtil {
	/**
	 * 根据用户的所有权限,筛选出菜单部分
	 * @param permissions
	 * @return
	 */
	public static List<SysPermission>  getMenus(List<SysPermission> permissions){
		List<SysPermission> rsList=new LinkedList<SysPermission>();
		if(permissions!=null&&permissions.size()>0){
			for(SysPermission permission:permissions){
				if(!"3".equals(permission.getType())){
					rsList.add(permission);
				}
			}
		}
		return rsList;
	}
	
	/**
	 * 根据用户的所有权限,筛选出操作权限
	 * @param permissions
	 * @return
	 */
	public static List<SysPermission>  getPermissions(List<SysPermission> permissions){
		List<SysPermission> rsList=new LinkedList<SysPermission>();
		if(permissions!=null&&permissions.size()>0){
			for(SysPermission permission:permissions){
				if("3".equals(permission.getType())){
					rsList.add(permission);
				}
			}
		}	
		return rsList;
	}
	
	/**
	 * 找出指定模块下的Menus  
	 * @param pid 父节点id
	 * @param menus 需要被筛选的Menu
	 * @param type   1,一级子菜单 2,二级子菜单,null查询出所有
	 * @return
	 */
	public static List<SysPermission> FilterMenus(String pid,List<SysPermission> menus,String type){
		List<SysPermission> rsList=new LinkedList<SysPermission>();
		if(pid==null||"".equals(pid.trim())){
			return rsList;
		}
		if(menus!=null&&menus.size()>0){
			for(SysPermission menu:menus){
				String menupid=menu.getPid();
				//根据父id筛选出指定级别的子菜单
				if(pid.trim().equals(menupid.trim())){
					if(type==null){
						rsList.add(menu);
					} else if(type!=null&&type.trim().equals(menu.getType().trim())){
						rsList.add(menu);
					}
				}
			}
			if(rsList.size()>1){
				rsList.sort(new SysPermissionComparator());
			}
		}	
		return rsList;
	}
	
	/**
	 * Permission中对应字段copy到Tree中的对应字段
	 * @param permissions 需要copy的Permission
	 * @param type 菜单级别,1级,2级
	 * @param AllMenus 用户拥有的Permission
	 * @return
	 */
	public static List<MenuTree> copyPermissionToTree(List<SysPermission> permissions,String type ,List<SysPermission> AllMenus){
		List<MenuTree> rsList=new LinkedList<MenuTree>();
		if(permissions!=null&&permissions.size()>0){
			for(SysPermission permission:permissions){
				MenuTree tree=new MenuTree();
				//菜单id
				int id=permission.getId();
				tree.setId(id);
				//菜单名称
				String text=permission.getOperatename();
				if(StringUtils.isNotEmpty(text)){
					tree.setText(text);
				}
				//菜单小图标样式
				String iconCls=permission.getAuth();
				if(StringUtils.isNotEmpty(iconCls)){
					tree.setIconCls(iconCls);
				}
				//菜单描述 close表示关闭
				String description=permission.getDescription();
					if(StringUtils.isNotEmpty(description)){
						tree.setState(description);
				}
				//如果是1级树,而且没有子菜单,那么它也是打开的
				if("1".equals(type)){
					List<SysPermission> sysPermissions=FilterMenus(tree.getId()+"",AllMenus,null);
					if(sysPermissions.size()==0){
						tree.setState("open");
					}
				}
				//2级菜单肯定是打开的(没有子菜单)
				//2级菜单要有url
				if("2".equals(type)){
					tree.setState("open");
					Map<String,Object> map=new HashMap<String,Object>();
					if(permission.getUrl()!=null){
					   map.put("url", permission.getUrl());
					}
					tree.setAttributes(map);
				}	
				rsList.add(tree);
			}
		}
		return rsList;
		
	}
	
	
	/**
	 * Permission中对应字段copy到Tree中的对应字段,并将指定节点checked
	 * @param permissions
	 * @return
	 */
	public static List<MenuTree> copyPermissionToTree(List<SysPermission> permissions,List<String> Checkids){
		List<MenuTree> rsList=new LinkedList<MenuTree>();
		if(permissions!=null&&permissions.size()>0){
			for(SysPermission permission:permissions){
				MenuTree tree=new MenuTree();
				//菜单id
				int id=permission.getId();
				tree.setId(id);
				if(Checkids!=null&&Checkids.contains(id+"")){
					tree.setChecked(true);
				}
				//菜单名称
				String text=permission.getOperatename();
				if(StringUtils.isNotEmpty(text)){
					tree.setText(text);
				}
				//菜单小图标样式
				String iconCls=permission.getAuth();
				if(StringUtils.isNotEmpty(iconCls)){
					tree.setIconCls(iconCls);
				}
				//菜单描述 close表示关闭
				String description=permission.getDescription();
					if(StringUtils.isNotEmpty(description)){
						tree.setState(description);
				}
			    //pid 
			    String pid=permission.getPid();
			    if(StringUtils.isNotEmpty(pid)){
					tree.setPid(pid);
				}
			    
			    Map<String,Object> map=new HashMap<String,Object>();
			    String url=permission.getUrl();
			    if(url!=null){
					   map.put("url", url);
				}
			    String type=permission.getType();
			    if(type!=null){
					map.put("type", type);
				}
			    tree.setAttributes(map);
			    
			    
			    
				rsList.add(tree);
			}
		}
		return rsList;
		
	}

	
	
	
	
	
	
	
	
	
	
	private static class SysPermissionComparator implements Comparator<SysPermission>{
		@Override
		public int compare(SysPermission o1, SysPermission o2) {
			if(o1.getSeq()>o2.getSeq()){
				return 1;
			}
			if(o1.getSeq()<o2.getSeq()){
				return -1;
			}
			return 0;
		}
		
	}

}
