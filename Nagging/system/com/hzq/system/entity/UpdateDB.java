/**
 * @(#)UpdateDB.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月20日 chenwei 创建版本
 */
package com.hzq.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 数据库更新内容
 * @author chenwei
 */
public class UpdateDB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2281621074463498311L;
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 更新sql文件名
	 */
	private String name;
	/**
	 * 添加时间
	 */
	private Date addtime;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
}

