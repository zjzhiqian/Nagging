/**
 * @(#)CountDownDemo.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月29日 huangzhiqian 创建版本
 */
package com.hzq.store.code.study;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class CountDownLatchTest {
	
    public static void main(String[] args) throws InterruptedException {
    	CountDownLatch latch=new CountDownLatch(2);//两个工人的协作
    	Worker worker1=new Worker("Worker1", 3000, latch);
    	Worker worker2=new Worker("Worker2", 6000, latch);
    	worker1.start();//
    	worker2.start();//
    	latch.await();//等待所有工人完成工作
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("ALL FINISHED  "+sdf.format(new Date()));
	}
    
    
    static class Worker extends Thread{
    	String workerName; 
    	int workTime;
    	CountDownLatch latch;
    	public Worker(String workerName ,int workTime ,CountDownLatch latch){
    		 this.workerName=workerName;
    		 this.workTime=workTime;
    		 this.latch=latch;
    	}
    	public void run(){
    		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		System.out.println(workerName+" BEGIN  "+sdf1.format(new Date()));
    		doWork();//工作了
    		System.out.println(workerName+" FINISH "+sdf1.format(new Date()));
    		latch.countDown();//工人完成工作，计数器减一

    	}
    	
    	private void doWork(){
    		try {
				Thread.sleep(workTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    
     
}

