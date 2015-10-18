package com.hzq.lucene.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * 用来分析数据的工具类
 * @author huangzhiqian
 * @date 2015年10月18日 下午10:24:42
 */
public class DataAnalyserUtil {
	
	
	/**
	 * 进行天涯帖子数据抓取逻辑
	 */
	public static void getTianyaData() {
		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse response = null;
			String url = "http://bbs.tianya.cn/list-lookout-1.shtml";
			httpclient = HttpClients.custom().build();
			try {
					try {
						HttpGet httpget = new HttpGet(url);
						response = httpclient.execute(httpget);
						HttpEntity entity = response.getEntity();
						String content = EntityUtils.toString(entity);
						System.err.println(content);
						EntityUtils.consume(entity);
					
					} finally {
						response.close();
					}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
}
