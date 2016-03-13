package com.hzq.store.study.code.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * CountDownLatch只触发一次,CyclicBarrier可以多次重用       CyclicBarrier.class
 */
public class CyclicBarrierTest2 {
	
	private  static class  mtask implements Runnable{
		private CyclicBarrier barrier;
		private int time = 0 ;
		public mtask(CyclicBarrier barrier){
			this.barrier=barrier;
		}
		@Override
		public void run() {
			while(!Thread.interrupted()){
				System.out.println("=======in======="+time++);
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(4, new Runnable(){
			@Override
			public void run() {
				System.out.println("................Running");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		ExecutorService s = Executors.newCachedThreadPool();
		//4个线程都await后,会调用barrier的run方法 ()
		s.execute(new mtask(barrier));
		s.execute(new mtask(barrier));
		s.execute(new mtask(barrier));
		s.execute(new mtask(barrier));
	}
}
