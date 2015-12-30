package com.hzq.store.study.code.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StaticMethodLockTest {
	
	public  synchronized static void testMethodLock(){
		System.out.println("1");
		System.out.println("2");
	}
	
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.execute(new task());
		service.execute(new task());
		service.shutdown();
	}
	
	
	
}

class task implements Runnable{

	@Override
	public void run() {
		StaticMethodLockTest.testMethodLock();
	}
	
}