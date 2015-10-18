package com.hzq.test.a;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


public class MainTest {
	private static List<TianYaPost> postList=new ArrayList<TianYaPost>();
	private static int PageNum=0;
	
	public static void getTianyaData(String url) {
		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse response = null;
			httpclient = HttpClients.custom().build();
			try {
					try {
						HttpGet httpget = new HttpGet(url);
						response = httpclient.execute(httpget);
						HttpEntity entity = response.getEntity();
						String content = EntityUtils.toString(entity);
						transFerContentToPost(content);
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
	
	
	
	private static void transFerContentToPost(String content) {
		Document doc=Jsoup.parse(content);
//		Elements elements = doc.select("tr");
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
////		System.out.println(elements.size());
//		//0是标题,移除掉
//		elements.remove(0);
//		TianYaPost post=null;
//		for (Element element : elements) {
//			
//			Elements tdEle=element.select("td");
//			post=new TianYaPost();
//			//
//			
//			
//			Element ele00=tdEle.get(0);
//			//是否加精
//			if(ele00.attr("class").indexOf("facered")!=-1){
//				post.setIsBest(1);
//			}else{
//				post.setIsBest(0);
//			}
//			//url,标题 TODO 内有XXX张图片
//			Elements ele01=ele00.select("a");
//			if(ele01!=null&&ele01.size()>0){
//				String href=ele01.get(0).attr("href");
//				if(href!=null){
//					post.setHref(href.trim());
//				}
//				String title=ele01.text();
//				if(StringUtils.isNotEmpty(title)){
//					post.setTitle(title.trim());
//				}
//			}
//			
//			//userId,userName
//			Element ele10=tdEle.get(1);
//			String username=ele10.text();
//			if(StringUtils.isNotEmpty(username)){
//				post.setAddUserName(username.trim());
//			}
//			Elements ele11=ele10.select("a");
//			if(ele11!=null&&ele11.size()>0){
//				String UserIdHref=ele11.get(0).attr("href");
//				if(StringUtils.isNotEmpty(UserIdHref)){
//					UserIdHref=UserIdHref.trim();
//					String userId=UserIdHref.substring(UserIdHref.lastIndexOf("/")+1,UserIdHref.length());
//					post.setAddUserId(userId);
//				}
//				
//			}
//			//点击数
//			Element ele20=tdEle.get(2);
//			post.setClick(ele20.text());
//			//回复数
//			Element ele30=tdEle.get(3);
//			post.setReply(ele30.text());
//			//最后回复时间
//			Element ele40=tdEle.get(4);
//			try {
//				Date d=sdf.parse(ele40.attr("title"));
//				post.setLastReplyTime(d);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			postList.add(post);
//		}
//		System.err.println(postList);
//		System.out.println(postList.size());
		
		//下一页按钮的url
		Elements nexts=doc.select("div.links");
		nexts=nexts.get(0).select("a");
		for(Element next:nexts){
			if(next.text().indexOf("下一页")!=-1){
				String pageUrl=next.attr("href");
				String nextPageUrl="http://bbs.tianya.cn/"+pageUrl;
				System.out.println(nextPageUrl);
				System.out.println(++PageNum);
				getTianyaData(nextPageUrl);
			}
		}
		
	}



	public static void main(String[] args) {
		String url = "http://bbs.tianya.cn/list-lookout-1.shtml";
		getTianyaData(url);
		
	}
	
}
