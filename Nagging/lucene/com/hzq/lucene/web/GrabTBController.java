package com.hzq.lucene.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hzq.common.util.SpringContextUtils;
import com.hzq.lucene.entity.BBSModual;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.service.TaoBaoPostService;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.lucene.util.ThreadService;

@Controller
@RequestMapping("lucene")
public class GrabTBController {
	@Autowired
	TaoBaoPostService taoBaoPostService;
	
	private static final String COOKIE_VAL="mt=ci%3D-1_0; swfstore=208512; thw=cn; cna=TJDXDmZfojkCAbeUx3aHdj4+; lzstat_uv=23111847702808552989|3600144; showPopup=3; v=0; _tb_token_=yQy202z3nXbZXB; lzstat_ss=916384926_0_1448565230_3600144; uc3=nk2=AnDS93hBOWEYdpk%3D&id2=W8twrLUvJaM8&vt3=F8dAScAeBM%2FuK1py27g%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; existShop=MTQ0ODUzNjM4MA%3D%3D; lgc=aa616095191; tracknick=aa616095191; sg=146; cookie2=1444c520f3e6f391a0ef057f87018bf9; mt=np=&ci=0_1; cookie1=UoH63f0hQrqOdpJbya2KiQs0ss%2FS7iTCpJ38n9RxOBQ%3D; unb=824664974; skt=d0a5896e3916b5ae; t=163e4edf9106645ab8a9da2b7349e91f; _cc_=VFC%2FuZ9ajQ%3D%3D; tg=0; _l_g_=Ug%3D%3D; _nk_=aa616095191; cookie17=W8twrLUvJaM8; CNZZDATA30070035=cnzz_eid%3D2128470411-1448173462-%26ntime%3D1448532737; uc1=cookie14=UoWzUa%2BzOsWHsA%3D%3D&existShop=false&cookie16=W5iHLLyFPlMGbLDwA%2BdvAGZqLg%3D%3D&cookie21=U%2BGCWk%2F7pY%2FF&tag=3&cookie15=Vq8l%2BKCLz3%2F65A%3D%3D&pas=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; l=Anp6k1AJMUF9oSKZtOOAK-FhSq6cK/4F; whl=-1%260%260%261448536458826; isg=706B6187DB449D3B9C23ADEA3C8F7996";
	private static final String TAOBAO_URL="https://bbs.taobao.com/catalog/438501.htm?spm=0.0.0.0.SSGqE7";
	private static CloseableHttpClient httpclient = null;
	/**标记是否抓取完成的Flag**/
	public static boolean finishFlag=false;
	static{
		httpclient = HttpClients.custom().build();
	}
	
	@RequestMapping(value="taobao",method=RequestMethod.GET)
	public void begin(){
		doHttp();
	}
	
	
	@RequestMapping(value="getDetailData",method=RequestMethod.GET)
	public void doUpdate(){
		int step=100000;
		
		for(int i=800000;i<900000;i=i+step){
			
			List<TaoBaoPost> posts=taoBaoPostService.findLimitedPost(i,step);
			for(TaoBaoPost post:posts){
				if(StringUtils.isEmpty(post.getContent())){
					String content=getPostData(post.getUrl());
					ThreadService.getThreadService().execute(new saveTask(content, post.getId()));
				}
			}
			
		}
	}
	
	/**
	 * 保存异步任务
	 * @author huangzhiqian
	 *
	 */
	private class saveTask implements Runnable{
		private final String content;
		private final Long id;
		saveTask(String content,Long id){
			this.content=content;
			this.id=id;
		}
		@Override
		public void run() {
			TaoBaoPost post=new TaoBaoPost();
			post.setId(id);
			Document doc = Jsoup.parse(content);
			Elements ele=doc.select("span.time-author");
			//发帖时间
			if(ele!=null&&ele.size()>0){
				String sendTime=ele.get(0).text();
				sendTime=sendTime.replace("|", "");
				sendTime=sendTime.replace("发表于", "");
				sendTime=sendTime.replace("只看楼主", "").trim();
				try {
					post.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sendTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			//发帖人名称
			ele=doc.select("div.user-info-wrap");
			if(ele!=null&&ele.size()>0){
				Elements tmpEle=ele.select("h5");
				if(tmpEle!=null&&tmpEle.size()>0){
					post.setAdduserName(tmpEle.get(0).text().trim());
				}
			}
			//内容
			ele=doc.select("div.ke-post");
			if(ele!=null&&ele.size()>0){
				String data=ele.get(0).text().trim();
				if(data.length()>=20000){
					data="过长暂不存入";
				}
				post.setContent(data);
			}
			//查看
			ele=doc.select("div.visitor");
			if(ele!=null&&ele.size()>0){
				Elements tmpEle=ele.select("em");
				String watch="";
				String reply="";
				if(tmpEle!=null&&tmpEle.size()==2){
					watch=tmpEle.get(0).text().trim();
					reply= tmpEle.get(1).text().trim();
					
				}else if(tmpEle.size()==1){
					watch=tmpEle.get(0).text().trim();
					tmpEle=ele.select("span");
					if(tmpEle!=null&&tmpEle.size()==1){
						reply= tmpEle.get(0).text().trim();
					}
				}else{
					tmpEle=ele.select("span");
					if(tmpEle!=null&&tmpEle.size()==2){
						watch=tmpEle.get(0).text().trim();
						reply= tmpEle.get(1).text().trim();
					}
				}
				if(StringUtils.isEmpty(watch)){
					watch="0";
				}
				if(StringUtils.isEmpty(reply)){
					reply="0";
				}
				post.setClick(Long.parseLong(watch));
				post.setReply(Long.parseLong(reply));
			}
			
			if(StringUtils.isEmpty(post.getContent())){
				//如果无数据抓取失败,设置为-99
				post.setReply(-99L);
				post.setClick(-99L);
			}
			boolean flag=taoBaoPostService.updatePost(post);
			if(flag){
				System.out.println(post.getId()+"...成功");
			}else{
				System.err.println(post.getId()+"...失败");
			}
		}
	}
	
	/**
	 * 抓取并解析的详情页面
	 * @param url
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月25日
	 */
	private String getPostData(String url){
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
			if(content.contains("全登陆不允许iframe嵌入")){
				throw new RuntimeException("全登陆不允许iframe嵌入,需要重新登录");
			}
			return content;
		} catch (Exception e) {
			// 捕获异常继续执行
			e.printStackTrace();
			return null;
		}

	}
	
	private static void doHttp(){
		finishFlag=false;
		try{
			String content="";
			HttpGet httpget=null;
			CloseableHttpResponse response = null;
			try {
				httpget = new HttpGet(TAOBAO_URL);
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
					checkcontent(content);
					EntityUtils.consume(entity);
					List<BBSModual> moduals =getModuales(content);
					for(BBSModual modual:moduals){
						//单线程
						getDataforModual(modual,modual.getUrl(),modual.getUrl());
					}
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
	
	/**
	 * 获取BBS每个模块的数据
	 * @param modual 模块类
	 * @author huangzhiqian
	 * @param httpclient2 
	 * @date 2015年10月29日
	 */
	private static void getDataforModual(BBSModual modual,String modualUrl,String nextPage) {
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
				checkcontent(content);
				EntityUtils.consume(entity);
				Document doc=Jsoup.parse(content);
				//title,url
				Elements eles=doc.select("div.detail a");
				TaoBaoPost post=null;
				TianYaPostService service=(TianYaPostService) SpringContextUtils.getBean("tianYaPostServiceImpl");
				for(Element ele:eles){
					post=new TaoBaoPost();
					String url=ele.attr("href");
					url="https:"+url;
					String title=ele.attr("title");
					post.setUrl(url);
					post.setTitle(title);
					post.setModual(modual.getName());
					post.setNextpageurl(nextPage);
					service.add(post);
				}
				//递归终止条件
				Elements npgeles=doc.getElementsByAttributeValue("class","page-next j_page_changer");
				//如果是最后一页
				if(npgeles.size()==0){
					System.out.println(modual+"此模块数据抓取完毕");
				}else{
					//递归执行下一条
					nextPage="https:"+npgeles.get(0).attr("href");
					getDataforModual(modual,nextPage,nextPage);
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
			getDataforModual(modual,modualUrl,nextPage);
		}
		
		
	}
	
	private static void checkcontent(String content){
		if(content.contains("对不起，系统繁忙，请提交验证码后继续")){
			throw new RuntimeException("对不起，系统繁忙，请提交验证码后继续。");
		}
	}
	
	/**请求头**/
	private static void setHeadersForGet(HttpGet httpget) {
		httpget.addHeader(new BasicHeader("cookie",COOKIE_VAL));
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
	
	/**请求头**/
	private static void setHeadersForGet2(HttpGet httpget,String modualUrl) {
		httpget.addHeader(new BasicHeader("cookie",COOKIE_VAL));
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
	
	public static void main(String[] args) {
		doHttp();
	}
}
