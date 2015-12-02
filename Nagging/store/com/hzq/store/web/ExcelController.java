/**
 * @(#)ExcelController.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月2日 huangzhiqian 创建版本
 */
package com.hzq.store.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.CommonUtils;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.store.util.ExcelUtils;

/**
 * 
 * 
 * @author huangzhiqian
 */
@Controller
@RequestMapping("store")
public class ExcelController {
	@Autowired
	TianYaPostService tianYaPostService;
	
	
	
	@RequestMapping("excel")
	public String showDemoPage(){
		return "store/excel";
	}
	
	/**
	 * Excel导出Demo
	 * @param req
	 * @return
	 * @throws IOException
	 * @author huangzhiqian
	 * @date 2015年12月2日
	 */
	@RequestMapping(value="extractExcel",method=RequestMethod.POST)
	public ResponseEntity<byte[]> extractExcel(HttpServletRequest req) throws IOException{
		QueryCondition condition=CommonUtils.parseRequestToCondition(req);
		Grid<TianYaPost> posts=tianYaPostService.getDataGridResult(condition);
		List<TianYaPost> list=posts.getRows();
        return ExcelUtils.Exeport(list,"标题","tianya_post");
	}
	
	
	
	
	
}

