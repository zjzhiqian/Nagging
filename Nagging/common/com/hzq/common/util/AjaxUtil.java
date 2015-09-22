/**
 * @(#)AjaxUtil.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月4日 huangzhiqian 创建版本
 */
package com.hzq.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class AjaxUtil {
	/**
	 * 判断是否是Ajax请求
	 * @param request
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月4日
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String requestType = request.getHeader("X-Requested-With");
		if(requestType!=null&&"XMLHttpRequest".equals(requestType)){
			return true;
		}else{
			return false;
		}
	}
}

