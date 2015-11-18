package com.hzq.lucene.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;
import com.hzq.lucene.core.IndexCreator;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TianYaPostService;

/**
 * 用于生成索引
 * 
 * @author huangzhiqian
 * @date 2015年10月24日 下午4:20:30
 */
@Controller
@RequestMapping("lucene")
public class LuceneIndexController {
	@Autowired
	TianYaPostService tianYaPostService;

	/**
	 * 生成单目录索引
	 * @return
	 */
	@RequestMapping("tianyaindexOnePath")
	@ResponseBody
	public Json tianyaPostIndex() {
		Long time1=System.currentTimeMillis();
		List<TianYaPost> posts = tianYaPostService.findAllPosts();
		boolean flag=IndexCreator.ToOnePath(posts);
		Json json=new Json(false);
		if(flag){
			json=new Json(true,String.format("生成成功,用了%s毫秒",System.currentTimeMillis()-time1+""));
		}
		return json;
	}
	
	/**
	 * 生成单目录索引
	 * @return
	 */
	@RequestMapping("tianyaindexMultiPath")
	@ResponseBody
	public Json tianyaPostIndexMulti() {
		Long time1=System.currentTimeMillis();
		List<TianYaPost> posts = tianYaPostService.findAllPosts();
		try {
			IndexCreator.ToMultiPath(posts, 5);
			return new Json(true,String.format("生成成功,用了%s毫秒",System.currentTimeMillis()-time1+""));
		} catch (Exception e) {
			return new Json(false,e.getMessage());
		}
	}

}
