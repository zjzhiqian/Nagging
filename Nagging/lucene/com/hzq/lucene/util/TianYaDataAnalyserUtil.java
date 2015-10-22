package com.hzq.lucene.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.threadService.ThreadService;
import com.hzq.lucene.web.GrabController;


/**
 * 用来分析数据的工具类
 * @author huangzhiqian
 * @date 2015年10月18日 下午10:24:42
 */
public class TianYaDataAnalyserUtil {
	private static final String TIANYA_URL="http://bbs.tianya.cn/list-lookout-1.shtml";
	private static CloseableHttpClient httpclient = null;
	private static CloseableHttpResponse response = null;
	
	static {
		httpclient = HttpClients.custom().build();
		
	}
	/**
	 * 进行天涯帖子数据抓取
	 * 
	 * @author huangzhiqian
	 * @date 2015年10月22日
	 */
	public static void grabTianYaData(){
		AddTianYaPostByUrl(TIANYA_URL,httpclient,response);
	}
	
	//TODO 获取帖子内容
	private static void AddTianYaPostByUrl(String tianyaUrl,CloseableHttpClient httpclient,CloseableHttpResponse response){
		try {
			HttpGet httpget = new HttpGet(tianyaUrl);
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			
			Document doc=Jsoup.parse(content);
			
			//下一页按钮的url(开启异步线程根据下一页url继续解析)
			Elements nexts=doc.select("div.links");
			nexts=nexts.get(0).select("a");
			if(!nexts.text().contains("下一页")){
				//睡眠10秒,保证其他线程的解析执行完毕
				synchronized (TianYaDataAnalyserUtil.class) {
					Thread.sleep(3000L);
					GrabController.FinishFlag=true;
				}	
			}
			for(Element next:nexts){
				if(next.text().indexOf("下一页")!=-1){
					String pageUrl=next.attr("href");
					String nextPageUrl="http://bbs.tianya.cn/"+pageUrl;
					ThreadService.getThreadService().execute(new AddTianYaTask(nextPageUrl, httpclient, response));
				}
			}
			
			Elements elements = doc.select("tr");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//0是标题,移除掉
			elements.remove(0);
			TianYaPost post=null;
			for (Element element : elements) {
				Elements tdEle=element.select("td");
				post=new TianYaPost();

				Element ele00=tdEle.get(0);
				//是否加精
				if(ele00.attr("class").indexOf("facered")!=-1){
					post.setIsBest("1");
				}else{
					post.setIsBest("0");
				}
				//url,标题
				Elements ele01=ele00.select("a");
				String href="";
				if(ele01!=null&&ele01.size()>0){
					href=ele01.get(0).attr("href");
					if(StringUtils.isNotEmpty(href)){
						post.setUrl("http://bbs.tianya.cn"+href.trim());
					}
					String title=ele01.text();
					if(StringUtils.isNotEmpty(title)){
						post.setTitle(title.trim());
					}
				}
				//userId,userName
				Element ele10=tdEle.get(1);
				String username=ele10.text();
				if(StringUtils.isNotEmpty(username)){
					post.setAdduserName(username.trim());
				}
				Elements ele11=ele10.select("a");
				if(ele11!=null&&ele11.size()>0){
					String UserIdHref=ele11.get(0).attr("href");
					if(StringUtils.isNotEmpty(UserIdHref)){
						UserIdHref=UserIdHref.trim();
						String userId=UserIdHref.substring(UserIdHref.lastIndexOf("/")+1,UserIdHref.length());
						if(StringUtils.isNotEmpty(userId)){
							post.setAdduserId(userId);
						}
					}
				}
				//点击数
				Element ele20=tdEle.get(2);
				if(StringUtils.isNotEmpty(ele20.text())){
					post.setClick(Long.parseLong(ele20.text()));
				}
				//回复数
				Element ele30=tdEle.get(3);
				if(StringUtils.isNotEmpty(ele30.text())){
					post.setReply(Long.parseLong(ele30.text()));
				}
				//最后回复时间
				Element ele40=tdEle.get(4);
				try {
					Date d=sdf.parse(ele40.attr("title"));
					post.setLastReplyTime(d);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				GrabController.getDataList().add(post);
			}
			
		}catch(Exception e){
			throw new RuntimeException("抓取数据出错!");
		}finally {
			try {
				response.close();
			} catch (IOException e) {
			}
		}
		
	}
	
	private static class AddTianYaTask implements Runnable{
		private CloseableHttpClient httpclient;
		private CloseableHttpResponse response;
		private String tianyaUrl;
		public AddTianYaTask(String tianyaUrl,CloseableHttpClient httpclient,CloseableHttpResponse response){
			this.tianyaUrl=tianyaUrl;
			this.httpclient=httpclient;
			this.response=response;
		}
		@Override
		public void run() {
			try {
				AddTianYaPostByUrl(tianyaUrl,httpclient,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}
