package com.hzq.lucene.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;


@Controller
@RequestMapping("lucene")
public class GrabController {
	private static List<String> dataList;
	private static boolean FinishFlag;
	static {
		dataList=Collections.synchronizedList(new ArrayList<String>());
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
		//TODO 异步处理
		doGrab();
		return j;
	}
	
	@RequestMapping("tianyaShowNum")
	@ResponseBody
	public Json showGrabNum(){
		if(!FinishFlag){
			return new Json(true,dataList.size()+"");
		}else{
			return new Json(false,"抓取完毕");
		}
	}
	
	/**
	 * 天涯论坛数据抓取方法
	 */
	private void doGrab(){
		for(int i=0;i<100000000;i++){
			dataList.add("3");
		}
	}
}
