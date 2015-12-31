/**
 * @(#)JsonUtil.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月31日 huangzhiqian 创建版本
 */
package com.hzq.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * Json 操作工具类
 * @author huangzhiqian
 */
public class JsonUtil {
	
	private static ObjectMapper mapper;
	
	private static ObjectMapper getMapper() {
		if(mapper==null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	public static String obj2json(Object obj) {
		try {
			return getMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	
	public static <T> T json2obj(String json,Class<T> clz) {
		try {
			return getMapper().readValue(json,clz);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

