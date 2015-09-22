package com.hzq.system.util;

import java.util.ArrayList;
import java.util.List;

import com.hzq.common.entity.MenuTree;


/**
 * @author hzq
 *
 * 2015年9月14日 下午7:56:29 
 */
public class TreeUtil {
	
	    public final static List<MenuTree> getfatherNode(List<MenuTree> treeDataList) {
	        List<MenuTree> newTreeDataList = new ArrayList<MenuTree>();
	        for (MenuTree MenuTree : treeDataList) {
	            if("-1".equals(MenuTree.getPid())) {
	                //获取父节点下的子节点
	                MenuTree.setChildren(getChildrenNode(MenuTree.getId()+"",treeDataList));
	                MenuTree.setState("open");
	                newTreeDataList.add(MenuTree);
	            }
	        }
	        return newTreeDataList;
	    }
	     
	    private final static List<MenuTree> getChildrenNode(String pid , List<MenuTree> treeDataList) {
	        List<MenuTree> newTreeDataList = new ArrayList<MenuTree>();
	        for (MenuTree MenuTree : treeDataList) {
	            if(MenuTree.getPid() == null)  continue;
	            //这是一个子节点
	            if(MenuTree.getPid().equals(pid)){
	                //递归获取子节点下的子节点
	                MenuTree.setChildren(getChildrenNode(MenuTree.getId()+"", treeDataList));
	                newTreeDataList.add(MenuTree);
	            }
	        }
	        return newTreeDataList;
	    }
	    
	    
	    
}
