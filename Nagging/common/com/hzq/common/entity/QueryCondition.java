/**
 * @(#)EasyQuery.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月8日 huangzhiqian 创建版本
 */
package com.hzq.common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class QueryCondition implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//其他字段可扩展
	
	Map<Object,Object> condition=new HashMap<Object,Object>();

	public Map<Object,Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<Object,Object> condition) {
		this.condition = condition;
	}

		
	
	
}

