package com.hzq.lucene.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("lucene")
public class TianYaPostController {
	
	@RequestMapping(value="tianyaquery",method=RequestMethod.GET)
	public String  showTianYaPostPage(){
		return "lucene/tianyapost";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
