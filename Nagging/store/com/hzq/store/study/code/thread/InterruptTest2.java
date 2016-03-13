package com.hzq.store.study.code.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class InterruptTest2 {
	private static class mTask implements Runnable{

		@Override
		public void run() {
			long time = System.currentTimeMillis();
			
			work();
//			try {
//				TimeUnit.SECONDS.sleep(3);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			System.err.println(System.currentTimeMillis()-time);
		}
		
		private void work(){
			for (int i = 0 ; i < 99999; i ++){
				for (int j = 0 ; j < 9; j ++){
							System.out.println("1");
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		
		service.execute(new mTask());
//		service.shutdown();
		service.shutdownNow();
		System.out.println("================================");
	}
}
