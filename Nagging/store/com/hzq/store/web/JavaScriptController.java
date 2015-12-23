/**
 * @(#)JavaScriptController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月21日 huangzhiqian 创建版本
 */
package com.hzq.store.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzq.system.entity.ShiroUser;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("store")
public class JavaScriptController {
	
	
	/**
	 * js函数
	 * @param value
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月23日
	 */
	@RequestMapping(value="js/1/{val}")
	public String jsFunction(@PathVariable("val") String value){
		return "store/JavaScript/function/"+value;
	}
	
	/**
	 * js对象
	 * @param value
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月23日
	 */
	@RequestMapping(value="js/2/{val}")
	public String jsObject(@PathVariable("val") String value){
		return "store/JavaScript/object/"+value;
	}
	
	/**
	 * js闭包
	 * @param value
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月23日
	 */
	@RequestMapping(value="js/3/{val}")
	public String jsClose(@PathVariable("val") String value,HttpServletRequest req){
		return "store/JavaScript/closed/"+value;
	}
	
}

