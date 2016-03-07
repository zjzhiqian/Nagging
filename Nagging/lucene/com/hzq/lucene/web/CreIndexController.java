package com.hzq.lucene.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;
import com.hzq.lucene.constant.ConstantLucene;
import com.hzq.lucene.core.IndexCreator;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TaoBaoPostService;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.lucene.suggest.Suggesters;

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
	@RequestMapping("index/{type}/{multi}")
	@ResponseBody
	public Json postIndex(@PathVariable String type,@PathVariable String multi) {
		if(ConstantLucene.isDisabled){
			return new Json(false,"服务器IO,流量有限,此功能暂被禁止");
		}
		
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
			int step=50000;
			for(int i=0;i<800000;i=i+step){
				List<TaoBaoPost> posts = taoBaoPostService.findLimitedPost(i,step);
				//TB
				if("0".equals(multi)){
					//单目录(多线程索引)
					flag=IndexCreator.ToOnePathForTB(posts);
					if(!flag){
						break; //生成出错,退出
					}
				}else if("1".equals(multi)){
					//多目录
					try {
						IndexCreator.ToMultiPathForTB(posts);
						flag=true;
					} catch (Exception e) {
						flag=false; msg=e.getMessage(); break;
					}
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
	 * 检索提示部分索引
	 * @return
	 */
	@RequestMapping("suggest/{type}")
	@ResponseBody
	public Json  suggestCreator(HttpServletRequest req,@PathVariable("type")String type){
		if(ConstantLucene.isDisabled){
			return new Json(false,"服务器IO,流量有限,此功能暂被禁止");
		}
		
		Long time1=System.currentTimeMillis(); 
		boolean flag=true;
		if("1".equals(type)){ //TY部分
			List<TianYaPost> posts = tianYaPostService.findAllPosts();
			flag=Suggesters.createSuggest(posts);
		}else if("2".equals(type)){ //TB部分
			int step=100000;
			for(int i=0;i<900000;i=i+step){
				List<TaoBaoPost> posts=taoBaoPostService.findLimitedPost(i, step);
				boolean rs=Suggesters.createSuggestForTb(posts);
				if(!rs){
					flag=false;
					break;
				}
			}
			
		}
		if(flag){
			return new Json(true,String.format("生成成功,用了%s毫秒",System.currentTimeMillis()-time1+""));
		}
		return new Json(false);
		
	}
	


}
