/**
 * @(#)ThreadService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月22日 huangzhiqian 创建版本
 */
package com.hzq.lucene.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 执行线程任务的Service
 * @author huangzhiqian
 */
public class ThreadService {
	private static ExecutorService threadService;
	
	private static volatile ExecutorService tyGrabService;
	static {
		threadService=Executors.newFixedThreadPool(500);
	}
	
	public static ExecutorService getThreadService() {
		return threadService;
	}
	public static void setThreadService(ExecutorService threadService) {
		ThreadService.threadService = threadService;
	}
	
	public synchronized static ExecutorService gettyGrabService() {
		if(tyGrabService==null){
			tyGrabService =Executors.newFixedThreadPool(1); 
		}
		return threadService;
	}
	
	
	
}

