package com.hzq.test.a;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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



public class MainTest {
	private static List<TianYaPost> postList=new ArrayList<TianYaPost>();
	private static final String TIANYA_URL="http://bbs.tianya.cn/list-lookout-1.shtml";
	private static CloseableHttpClient httpclient = null;
	private static CloseableHttpResponse response = null;
	private static boolean FinishFlag=false;
	private static Long time1;
	static {
		httpclient = HttpClients.custom().build();
		time1=System.currentTimeMillis();
	}
	
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
					synchronized (MainTest.class) {
						Thread.sleep(3000L);
						FinishFlag=true;
						System.out.println("数据抓取完毕");
					}	
				}
				for(Element next:nexts){
					if(next.text().indexOf("下一页")!=-1){
						String pageUrl=next.attr("href");
						String nextPageUrl="http://bbs.tianya.cn/"+pageUrl;
						ThreadService.getThreadService().execute(new AddTianYaTask(nextPageUrl, httpclient, response));
						System.out.println(nextPageUrl);
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
							String postUrl="http://bbs.tianya.cn"+href.trim();
							System.err.println(postUrl);
							HttpGet postGet = new HttpGet(postUrl);
							HttpResponse postResponse = httpclient.execute(postGet);
							HttpEntity Postentity = postResponse.getEntity();
							String Postcontent = EntityUtils.toString(Postentity);
							FileUtils.copyInputStreamToFile(IOUtils.toInputStream(Postcontent),new File("E:\\post\\"+href+".html"));
							
							EntityUtils.consume(Postentity);
							
							post.setUrl(postUrl);
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
					postList.add(post);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException("抓取数据出错!");
			}finally {
				
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
	
	
	
		public static void main(String[] args) {
			grabTianYaData();
		}
	
}
