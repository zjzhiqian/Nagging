package com.hzq.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.hzq.common.entity.QueryCondition;

/**
 * @author hzq
 *
 *         2015年8月17日 下午7:46:42
 */
public class CommonUtils {

	/**
	 * 获取客户端地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf("0:") != -1) {
			ip = "本地";
		}
		return ip;
	}

	/**
	 * 将request请求封装成查询条件
	 * 
	 * @param request
	 * @return
	 */
	public static QueryCondition parseRequestToCondition(HttpServletRequest request) {
		QueryCondition con = new QueryCondition();
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				if (values.length == 1) {
					// 为空则不加入查询条件
					if (StringUtils.isNotEmpty(values[0])) {
						map.put(key, values[0].trim());
					}
				} else {
					map.put(key, values);
				}
			}
		}
		String pg = (String) map.get("page");
		String rw = (String) map.get("rows");
		if (pg != null && rw != null) {
			int page = Integer.parseInt(pg);
			int rows = Integer.parseInt(rw);
			int beginIndex = 0;
			if (rows > 1) {
				beginIndex = (page - 1) * rows;
			}
			map.put("beginIndex", beginIndex);
			map.put("rows", rows);
		}

		con.setCondition(map);
		return con;
	}

	/**
	 * 反序列化
	 * 
	 * @param in
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月20日
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deSerialize(InputStream in, Class<T> clazz) {

		try {
			ObjectInputStream oin = new ObjectInputStream(in);
			return (T) oin.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * object转换Map,对部分数据类型处理
	 * @param object
	 * @param nullToEmpty null字段是否设置为""
	 * @return
	 */
	public static Map<String, Object> describe(Object object,boolean nullToEmpty) {
		Map<String, Object> result = new HashMap<String, Object>();
		Method[] methods = object.getClass().getDeclaredMethods();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(object, (Object[]) null);
					if (value != null) {
						if (value instanceof String) {
							result.put(field, value);
						} else if (value instanceof Date) {
							result.put(field, fmt.format(value));
						} else if (value instanceof Long) {
							result.put(field, value);
						} else {
							result.put(field, value.toString());
						}
					}else{
						if(nullToEmpty){
							result.put(field, "");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
}
