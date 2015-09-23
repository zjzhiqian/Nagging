package com.hzq.system.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.hzq.common.entity.MenuTree;
import com.hzq.system.entity.SysPermission;

/**
 * @author hzq
 *
 *         2015年9月14日 下午7:56:29
 */
public class TreeUtil {

	public final static List<MenuTree> getfatherNode(List<MenuTree> treeDataList) {
		List<MenuTree> newTreeDataList = new ArrayList<MenuTree>();
		for (MenuTree MenuTree : treeDataList) {
			if ("-1".equals(MenuTree.getPid())) {
				// 获取父节点下的子节点
				MenuTree.setChildren(getChildrenNode(MenuTree.getId() + "", treeDataList));
				MenuTree.setState("open");
				newTreeDataList.add(MenuTree);
			}
		}
		return newTreeDataList;
	}

	private final static List<MenuTree> getChildrenNode(String pid, List<MenuTree> treeDataList) {
		List<MenuTree> newTreeDataList = new ArrayList<MenuTree>();
		for (MenuTree MenuTree : treeDataList) {
			if (MenuTree.getPid() == null)
				continue;
			// 这是一个子节点
			if (MenuTree.getPid().equals(pid)) {
				// 递归获取子节点下的子节点
				MenuTree.setChildren(getChildrenNode(MenuTree.getId() + "", treeDataList));
				newTreeDataList.add(MenuTree);
			}
		}
		newTreeDataList.sort(new MenuTreeComparator());
		return newTreeDataList;
	}
	
	
	
/*	public  static List<SysPermission> getChildrenPermission(String pid, List<SysPermission> permissions) {
		List<SysPermission> newTreeDataList = new ArrayList<SysPermission>();
		for (SysPermission p : permissions) {
			if (p.getPid() == null)
				continue;
			if (p.getPid().equals(pid)) {
				newTreeDataList.add(p);
				newTreeDataList.addAll(getChildrenPermission(p.getId() + "", permissions));
				
			}
		}
		return newTreeDataList;
	}*/
	
	/**
	 * 获取指定节点所有子节点的id
	 * @param pid
	 * @param permissions
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月23日
	 */
	public  static List<String> getChildrenPermissionIds(String pid, List<SysPermission> permissions) {
		List<String> delList = new ArrayList<String>();
		for (SysPermission p : permissions) {
			if (p.getPid() == null)
				continue;
			if (p.getPid().equals(pid)) {
				delList.add(p.getId()+"");
				delList.addAll(getChildrenPermissionIds(p.getId() + "", permissions));
				
			}
		}
		return delList;
	}
	
	
	
	
	
	
	

	private static class MenuTreeComparator implements Comparator<MenuTree> {

		@Override
		public int compare(MenuTree tree0, MenuTree tree1) {
			if (tree0.getSeq() > tree1.getSeq()) {
				return 1;
			} else if (tree0.getSeq() < tree1.getSeq()) {
				return -1;
			} else
				return 0;
		}

	}

}
