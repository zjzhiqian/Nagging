/**
 * @(#)BBSModual.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月29日 huangzhiqian 创建版本
 */
package com.hzq.lucene.entity;

/**
 * 
 * 淘宝bbs模块的类
 * 
 * @author huangzhiqian
 */
public class BBSModual {
	private String name;
	private String url;
	private String spmId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public String getSpmId() {
		return spmId;
	}

	public void setSpmId(String spmId) {
		this.spmId = spmId;
	}

	@Override
	public String toString() {
		return "BBSModual [name=" + name + ", url=" + url + ", spmId=" + spmId + "]";
	}

	

}
