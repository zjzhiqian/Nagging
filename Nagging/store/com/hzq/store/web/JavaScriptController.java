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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("store")
public class JavaScriptController {
	
	
	
	@RequestMapping(value="js/1/{val}")
	public String jsFunction(@PathVariable("val") String value)throws Exception{
		return "store/JavaScript/function/"+value;
	}
	
	@RequestMapping(value="js/2/{val}")
	public String jsObject(@PathVariable("val") String value)throws Exception{
		return "store/JavaScript/object/"+value;
	}
	
}

