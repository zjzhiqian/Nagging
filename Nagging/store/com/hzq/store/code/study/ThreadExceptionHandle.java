/**
 * @(#)TmpLock.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月27日 huangzhiqian 创建版本
 */
package com.hzq.store.code.study;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


/**
 * 
 * 
 * @author huangzhiqian
 */
public class ThreadExceptionHandle {
	
	
	public static void main(String[] args) throws IOException {
		
		ThreadFactory fac=new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t=new Thread(r);
				t.setUncaughtExceptionHandler(new handler());
				return t;
			}
		};
		ExecutorService service=Executors.newFixedThreadPool(3,fac);
		service.execute(new task());
		service.shutdown();
	}
}

class task implements Runnable {
	
	@Override
	public void run(){
		throw new RuntimeException("ERRORMSG");
	}
	
}

class handler implements UncaughtExceptionHandler{
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println(e.getMessage());
	}
	
	
}



