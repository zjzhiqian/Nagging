package com.hzq.lucene.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.lucene.threadService.ThreadService;
import com.hzq.lucene.util.TianYaDataAnalyserUtil;


@Controller
@RequestMapping("lucene")
/**
 * 抓取Controller
 * @author huangzhiqian
 *
 */
public class TianYaGrabController {
	
	private static volatile long getCount=0;
	
	@Autowired
	TianYaPostService tianYaPostService;
	
	/**天涯帖子抓取数据List**/
	public static List<TianYaPost> dataList;
	/**天涯数据是否抓取完毕Flag**/
	public static boolean FinishFlag;
	
	public static List<TianYaPost> getDataList() {
		return dataList;
	}


	public static void setDataList(List<TianYaPost> dataList) {
		TianYaGrabController.dataList = dataList;
	}

	static {
		dataList=Collections.synchronizedList(new LinkedList<TianYaPost>());
	}
	@RequestMapping("tianya")
	public String showTianyaGrabPage(){
		return "lucene/tianyagrab";
	}
	
	
	@RequestMapping("tianyaBegin")
	@ResponseBody
	public Json startGrab(){
		FinishFlag=false;
		//把抓取数量设置为0
		getCount=0;
		if(dataList.size()>0){
			dataList.clear();
		}
		tianYaPostService.deleteAllTianYaData();
		//开启异步线程执行任务
		ThreadService.getThreadService().execute(new TianYaDataTask());
		return new Json(true,"抓取开始");
	}
	
	@RequestMapping("tianyaShowNum")
	@ResponseBody
	public synchronized Json showGrabNum(){
			if(!FinishFlag){
				if(dataList.size()>0){
					List<TianYaPost> posts=new ArrayList<TianYaPost>();
					posts.addAll(dataList);
					for(TianYaPost post:posts){
						if(post.getContent()!=null){
							dataList.remove(post);
							getCount++;
							if(post.getContent().length()>20000){
								post.setContent("此贴数据太长,暂不存入");
							}
							tianYaPostService.savePost(post);
						}
					}
				}
				return new Json(true,getCount+"");
			}else{
				if(dataList.size()>0){
					List<TianYaPost> posts=new ArrayList<TianYaPost>();
					posts.addAll(dataList);
					dataList.removeAll(posts);
					getCount=getCount+dataList.size();
					tianYaPostService.savePosts(posts);
				}
				return new Json(false,String.format("抓取完毕,抓取到了%d条数据", getCount));
			}
		
	}
	
	/**
	 * 天涯论坛数据抓取Task
	 */
	private class TianYaDataTask implements Runnable{

		@Override
		public void run() {
			TianYaDataAnalyserUtil.grabTianYaData();
		}
		
	}
	
}