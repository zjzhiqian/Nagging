/**
 * @(#)NRTJob.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月11日 huangzhiqian 创建版本
 */
package com.hzq.lucene.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

/**
 * 
 * Lucene近实时搜索定时任务的实现
 * @author huangzhiqian
 */
public class LuceneNRTJob {
	
	public static final Stack<TianYaPost> posts=new Stack<TianYaPost>();
	
	private static final Lock nrtLock = new ReentrantLock();
	
	public static Lock getNrtlock() {
		return nrtLock;
	}

	public void doIndex(){
		nrtLock.lock();
		try{
			indexDoc(LuceneUtil.getTianYaWriterOne(), posts);
			posts.clear();
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			nrtLock.unlock();
		}
	}
	
	private static void indexDoc(IndexWriter writer, Stack<TianYaPost> posts) throws Exception{
			Document doc = null;
			List<Document> docs = new ArrayList<Document>();
			for(TianYaPost post:posts){
				doc = new Document();
				// ID
				doc.add(new Field("id", "-1", LuceneUtil.IdFielType));
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
				doc.add(new LongField("click", 999L, Store.YES));
				// 排序处理
				doc.add(new NumericDocValuesField("click", 0L));
				// reply
				doc.add(new LongField("reply", 999L, Store.YES));
				// 排序处理
				doc.add(new NumericDocValuesField("reply", 0L));
				// isBest
				doc.add(new StringField("isBest", post.getIsBest(), Store.YES));
				
				docs.add(doc);
			}
			writer.addDocuments(docs);

	}
	
}
