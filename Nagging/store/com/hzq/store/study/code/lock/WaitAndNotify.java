/**
 * @(#)ObbTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月24日 huangzhiqian 创建版本
 */
package com.hzq.store.study.code.lock;

import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class WaitAndNotify {	
	
	
	public void dolock(){
		
		synchronized (this) {
			int i = 0 ; 
			while(true){
				System.out.println("进入While True");
				if(i == 1){
					break;
				}
				try {
					wait(); //释放this锁
					i++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void release(){
		//上面wait,获取了this索
		synchronized (this) {
			System.out.println("执行notifyAll");
			notifyAll();
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		WaitAndNotify test = new WaitAndNotify();
		new Thread(new Runnable() {
			@Override
			public void run() {
				test.dolock();
			}
		}).start();
		TimeUnit.SECONDS.sleep(2);
		test.release();
	}
	
}
	
