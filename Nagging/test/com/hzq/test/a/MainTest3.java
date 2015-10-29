/**
 * @(#)MainTest3.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月29日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class MainTest3 {
	private static final String TAOBAO_URL="https://bbs.taobao.com/catalog/438501.htm?spm=0.0.0.0.SSGqE7";
	private static CloseableHttpClient httpclient = null;
	private static List<BBSModual> moduals=null;
	public static int NowGrabModual=0;
	public static int NowGrabPost=0;
	public static List<TaoBaoPost> posts=Collections.synchronizedList(new LinkedList<TaoBaoPost>());
	static{
		httpclient = HttpClients.custom().build();
	}
	
	private static void doHttp(){
		//将模块先置空
		moduals=null;
		try{
			String content="";
			CloseableHttpResponse response = null;
			try {
				HttpGet httpget = new HttpGet(TAOBAO_URL);
				httpget.setConfig(RequestConfig.custom() 
				          .setConnectTimeout(40000) 
				          .setConnectionRequestTimeout(40000) 
				          .setSocketTimeout(40000) 
				          .build());
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if(entity!=null){
					content = EntityUtils.toString(entity);
					moduals =getModualeUrls(content);
					for(BBSModual modual:moduals){
						//正在抓取的模块数+1
						NowGrabModual++;
						//单线程每个模块分开执行
						getDataforModual(modual);
						
					}
					
					
					
					
				}
				EntityUtils.consume(entity);
			}finally {
				try {
					if(response!=null){
						response.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有板块的url
	 * @param content
	 * @return
	 * @author huangzhiqian
	 * @date 2015年10月29日
	 */
	private static List<BBSModual> getModualeUrls(String content) {
		List<BBSModual> moduals=new ArrayList<BBSModual>();
		Document doc=Jsoup.parse(content);
		Elements eles=doc.select("div.sidebar a");
		BBSModual modual=null;
		for(Element ele:eles){
			String url=ele.attr("href");
			if(url.indexOf("index")==-1){
				modual=new BBSModual();
				url="https:"+url;
				modual.setUrl(url);
				modual.setName(ele.text());
				moduals.add(modual);
			}
		}
		return moduals;
	}
	
	/**
	 * 获取每个模块的数据
	 * @param modual
	 * @author huangzhiqian
	 * @date 2015年10月29日
	 */
	private static void getDataforModual(BBSModual modual) {
		String modualUrl=modual.getUrl();
		try{
			String content="";
			CloseableHttpResponse response = null;
			try {
				HttpGet httpget = new HttpGet(modualUrl);
				httpget.setConfig(RequestConfig.custom() 
				          .setConnectTimeout(40000) 
				          .setConnectionRequestTimeout(40000) 
				          .setSocketTimeout(40000) 
				          .build());
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if(entity!=null){
					content = EntityUtils.toString(entity);
				}
				EntityUtils.consume(entity);
				
				Document doc=Jsoup.parse(content);
				Elements eles=doc.select("div.detail a");
				System.out.println(eles);
			}finally {
				try {
					if(response!=null){
						response.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			getDataforModual(modual);
		}
		
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		doHttp();
	}
	
	
}

