package com.hzq.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzq.system.service.realm.ShiroRealm;

@Controller
public class ClearShiroCache {
	
	//注入realm
	@Autowired
	private ShiroRealm shiroRealm;
	
	@RequestMapping("/clearShiroCache")
	public String clearShiroCache(){
		//清除缓存，将来正常开发要在service调用customRealm.clearCached()
		shiroRealm.clearCached();
		return "success";
	}

}
