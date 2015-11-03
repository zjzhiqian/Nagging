/**
 * @(#)DataList.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月3日 huangzhiqian 创建版本
 */
package com.hzq.common.entity;

import java.io.Serializable;

/**
 * 
 * easyUI dataList的返回类
 * @author huangzhiqian
 */
public class DataList implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String text;
	private String group;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	@Override
	public String toString() {
		return "DataList [text=" + text + ", group=" + group + "]";
	}
	
	
}

