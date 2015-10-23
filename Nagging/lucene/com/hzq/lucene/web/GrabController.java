package com.hzq.lucene.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;
import com.hzq.common.util.SpringContextUtils;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.lucene.threadService.ThreadService;
import com.hzq.lucene.util.TianYaDataAnalyserUtil;


@Controller
@RequestMapping("lucene")
public class GrabController {
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
		GrabController.dataList = dataList;
	}

	static {
		dataList=Collections.synchronizedList(new ArrayList<TianYaPost>());
	}
	@RequestMapping("tianya")
	public String showTianyaGrabPage(){
		return "lucene/tianyagrab";
	}
	
	
	@RequestMapping("tianyaBegin")
	@ResponseBody
	public Json startGrab(){
		FinishFlag=false;
		Json j=new Json("数据不为空,重新开始数据抓取");
		if(dataList.size()>0){
			dataList.clear();
		}else{
			j=new Json(true,"抓取开始");
		}
		//开启异步线程执行任务
		ThreadService.getThreadService().execute(new TianYaDataTask());
		return j;
	}
	
	@RequestMapping("tianyaShowNum")
	@ResponseBody
	public synchronized Json showGrabNum(){
			if(!FinishFlag){
				return new Json(true,dataList.size()+"");
			}else{
				ThreadService.getThreadService().execute(new tianyaSavePostTask());
				return new Json(false,String.format("抓取完毕,抓取到了%d条数据", dataList.size()));
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
	
	/**
	 * 天涯论坛数据保存到数据库线程
	 */
	private class tianyaSavePostTask implements Runnable{
		@Override
		public void run() {
			TianYaPostService postService=(TianYaPostService) SpringContextUtils.getBean("tianYaPostServiceImpl");
			List<TianYaPost> insertList=new ArrayList<TianYaPost>(dataList);
			postService.savePosts(insertList);
		}
		
	}
}
