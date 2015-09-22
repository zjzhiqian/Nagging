package com.hzq.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author hzq
 *
 * 2015年9月6日 上午8:01:13 
 */
@Controller
@RequestMapping("system")
public class IndexController {
	
	
	@RequestMapping(value="navigation",method=RequestMethod.GET)
	public String getNavigation(){
		return "index/navigation";
	}
	
	
	@RequestMapping(value="welcome",method=RequestMethod.GET)
	public String goWelcomePage(){
		return "index/welcome";
	}
}
