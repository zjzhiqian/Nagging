package com.hzq.lucene.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.util.ThreadService;
import com.hzq.lucene.web.TianYaGrabController;

/**
 * 抓取数据并解析数据
 * 
 * @author huangzhiqian
 * @date 2015年10月18日 下午10:24:42
 */
public class TianYaDataAnalysis {
	private static final String TIANYA_URL = "http://bbs.tianya.cn/list-lookout-1.shtml";
	private static CloseableHttpClient httpclient = null;
	static {
		httpclient = HttpClients.custom().build();
	}

	/**
	 * 进行天涯帖子数据抓取
	 * 
	 * @author huangzhiqian
	 * @date 2015年10月22日
	 */
	public static void grabTianYaData() {
		AddTianYaPostByUrl(TIANYA_URL);
	}

	private static void AddTianYaPostByUrl(String tianyaUrl) {
		try {
			String content = "";
			CloseableHttpResponse response = null;
			try {
				HttpGet httpget = new HttpGet(tianyaUrl);
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

			Document doc = Jsoup.parse(content);

			Elements elements = doc.select("tr");
			// 0是标题,移除掉
			elements.remove(0);
			TianYaPost post = null;
			for (Element element : elements) {
				Elements tdEle = element.select("td");
				post = new TianYaPost();

				Element ele00 = tdEle.get(0);
				// isBest
				if (ele00.attr("class").indexOf("facered") != -1) {
					post.setIsBest("1");
				} else {
					post.setIsBest("0");
				}
				// title
				Elements ele01 = ele00.select("a");

				String title = ele01.text();
				if (StringUtils.isNotEmpty(title)) {
					post.setTitle(title.trim());
				}
				// url,content,AddTime
				String href = "";
				if (ele01 != null && ele01.size() > 0) {
					href = ele01.get(0).attr("href");
					if (StringUtils.isNotEmpty(href)) {
						String postUrl = "http://bbs.tianya.cn" + href.trim();
						post.setUrl(postUrl);
						// 开启异步线程根据url抓取帖子内容,帖子新增时间,并且set到Post里面
						ThreadService.getThreadService().execute(new TianYaPageTask(post, postUrl));
					}

				}
				// 解析List异步线程
				ThreadService.getThreadService().execute(new TianYaListTask(post, tdEle));
				TianYaGrabController.getDataList().add(post);
			}
			// 下一页按钮的url(开启异步线程根据下一页url继续解析)
			Elements nexts = doc.select("div.links");
			nexts = nexts.get(0).select("a");
			// 如果不包含下一页表示解析结束
			if (!nexts.text().contains("下一页")) {
				// 睡眠60秒,保证其他线程的解析出来的数据set到Post中
				try {
					Thread.sleep(120000L);
				} catch (InterruptedException e) {
					// doNothing
				}
				TianYaGrabController.FinishFlag = true;
				System.err.println(tianyaUrl);
				System.exit(0);
			}
			for (Element next : nexts) {
				if (next.text().indexOf("下一页") != -1) {
					String pageUrl = next.attr("href");
					String nextPageUrl = "http://bbs.tianya.cn/" + pageUrl;
					// 递归(同步)
					AddTianYaPostByUrl(nextPageUrl);
				}
			}
		} catch (Exception e) {
			// 捕获异常继续执行
			AddTianYaPostByUrl(tianyaUrl);
		}

	}

	/**
	 * 处理从详情页面获取的数据异步Task(包含抓取和解析)
	 * 
	 * @author huangzhiqian
	 * @date 2015年10月23日 下午8:15:40
	 */
	private static class TianYaPageTask implements Runnable {
		private TianYaPost post;
		private String postUrl;

		public TianYaPageTask(TianYaPost post, String postUrl) {
			this.post = post;
			this.postUrl = postUrl;
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
						HttpGet postGet = new HttpGet(postUrl);
						postGet.setConfig(RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(40000).setSocketTimeout(40000).build());
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

					Document doc = Jsoup.parse(postContent);
					// 发帖时间获取
					Elements eles = doc.select("div.atl-info");
					for (Element ele : eles) {
						String eleText = ele.text();
						if (eleText.contains("时间") && eleText.contains("回复") && eleText.contains("点击")) {
							String time = ele.text().substring(eleText.indexOf("时间：") + 3, eleText.indexOf("点击")).trim();
							Date addTime = null;
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								addTime = sdf.parse(time);
								post.setAddTime(addTime);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							break;
						}
					}
					// 帖子内容
					Elements eles2 = doc.getElementsByAttributeValue("class", "bbs-content clearfix");
					for (Element ele2 : eles2) {
						String content = ele2.text();
						content.replaceAll("<br>", "");
						post.setContent(content);
					}
				}
			} catch (ConnectTimeoutException | java.net.SocketException | java.net.SocketTimeoutException e) {
				if (post.getRetryCount().incrementAndGet() < 10) {
					ThreadService.getThreadService().execute(new TianYaPageTask(post, postUrl));
				}
			} catch (Exception e) {

			}

		}
	}

	/**
	 * 处理从List页面抓取的数据异步Task(只包含解析)
	 * 
	 * @author huangzhiqian
	 * @date 2015年10月23日 下午8:15:23
	 */
	private static class TianYaListTask implements Runnable {
		private TianYaPost post;
		private Elements tdEle;

		public TianYaListTask(TianYaPost post, Elements tdEle) {
			this.post = post;
			this.tdEle = tdEle;
		}

		@Override
		public void run() {
			try {
				// userId,userName
				Element ele10 = tdEle.get(1);
				String username = ele10.text();
				if (StringUtils.isNotEmpty(username)) {
					post.setAdduserName(username.trim());
				}
				Elements ele11 = ele10.select("a");
				if (ele11 != null && ele11.size() > 0) {
					String UserIdHref = ele11.get(0).attr("href");
					if (StringUtils.isNotEmpty(UserIdHref)) {
						UserIdHref = UserIdHref.trim();
						String userId = UserIdHref.substring(UserIdHref.lastIndexOf("/") + 1, UserIdHref.length());
						if (StringUtils.isNotEmpty(userId)) {
							post.setAdduserId(userId);
						}
					}
				}
				// 点击数
				Element ele20 = tdEle.get(2);
				if (StringUtils.isNotEmpty(ele20.text())) {
					post.setClick(Long.parseLong(ele20.text()));
				}
				// 回复数
				Element ele30 = tdEle.get(3);
				if (StringUtils.isNotEmpty(ele30.text())) {
					post.setReply(Long.parseLong(ele30.text()));
				}
				// 最后回复时间
				Element ele40 = tdEle.get(4);
				try {
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date d = sdf2.parse(ele40.attr("title"));
					post.setLastReplyTime(d);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
	}

}
