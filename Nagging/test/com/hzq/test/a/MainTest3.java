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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hzq.lucene.entity.BBSModual;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.threadService.ThreadService;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class MainTest3 {
	private static final String TAOBAO_URL="https://bbs.taobao.com/catalog/438501.htm?spm=0.0.0.0.SSGqE7";
	private static CloseableHttpClient httpclient = null;
	/**保存每个模块已抓取帖子数量的Map**/
	public static Map<BBSModual,AtomicInteger> modualCountMap=null;
	/**多线程同时工作,保证全部完成**/
	public static CountDownLatch modualCountDown;
	/**标记是否抓取完成的Flag**/
	public static boolean finishFlag=false;
	public static List<TaoBaoPost> posts=null;
	static{
		httpclient = HttpClients.custom().build();
	}
	
	/**
	 * 抓取之前的初始化
	 * 
	 * @author huangzhiqian
	 * @date 2015年10月29日
	 */
	private static void init() {
		//重新new一个posts和Modual,把完成标记设置为false
		posts=Collections.synchronizedList(new LinkedList<TaoBaoPost>());
		modualCountMap=Collections.synchronizedMap(new HashMap<BBSModual,AtomicInteger>());
		finishFlag=false;
		
	}
	
	
	private static void doHttp(){
		init();
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
				setHeadersForGet(httpget);
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if(entity!=null){
					content = EntityUtils.toString(entity);
//					System.out.println(content);
					EntityUtils.consume(entity);
					List<BBSModual> moduals =getModuales(content);
					modualCountDown=new CountDownLatch(moduals.size());
					System.out.println(moduals.size());
					for(BBSModual modual:moduals){
						modualCountMap.put(modual, new AtomicInteger(0));
						ThreadService.getThreadService().execute(new getDataByModualTask(modual,modualCountDown));
					}
					modualCountDown.await();
					System.err.println("抓取完成!!");
					finishFlag=true;
				}
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
	private static List<BBSModual> getModuales(String content) {
		List<BBSModual> moduals=new ArrayList<BBSModual>();
		Document doc=Jsoup.parse(content);
		Elements eles=doc.select("div.sidebar a");
		BBSModual modual=null;
		for(Element ele:eles){
			String url=ele.attr("href");
			if(url.indexOf("index")==-1){
				modual=new BBSModual();
				url="https:"+url+"?spm=a210m.7475144.0.0.SdFSF4";
				modual.setUrl(url);
				modual.setName(ele.text());
				moduals.add(modual);
			}
		}
		return moduals;
	}
	
	
	private static class getDataByModualTask implements Runnable{
		private final BBSModual modual;
		private final CountDownLatch modualCountDown;
		
		public getDataByModualTask(BBSModual modual, CountDownLatch modualCountDown) {
			this.modualCountDown=modualCountDown;
			this.modual=modual;
		}
		@Override
		public void run() {
			try{
				getDataforModual(modual,modual.getUrl());
			}finally{
				modualCountDown.countDown();
			}
			
		}
		
		
	}
	
	
	/**
	 * 获取BBS每个模块的数据
	 * @param modual 模块类
	 * @author huangzhiqian
	 * @date 2015年10月29日
	 */
	private static void getDataforModual(BBSModual modual,String modualUrl) {
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
				setHeadersForGet2(httpget,modualUrl);
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if(entity!=null){
					content = EntityUtils.toString(entity);
				}
				EntityUtils.consume(entity);
				Document doc=Jsoup.parse(content);
				//title,url
				Elements eles=doc.select("div.detail a");
				TaoBaoPost post=null;
				for(Element ele:eles){
					post=new TaoBaoPost();
					String url=ele.attr("href");
					url="https:"+url;
					String title=ele.attr("title");
					post.setUrl(url);
					post.setTitle(title);
//					ThreadService.getThreadService().execute(new PageDataTask(post,modual));
				}
				
				//递归终止条件
				Elements npgeles=doc.getElementsByAttributeValue("class","page-next j_page_changer");
				//如果是最后一页
				if(npgeles.size()==0){
					modualCountDown.countDown();
					System.out.println(modual+"此模块数据抓取完毕");
				}else{
					//递归执行下一条
					getDataforModual(modual,modualUrl);
				}
				
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
			getDataforModual(modual,modualUrl);
		}
		
		
	}
	
	
	

	/**
	 * 每一个帖子的详情数据的抓取
	 * @author huangzhiqian
	 *
	 */
	private static class PageDataTask implements Runnable{
		private final TaoBaoPost post;
		private final BBSModual modual;
		
		public PageDataTask(TaoBaoPost post, BBSModual modual) {
			this.post=post;
			this.modual=modual;
		}

		@Override
		public void run() {
			try {
				CloseableHttpClient httpclient2 = null;
				CloseableHttpResponse response2 = null;
				String postContent = "";
				try {
					httpclient2 = HttpClients.custom().build();
					try {
						HttpGet postGet = new HttpGet(post.getUrl());
						postGet.setConfig(RequestConfig.custom() 
						          .setConnectTimeout(40000) 
						          .setConnectionRequestTimeout(40000) 
						          .setSocketTimeout(40000) 
						          .build());
						response2 = httpclient2.execute(postGet);
						HttpEntity Postentity = response2.getEntity();
						postContent = EntityUtils.toString(Postentity);
						EntityUtils.consume(Postentity);
					} finally {
						if (response2 != null) {
							response2.close();
						}
					}
				} finally {
					if (httpclient2 != null) {
						httpclient2.close();
					}
				}
				if (StringUtils.isNotEmpty(postContent)) {
					System.out.println(postContent);
					Document doc = Jsoup.parse(postContent);
					//TODO 详情页数据获取
					
					
					
					AtomicInteger postNum=modualCountMap.get(modual);
					postNum.getAndIncrement();
				}
				
			} catch (ConnectTimeoutException|java.net.SocketException|java.net.SocketTimeoutException e) {
				if(post.getRetryCount().incrementAndGet()<10){
					ThreadService.getThreadService().execute(new PageDataTask(post,modual));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	public static void main(String[] args) {
		doHttp();
	}
	
	
	private static void setHeadersForGet(HttpGet httpget) {
//		httpget.addHeader(new BasicHeader("cookie", "mt=ci%3D-1_0; thw=cn; cna=Qt22Du+QwkACAXthouKyEcCj; lzstat_uv=4570413951147359016|3600144; v=0; _tb_token_=e733716beebe3; showPopup=3; cookie2=1ea9ad5282e73e82b9f6b788236c26d6; t=7b161c0f951b5ad1f635de2020db11cc; mt=ci%3D-1_0; uc1=cookie14=UoWzX%2BKOpr6cIw%3D%3D; CNZZDATA30070035=cnzz_eid%3D1286133655-1446037247-https%253A%252F%252Fwww.baidu.com%252F%26ntime%3D1446118504; isg=7B5923C983E235BE7FE51763F192212A; l=AjU14c8JQx9z071IJwq37m4zxb/vsunE"));
		httpget.addHeader(new BasicHeader("cookie", "mt=ci%3D-1_0; swfstore=5768; thw=cn; cna=Qt22Du+QwkACAXthouKyEcCj; lzstat_uv=4570413951147359016|3600144; v=0; _tb_token_=e733716beebe3; showPopup=3; uc3=nk2=AnDS93hBOWEYdpk%3D&id2=W8twrLUvJaM8&vt3=F8dASMdNBP60hmY1PwE%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; existShop=MTQ0NjExOTQ2Nw%3D%3D; lgc=aa616095191; tracknick=aa616095191; skt=01483dcc3612a40e; _cc_=Vq8l%2BKCLiw%3D%3D; tg=0; whl=-1%260%260%261446119556768; mt=ci=-1_0; JSESSIONID=9E865A994486D3ED905D8690527D7A9D; cookie2=1ea9ad5282e73e82b9f6b788236c26d6; t=7b161c0f951b5ad1f635de2020db11cc; CNZZDATA30070035=cnzz_eid%3D1286133655-1446037247-https%253A%252F%252Fwww.baidu.com%252F%26ntime%3D1446118504; uc1=cookie14=UoWzX%2BKNdnltWw%3D%3D; l=At7eZEsVyOI0FgbtuNesI0ACrnogn6IZ; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; isg=45EF2A65AFE05FA6C528C3C7962E5B5A"));
		httpget.addHeader(new BasicHeader(":host","bbs.taobao.com"));
		httpget.addHeader(new BasicHeader(":method","GET"));
		httpget.addHeader(new BasicHeader(":path","/catalog/438501.htm?spm=0.0.0.0.SSGqE7"));
		httpget.addHeader(new BasicHeader(":scheme","https"));
		httpget.addHeader(new BasicHeader(":version","HTTP/1.1"));
		httpget.addHeader(new BasicHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
		httpget.addHeader(new BasicHeader("accept-encoding","gzip, deflate, sdch"));
		httpget.addHeader(new BasicHeader("accept-language","zh-CN,zh;q=0.8"));
		httpget.addHeader(new BasicHeader("cache-control","max-age=0"));
		httpget.addHeader(new BasicHeader("upgrade-insecure-requests","1"));
		httpget.addHeader(new BasicHeader("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36"));
		httpget.addHeader(new BasicHeader("referer","https://bbs.taobao.com/catalog/438501.htm?spm=0.0.0.0.SSGqE7&smToken=52facb8024304c8a9041cd2de74b4466&smSign=9gAnQJUxfxo6Y0Yd7AvV6A%3D%3D"));
		
	}
	
	private static void setHeadersForGet2(HttpGet httpget,String modualUrl) {
//		httpget.addHeader(new BasicHeader("cookie", "mt=ci%3D-1_0; thw=cn; cna=Qt22Du+QwkACAXthouKyEcCj; lzstat_uv=4570413951147359016|3600144; v=0; _tb_token_=e733716beebe3; showPopup=3; cookie2=1ea9ad5282e73e82b9f6b788236c26d6; t=7b161c0f951b5ad1f635de2020db11cc; mt=ci%3D-1_0; uc1=cookie14=UoWzX%2BKOpr6cIw%3D%3D; CNZZDATA30070035=cnzz_eid%3D1286133655-1446037247-https%253A%252F%252Fwww.baidu.com%252F%26ntime%3D1446118504; isg=7B5923C983E235BE7FE51763F192212A; l=AjU14c8JQx9z071IJwq37m4zxb/vsunE"));
		httpget.addHeader(new BasicHeader("cookie", "mt=ci%3D-1_0; swfstore=5768; thw=cn; cna=Qt22Du+QwkACAXthouKyEcCj; lzstat_uv=4570413951147359016|3600144; v=0; _tb_token_=e733716beebe3; showPopup=3; uc3=nk2=AnDS93hBOWEYdpk%3D&id2=W8twrLUvJaM8&vt3=F8dASMdNBP60hmY1PwE%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; existShop=MTQ0NjExOTQ2Nw%3D%3D; lgc=aa616095191; tracknick=aa616095191; skt=01483dcc3612a40e; _cc_=Vq8l%2BKCLiw%3D%3D; tg=0; whl=-1%260%260%261446119556768; mt=ci=-1_0; JSESSIONID=9E865A994486D3ED905D8690527D7A9D; cookie2=1ea9ad5282e73e82b9f6b788236c26d6; t=7b161c0f951b5ad1f635de2020db11cc; CNZZDATA30070035=cnzz_eid%3D1286133655-1446037247-https%253A%252F%252Fwww.baidu.com%252F%26ntime%3D1446118504; uc1=cookie14=UoWzX%2BKNdnltWw%3D%3D; l=At7eZEsVyOI0FgbtuNesI0ACrnogn6IZ; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; isg=45EF2A65AFE05FA6C528C3C7962E5B5A"));
		httpget.addHeader(new BasicHeader(":host","bbs.taobao.com"));
		httpget.addHeader(new BasicHeader(":method","GET"));
		String headerPath=modualUrl.substring(modualUrl.indexOf("catalog/")-1,modualUrl.length());
		httpget.addHeader(new BasicHeader(":path",headerPath));
		httpget.addHeader(new BasicHeader(":scheme","https"));
		httpget.addHeader(new BasicHeader(":version","HTTP/1.1"));
		httpget.addHeader(new BasicHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
		httpget.addHeader(new BasicHeader("accept-encoding","gzip, deflate, sdch"));
		httpget.addHeader(new BasicHeader("accept-language","zh-CN,zh;q=0.8"));
		httpget.addHeader(new BasicHeader("cache-control","max-age=0"));
		httpget.addHeader(new BasicHeader("upgrade-insecure-requests","1"));
		httpget.addHeader(new BasicHeader("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36"));
		httpget.addHeader(new BasicHeader("referer","https://bbs.taobao.com/catalog/12129511.htm?spm=a210m.7475144.0.0.P2Atao"));
		
	}
	
}

