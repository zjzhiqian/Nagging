package com.hzq.test.a;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class HttpTest {
	private static CloseableHttpClient httpclient = null;
	static {
		httpclient = HttpClients.custom().build();
	}
	static void getPostData(String url){
		try {
			String content = "";
			CloseableHttpResponse response = null;
			try {
				HttpGet httpget = new HttpGet(url);
				setHeadersForGet(httpget);
				httpget.setConfig(RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(40000).setSocketTimeout(40000).build());
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					content = EntityUtils.toString(entity);
				}
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
			System.out.println(content);
			Document doc = Jsoup.parse(content);
			Elements ele=doc.select("span.time-author");
			
			//发帖时间
			if(ele!=null&&ele.size()>0){
				String sendTime=ele.get(0).text();
				sendTime=sendTime.replace("|", "");
				sendTime=sendTime.replace("发表于", "");
				sendTime=sendTime.replace("只看楼主", "").trim();
				System.out.println(sendTime);
			}
			
			//发帖人名称
			ele=doc.select("div.user-info-wrap");
			if(ele!=null&&ele.size()>0){
				Elements tmpEle=ele.select("h5");
				if(tmpEle!=null&&tmpEle.size()>0){
					System.out.println(tmpEle.get(0).text());
				}
			}
			//内容
			ele=doc.select("div.ke-post");
			if(ele!=null&&ele.size()>0){
				System.out.println(ele.get(0).text());
			}
			//查看
			ele=doc.select("div.visitor");
			if(ele!=null&&ele.size()>0){
				Elements tmpEle=ele.select("em");
				if(tmpEle!=null&&tmpEle.size()==2){
					System.out.println(tmpEle.get(0).text().trim());
					System.out.println(tmpEle.get(1).text().trim());
				}else if(tmpEle.size()==1){
					System.out.println(tmpEle.get(0).text().trim());
					tmpEle=ele.select("span");
					if(tmpEle!=null&&tmpEle.size()==1){
						String reply= tmpEle.get(0).text().trim();
						System.out.println(reply+"..");
					}
					
				}else{
					tmpEle=ele.select("span");
					if(tmpEle!=null&&tmpEle.size()==2){
						String watch=tmpEle.get(0).text().trim();
						String reply= tmpEle.get(1).text().trim();
						if(StringUtils.isNotEmpty(reply)){
							
						}
						System.out.println(watch);
						System.out.println(reply);
					}
				}
			}
			
			
		}catch(IOException e){
			
		}

	}
	
	public static void main(String[] args) {
//		getPostData("https://bbs.taobao.com/catalog/thread/508895-256357307.htm");
//		System.out.println(Long.parseLong("0"));
		int step=1000;
		for(int i=0;i<5000;i=i+step){
			System.out.println(i);
		}
	}
	
	
	
	
	
	
	
	
	
	
	/**请求头**/
	private static void setHeadersForGet(HttpGet httpget) {
		httpget.addHeader(new BasicHeader("cookie", "mt=ci%3D-1_0; swfstore=6477; thw=cn; cna=TJDXDmZfojkCAbeUx3aHdj4+; showPopup=3; v=0; _tb_token_=zNv5Yuycs0YU0P3; uc3=nk2=AnDS93hBOWEYdpk%3D&id2=W8twrLUvJaM8&vt3=F8dAScH%2FJpyTg9qEJNc%3D&lg2=VT5L2FSpMGV7TQ%3D%3D; existShop=MTQ0ODE4Mjg4NQ%3D%3D; lgc=aa616095191; tracknick=aa616095191; sg=146; cookie2=3c03816f07cd8bcd2872be08eeea6e8a; mt=np=&ci=0_1; cookie1=UoH63f0hQrqOdpJbya2KiQs0ss%2FS7iTCpJ38n9RxOBQ%3D; unb=824664974; skt=958de47b52ef238e; t=163e4edf9106645ab8a9da2b7349e91f; _cc_=VFC%2FuZ9ajQ%3D%3D; tg=0; _l_g_=Ug%3D%3D; _nk_=aa616095191; cookie17=W8twrLUvJaM8; lzstat_uv=23111847702808552989|3600144; lzstat_ss=2480698172_0_1448211756_3600144; CNZZDATA30070035=cnzz_eid%3D2128470411-1448173462-%26ntime%3D1448178862; uc1=cookie14=UoWzUasgRRjnVw%3D%3D&existShop=false&cookie16=Vq8l%2BKCLySLZMFWHxqs8fwqnEw%3D%3D&cookie21=VT5L2FSpczFp&tag=3&cookie15=W5iHLLyFOGW7aA%3D%3D&pas=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; l=AtjYddje8w-Tq0A/wrkCUvGNKAhqwTxL; whl=-1%260%260%261448183569032; isg=0C5CA15AF002696028656B4396ADD60F"));
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
}

