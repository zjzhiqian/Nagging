/**
 * @(#)Aaaa.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月3日 huangzhiqian 创建版本
 */
package com.hzq.lucene.constant;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class Aaaa {
	private static CloseableHttpClient httpclient = null;
	
	private static final String url = "http://202.121.199.138/nature/time/AddTime.asp?Timestamp=1449132839199";
	private static final String COOKIE_VAL = "";
	static{
		httpclient = HttpClients.custom().build();
	}
	
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			sendHttp();
		}
		
	}

	private static void sendHttp(){
		try {
			String content = "";
			CloseableHttpResponse response = null;
			try {
				HttpGet httpget = new HttpGet(url);
				httpget.setConfig(RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(40000).setSocketTimeout(40000).build());
				setHeadersForGet(httpget);
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					content = EntityUtils.toString(entity);
				}
				System.out.println(content);
				EntityUtils.consume(entity);
			} finally {
				try {
					if (response != null) {
						response.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void setHeadersForGet(HttpGet httpget) {
		httpget.addHeader(new BasicHeader("accept-encoding","gzip, deflate, sdch"));
		httpget.addHeader(new BasicHeader("accept-language","zh-CN,zh;q=0.8"));
		httpget.addHeader(new BasicHeader("Cache-Control","no-cache"));
		httpget.addHeader(new BasicHeader("Connection","keep-alive"));
		httpget.addHeader(new BasicHeader("cookie",COOKIE_VAL));
		httpget.addHeader(new BasicHeader("Host","202.121.199.138"));
		httpget.addHeader(new BasicHeader("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36"));
		httpget.addHeader(new BasicHeader("referer","http://202.121.199.138/nature/time/WEBPAGE.ASP"));
		
	}

}

