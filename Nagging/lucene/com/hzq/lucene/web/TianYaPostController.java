package com.hzq.lucene.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.CommonUtils;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.lucene.util.LuceneQueryUtil;

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
		setSortForcondition(request,condition);
		Long time=System.currentTimeMillis();
		Grid<TianYaPost> rs = tianYaPostService.getDataGridResult(condition);
		rs.setO(new Json(true,System.currentTimeMillis()-time+""));
		return rs;
	}
	

	@RequestMapping(value="tianyaIndexQuery",method=RequestMethod.POST)
	@RequiresPermissions("lucene:tianyapostquery")
	@ResponseBody
	public Grid<TianYaPost> getPostDaTaByLucene(HttpServletRequest request){
		QueryCondition condition = CommonUtils.parseRequestToCondition(request);
		setSortForcondition(request, condition);
		Grid<TianYaPost> rs = LuceneQueryUtil.getDataGridResult(condition);
		return rs;
	}
	
	
	
	
	/**
	 * 根据request获得排序方式,放入condition
	 * @param request
	 * @param condition
	 */
	private void setSortForcondition(HttpServletRequest request, QueryCondition condition) {
		String order=request.getParameter("sort");
		if(StringUtils.isNotEmpty(order)){
			String ordersql="";
			if("1".equals(order)){
				ordersql="order by add_time desc";
			}else if("2".equals(order)){
				ordersql="order by click desc";
			}else if ("3".equals(order)){
				ordersql="order by reply desc";
			}
			if(StringUtils.isNotEmpty(ordersql)){
				condition.getCondition().put("orderSql", ordersql);
			}
		}
	}
	
	
}
