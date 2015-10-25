package com.hzq.common.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	 * map对象转换为Object对象
	 * 
	 * @param map
	 * @param class1
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public static <T> T mapToObject(HashMap<String, Object> map, Class<T> class1)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, ParseException {

		Field[] fields = class1.getDeclaredFields();
		T t = null;
		if (fields.length > 0) {
			t = class1.newInstance();
		}
		boolean flag; // 该字段是否可访问，private/protected/public
		for (Field field : fields) {
			if (map.containsKey(field.getName())
					&& map.get(field.getName()) != null) {
				flag = false;
				// 不可访问
				if (!field.isAccessible()) {
					field.setAccessible(true);
					flag = true;
				}
				// 时间类型的转换
				if ((field.getType() == java.util.Date.class || field.getType() == java.sql.Date.class)
						&& map.get(field.getName()).getClass() != field
								.getType()) {
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					field.set(t,
							format.parse((String) map.get(field.getName())));
					// Timestamp转换
				} else if (field.getType() == java.sql.Timestamp.class
						&& map.get(field.getName()).getClass() != field
								.getType()) {
					field.set(t, Timestamp.valueOf((String) map.get(field
							.getName())));

					// Long
				} else if (field.getType() == java.lang.Long.class
						&& map.get(field.getName()).getClass() != field
								.getType()) {

					field.set(t,
							Long.valueOf((String) map.get(field.getName())));

					// Integer
				} else if (field.getType() == java.lang.Integer.class
						&& map.get(field.getName()).getClass() != field
								.getType()) {

					field.set(t,
							Integer.parseInt((String) map.get(field.getName())));

					// int
				} else if ((field.getType() == int.class || field.getType() == java.lang.Integer.class)
						&& map.get(field.getName()).getClass() != field
								.getType()) {

					field.set(t,
							Integer.parseInt((String) map.get(field.getName())));

					// String
				} else {

					field.set(t, map.get((String) field.getName()));
				}

				// 还原访问属性
				if (flag) {
					field.setAccessible(false);
				}
			}
		}

		return t;
	}
	
	/**
	 * 将request请求封装成查询条件
	 * @param request
	 * @return
	 */
	public static QueryCondition parseRequestToCondition(HttpServletRequest request){
		QueryCondition con=new QueryCondition();
		Map<Object,Object> map=new HashMap<Object,Object>();
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				if(values.length==1){
					//为空则不加入查询条件
					if(StringUtils.isNotEmpty(values[0])){
						map.put(key,values[0].trim());
					}
				}else{
					map.put(key,values);
				}
			}
		}
		String pg=(String) map.get("page");
		String rw=(String) map.get("rows");
		if(pg!=null&&rw!=null){
			int page=Integer.parseInt(pg);
			int rows=Integer.parseInt(rw);
			int beginIndex=0;
			if(rows>1){
				beginIndex = (page-1)*rows;
			}
			map.put("beginIndex", beginIndex);
			map.put("rows", rows);
		}
		
		
		con.setCondition(map);
		return con;
	}
}
	
