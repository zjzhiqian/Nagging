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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("store")
public class JavaScriptController {
	
	
	@RequestMapping("demo3")
	public String test3()throws Exception{
		return "store/js_study/function/01_def";
	}
	
	
}

