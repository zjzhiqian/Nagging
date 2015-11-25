/**
 * @(#)SimpleQueryController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月24日 huangzhiqian 创建版本
 */
package com.hzq.lucene.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("lucene")
public class QueryController {
	
	
	@RequestMapping(value="easyquery",method=RequestMethod.GET)
	public String showSimpleQueryPage(){
		
		return "lucene/simplequery";
	}
	
}

