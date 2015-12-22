/**
 * @(#)LockObtainTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月22日 huangzhiqian 创建版本
 */
package com.hzq.store.study.code;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class LockObtain {
	private static final Set<String> LOCK_HELD = Collections.synchronizedSet(new HashSet<String>());

	private static Long LOCK_POLL_INTERVAL = 1000L;

	private static final Path path = Paths.get("E:\\aa.xls");

	private synchronized static boolean obtain(Path path) throws IOException {
		boolean obtained = false;
		if (LOCK_HELD.add(path.toString())) {
			FileChannel ch = null;
			try {
				ch = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
				try {
					if (ch.tryLock() != null) {
						obtained = true;
					}
				} catch (IOException | OverlappingFileLockException e) {
					e.printStackTrace();
				}
			} finally {
				if (obtained == false) {
					try {
						ch.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					LOCK_HELD.remove(path.toString());
				}
			}

		}
		return obtained;
	}

	private static final boolean obtain(long lockWaitTimeout) throws IOException {
		boolean locked = obtain(path);
		long maxSleepCount = lockWaitTimeout / LOCK_POLL_INTERVAL;
		
		long sleepCount = 0;
		while (!locked) {
			if (sleepCount++ >= maxSleepCount) {
				throw new RuntimeException("获取锁超时...");
			}
			try {
				Thread.sleep(LOCK_POLL_INTERVAL);
			} catch (InterruptedException ie) {
				throw new RuntimeException();
			}
			
			locked = obtain(path);
			System.out.println("尝试重新获取锁....");
		}
		return locked;

	}

	public static void main(String[] args) throws IOException {
		ExecutorService service = Executors.newFixedThreadPool(3);
		service.execute(new task());
		service.execute(new task());
		service.shutdown();
	}
	
	
	static class task implements Runnable {
		
		@Override
		public void run() {
			try {
				obtain(20000);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}

