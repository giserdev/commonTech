package com.crawler.abroad.mapdata.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.crawler.abroad.mapdata.utils.PropertyUtils;

/**线程
 * Created By giser on 2019-04-24
 */
public class ThreadContainer {
	
	private static final int threadPoolSize = Integer.valueOf(PropertyUtils.getStringByKey("thread.pool.size"));
	
	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 10000L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	
	public static Boolean canExecute() {
		return threadPool.getMaximumPoolSize() > threadPool.getActiveCount();
	}
	
	public static void execute(Runnable task) {
		threadPool.execute(task);
	}
	
}
