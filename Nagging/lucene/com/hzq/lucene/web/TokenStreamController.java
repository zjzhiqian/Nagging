/**
 * @(#)SynonymController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月3日 huangzhiqian 创建版本
 */
package com.hzq.lucene.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.DataList;
import com.hzq.common.entity.Grid;
import com.hzq.lucene.synonym.TxtSynonymEngine;
import com.hzq.lucene.util.AnalyzerUtil;
import com.hzq.lucene.util.LuceneUtil;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("lucene")
public class TokenStreamController {
	
	
	@RequestMapping(value="synonym/{id}",method=RequestMethod.GET)
	public String synonymPage(@PathVariable("id")int id,Map<String,String> map){
		//1为文本文件的同义词,2为数据库存储的同义词
		if(id==1){
			map.put("type", "1");
		}else if(id==2){
			map.put("type", "2");
		}
		
		return "lucene/synonym";
	}
	
	
	@RequestMapping(value="synonym/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Grid<DataList> synonymData(@PathVariable("id")int id,HttpServletRequest req){
		Grid<DataList> gridResult=new Grid<DataList>();
		
//		//1为文本文件的同义词,2为数据库存储的同义词
		if(id==1){
			List<String> synonyms=TxtSynonymEngine.getAllSynonym();
			gridResult.setTotal(synonyms.size());
			int page=Integer.parseInt(req.getParameter("page"));
			int rows=Integer.parseInt(req.getParameter("rows"));
			if("1".equals(page)){
				synonyms=synonyms.subList(0,rows-1);
			}else{
				synonyms=synonyms.subList(page*rows,(page+1)*rows-1);
			}
			List<DataList> rowdata = new LinkedList<DataList>();
			DataList list=null;
			for(String text:synonyms){
				list=new DataList();
				list.setText(text);
				list.setGroup("group1");
				rowdata.add(list);
			}
			gridResult.setRows(rowdata);
		}else if(id==2){
//			map.put("type", "2");
		}
		
		return  gridResult;
	}
	
	
	/**
	 * 进入分词页面
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月23日
	 */
	@RequestMapping(value="tokenquery",method=RequestMethod.GET)
	public String tokenquery(){
		return "lucene/tokenquery";
	}
	
	/**
	 * 查看分词信息
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月23日
	 */
	@RequestMapping(value="tokenquery",method=RequestMethod.POST)
	@ResponseBody
	public String tokenquerypost(HttpServletRequest req){
		String content=req.getParameter("content");
		if(StringUtils.isNotEmpty(content)){
			return AnalyzerUtil.displayAllTokenInfo(content.trim(), LuceneUtil.getSynonymAnalyzer());
		}else{
			return "不能为空";
		}
	}
	
}

