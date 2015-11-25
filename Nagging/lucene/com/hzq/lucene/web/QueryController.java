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

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.CommonUtils;
import com.hzq.lucene.core.TYDataQueries;
import com.hzq.lucene.entity.TianYaPost;

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
	
	@RequestMapping(value="easyquery/{id}",method=RequestMethod.POST)
	@ResponseBody
	/**
	 * 
	 * @param request
	 * @param type 类型, 1单目录 2多目录
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月25日
	 */
	public Grid<TianYaPost> getPostDaTaByLucene(HttpServletRequest request,@PathVariable("id") String type){
		QueryCondition condition = CommonUtils.parseRequestToCondition(request);
		Grid<TianYaPost> rs = TYDataQueries.getDataGridResult(condition,type);
		return rs;
	}
	
}

