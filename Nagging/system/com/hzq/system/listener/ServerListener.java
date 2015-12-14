/**
 * @(#)ServerListener.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月14日 huangzhiqian 创建版本
 */
package com.hzq.system.listener;

/**
 * 
 * 
 * @author huangzhiqian
 */
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.lucene.index.IndexWriter;

import com.hzq.lucene.util.LuceneUtil;


public class ServerListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		System.err.println("Server started!");
	}

	public void contextDestroyed(ServletContextEvent event) {
		IndexWriter writer = LuceneUtil.getTianYaWriterOne();
		if(writer != null){
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.err.println("Server closed!");
	}
}
