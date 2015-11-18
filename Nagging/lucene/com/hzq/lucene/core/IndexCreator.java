/**
 * @(#)TianYaDataToIndex.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月18日 huangzhiqian 创建版本
 */
package com.hzq.lucene.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.util.LuceneUtil;
import com.hzq.system.constant.Constant;

/**
 * 
 * 生成多目录索引
 * 
 * @author huangzhiqian
 */
public class IndexCreator {

	/**
	 * 生成单目录索引
	 * 
	 * @param posts
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月18日
	 */
	public static boolean ToOnePath(List<TianYaPost> posts) {
		IndexWriter writer = LuceneUtil.getIndexWriter(Constant.Index_TianYaPost_Path);
		try {
			// 生成索引之前 先删除所有此目录下的索引
			writer.deleteAll();
			indexDoc(writer, posts);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			LuceneUtil.closeWriter(writer);
		}
		return true;
	}

	/**
	 * 生成多目录索引
	 * 
	 * @param posts
	 * @pathNum 目录数量(线程数量)
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月18日
	 */
	public static boolean ToMultiPath(List<TianYaPost> posts, int pathNum) throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(pathNum);
		posts.subList(0, posts.size());
		CountDownLatch countDownLatch1 = new CountDownLatch(1);
		CountDownLatch countDownLatch2 = new CountDownLatch(pathNum);
		List<List<TianYaPost>> list = DivideList(posts, pathNum);
		List<Future<Boolean>> result = new ArrayList<Future<Boolean>>();
		for (int i = 0; i < pathNum; i++) {
			Callable<Boolean> task = new MultiPathTask(Constant.Index_TianYaPost_MultiPath + (i + 1), list.get(i), countDownLatch1, countDownLatch2);
			Future<Boolean> rs = pool.submit(task);
			result.add(rs);
		}
		countDownLatch1.countDown();// begin
		countDownLatch2.await();// wait
		pool.shutdown();// release
		int successNum = 0;
		for (Future<Boolean> rs : result) {
			if (rs.get()) {
				successNum++;
			}
		}
		if (successNum < pathNum) {
			throw new Exception(String.format("有%d个目录索引未生成成功", pathNum - successNum));
		}
		return true;
	}

	private static class MultiPathTask implements Callable<Boolean> {
		private final String path;
		private final List<TianYaPost> posts;
		private final CountDownLatch countDownLatch1;
		private final CountDownLatch countDownLatch2;

		public MultiPathTask(String path, List<TianYaPost> posts, CountDownLatch countDownLatch1, CountDownLatch countDownLatch2) {
			this.path = path;
			this.posts = posts;
			this.countDownLatch1 = countDownLatch1;
			this.countDownLatch2 = countDownLatch2;
		}

		@Override
		public Boolean call() throws Exception {
			countDownLatch1.await();
			IndexWriter writer = LuceneUtil.getIndexWriter(path);
			try {
				// 生成索引之前 先删除所有此目录下的索引
				writer.deleteAll();
				indexDoc(writer, posts);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				LuceneUtil.closeWriter(writer);
				countDownLatch2.countDown();
			}
			return true;
		}

	}

	/**
	 * 用指定writer生成指定post数据的索引
	 * 
	 * @param writer
	 * @param posts
	 * @throws Exception
	 * @author huangzhiqian
	 * @throws IOException
	 * @date 2015年11月18日
	 */
	private static void indexDoc(IndexWriter writer, List<TianYaPost> posts) throws IOException {

		Document doc = null;
		for (TianYaPost post : posts) {
			doc = new Document();
			// ID
			doc.add(new Field("id", post.getId() + "", LuceneUtil.IdFielType));
			doc.add(new Field("title", post.getTitle() + "", LuceneUtil.TitleFielType));
			String content = post.getContent().toLowerCase();
			doc.add(new Field("content", content, LuceneUtil.ContentFielType));
			if (content.length() > 20) {
				content = content.substring(0, 20);
			}
			// content结果显示
			doc.add(new Field("storedcontent", content, LuceneUtil.OnLyStoreFieldType));
			// url
			doc.add(new StringField("url", post.getUrl(), Store.YES));
			// addUserId
			doc.add(new StringField("adduser", post.getAdduserId(), Store.YES));
			// addUsername
			doc.add(new TextField("addusername", post.getAdduserName(), Store.YES));
			if (post.getAddTime() != null) {
				doc.add(new LongField("addtime", post.getAddTime().getTime(), Store.YES));
				// 排序处理
				doc.add(new NumericDocValuesField("addtime", post.getAddTime().getTime()));
			}
			// lastReplyTime
			doc.add(new LongField("lastreplytime", post.getLastReplyTime().getTime(), Store.YES));
			// click
			doc.add(new LongField("click", post.getClick(), Store.YES));
			// 排序处理
			doc.add(new NumericDocValuesField("click", post.getClick()));
			// reply
			doc.add(new LongField("reply", post.getReply(), Store.YES));
			// 排序处理
			doc.add(new NumericDocValuesField("reply", post.getReply()));
			// isBest
			doc.add(new StringField("isBest", post.getIsBest(), Store.YES));

			writer.addDocument(doc);
		}

	}

	/**
	 * 拆分list
	 * 
	 * @param posts
	 * @param Num
	 *            份数
	 * @return 拆分后的List<List<T>>
	 * @author huangzhiqian
	 * @date 2015年11月18日
	 */
	private static <T> List<List<T>> DivideList(List<T> posts, int Num) {
		List<List<T>> rsList = new ArrayList<List<T>>();
		int perNum = (int) Math.ceil(posts.size() / Num);
		if (posts.size() % Num == 0) { // 如果刚好整除
			for (int i = 0; i < Num; i++) {
				rsList.add(posts.subList(i * perNum, (i + 1) * perNum));
			}
		} else {
			for (int i = 0; i < Num - 1; i++) {
				rsList.add(posts.subList(i * perNum, (i + 1) * perNum));
			}
			rsList.add(posts.subList(perNum * (Num - 1), posts.size()));
		}
		return rsList;
	}

}
