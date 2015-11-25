package com.hzq.lucene.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;
import com.hzq.lucene.core.IndexCreator;
import com.hzq.lucene.core.Suggesters;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TaoBaoPostService;
import com.hzq.lucene.service.TianYaPostService;

/**
 * 用于生成索引
 * 
 * @author huangzhiqian
 * @date 2015年10月24日 下午4:20:30
 */
@Controller
@RequestMapping("lucene")
public class CreIndexController {
	@Autowired
	TianYaPostService tianYaPostService;
	@Autowired
	TaoBaoPostService taoBaoPostService;
	/**
	 * 生成索引
	 * @param type  1 天涯 2 淘宝
	 * @multi  1 单目录 2 多目录
	 * @return
	 */
	@RequestMapping("tianyaindex/{type}/{multi}")
	@ResponseBody
	public Json tianyaPostIndex(@PathVariable String type,@PathVariable String multi) {
		Long time1=System.currentTimeMillis();
		boolean flag=false; String msg="系统错误";
		
		
		if("1".equals(type)){
			//TY
			List<TianYaPost> posts = tianYaPostService.findAllPosts();
			if("0".equals(multi)){
				//单目录
				flag=IndexCreator.ToOnePath(posts);
			}else if("1".equals(multi)){
				//多目录
				try {
					IndexCreator.ToMultiPath(posts);
					flag=true;
				} catch (Exception e) {
					flag=false; msg=e.getMessage();
				}
			}
		}else if ("2".equals(type)){
			List<TaoBaoPost> posts = taoBaoPostService.findLimitedPost(0, 400000);
			//TB
			if("0".equals(multi)){
				//单目录
				flag=IndexCreator.ToOnePathForTB(posts);
			}else if("1".equals(multi)){
				//多目录
				try {
					IndexCreator.ToMultiPathForTB(posts);
					flag=true;
				} catch (Exception e) {
					flag=false; msg=e.getMessage();
				}
			}
		}
		if(flag){
			return new Json(true,String.format("生成成功,用了%s毫秒",System.currentTimeMillis()-time1+""));
		}else{
			return new Json(false,msg);
		}
		
		
		
		
	}
	

	/**
	 * 检索提示部分索引(TianYa)
	 * @return
	 */
	@RequestMapping("tianyaSuggest")
	@ResponseBody
	public Json  tianyaPostSuggester(){
		Long time1=System.currentTimeMillis();
		List<TianYaPost> posts = tianYaPostService.findAllPosts();
		boolean rs=Suggesters.createSuggest(posts);
		if(rs){
			return new Json(true,String.format("生成成功,用了%s毫秒",System.currentTimeMillis()-time1+""));
		}
		return new Json(false);
		
	}
	
	
	
	/**
	 * 生成索引(TaoBao)
	 * @return
	 */
	@RequestMapping("taobaoPostIndex")
	@ResponseBody
	public Json taobaoPostIndex() {
		Long time1=System.currentTimeMillis();
		List<TaoBaoPost> posts = taoBaoPostService.findLimitedPost(0, 40000);
		boolean flag=IndexCreator.ToOnePathForTB(posts);
		Json json=new Json(false);
		if(flag){
			json=new Json(true,String.format("生成成功,用了%s毫秒",System.currentTimeMillis()-time1+""));
		}
		return json;
	}
	

}
