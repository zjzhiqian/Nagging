package com.hzq.lucene.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.CommonUtils;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TianYaPostService;

@Controller
@RequestMapping("lucene")
public class TianYaPostController {
	@Autowired
	TianYaPostService tianYaPostService;
	
	
	
	@RequestMapping(value="tianyaquery",method=RequestMethod.GET)
	@RequiresPermissions("lucene:tianyapostquery")
	public String  showTianYaPostPage(){
		return "lucene/tianyapost";
	}
	
	@RequestMapping(value="tianyaquery",method=RequestMethod.POST)
	@RequiresPermissions("lucene:tianyapostquery")
	@ResponseBody
	public Grid<TianYaPost>  getPostDaTa(HttpServletRequest request){
		QueryCondition condition = CommonUtils.parseRequestToCondition(request);
		Grid<TianYaPost> rs = tianYaPostService.getDataGridResult(condition);
		return rs;
	}
	
	
	
	
	
	
	
	
	
	
}
