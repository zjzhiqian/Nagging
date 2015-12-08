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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.Utils;
import com.hzq.lucene.core.TBDataQueries;
import com.hzq.lucene.core.TYDataQueries;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.suggest.Suggesters;

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
	
	/**
	 * 
	 * @param request(TY)
	 * @param type 类型, 1单目录 2多目录
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月25日
	 */
	@RequestMapping(value="easyquery/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Grid<TianYaPost> getPostDadaTYByLucene(HttpServletRequest request,@PathVariable("id") String type){
		QueryCondition condition = Utils.parseRequestToCondition(request);
		Grid<TianYaPost> rs = TYDataQueries.getDataGridResult(condition,type);
		return rs;
	}
	
	/**
	 * 搜索提示
	 * @param request
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月26日
	 */
	@RequestMapping(value="tianyaSuggestQuery",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> tianyaSuggestQuery(HttpServletRequest request){
		String content=request.getParameter("content");
		List<Map<String,Object>> ListMap = null;
		if(StringUtils.isNotEmpty(content)){
			ListMap=Suggesters.getSuggestResult(content, "谈天说地");
		}
		return ListMap;
	}

	
	
	
	@RequestMapping(value="complexquery",method=RequestMethod.GET)
	public String showComplexQueryPage(){
		return "lucene/complexquery";
	}
	
	
	/**
	 * 
	 * @param request(TB)
	 * @param type 类型, 1单目录 2多目录
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月25日
	 */
	@RequestMapping(value="complexquery/{type}",method=RequestMethod.POST)
	@ResponseBody
	public Grid<TaoBaoPost> getPostDadaTBByLucene(HttpServletRequest request,@PathVariable("type") String type){
		QueryCondition condition = Utils.parseRequestToCondition(request);
		Grid<TaoBaoPost> rs = TBDataQueries.getDataGridResult(condition,type);
		return rs;
	}
	
	
	/**
	 * 搜索提示
	 * @param request
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月26日
	 */
	@RequestMapping(value="taobaoSuggestQuery",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> taobaoSuggestQuery(HttpServletRequest request){
 		String content=request.getParameter("content");
		List<Map<String,Object>> ListMap = null;
		if(StringUtils.isNotEmpty(content)){
			ListMap=Suggesters.getSuggestResultForTB(content, "谈天说地");
		}
		return ListMap;
	}
	
	
}

