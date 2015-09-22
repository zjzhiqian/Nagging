package com.hzq.common.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author hzq
 *easy-ui MenuTree
 * 2015年9月5日 下午10:31:11 
 */
public class MenuTree implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	/**
	 * 树节点名称
	 */
	private String text;
	/**
	 * 子节点
	 */
	private List<MenuTree> children;
	/**
	 * 前面小图标样式
	 */
	private String iconCls;
	/**
	 * 是否勾选状态
	 */
	private boolean checked =false ;
	/**
	 * 其他参数
	 */
	private Map<String,Object> attributes;
	/**
	 * 是否展开
	 */
	private String state="open";
	
	private String pid;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<MenuTree> getChildren() {
		return children;
	}
	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
