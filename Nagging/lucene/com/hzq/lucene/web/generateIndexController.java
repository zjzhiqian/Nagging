package com.hzq.lucene.web;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.lucene.util.LuceneIndexUtil;
import com.hzq.system.constant.Constant;

/**
 * 用于生成索引
 * 
 * @author huangzhiqian
 * @date 2015年10月24日 下午4:20:30
 */
@Controller
@RequestMapping("lucene")
public class generateIndexController {
	@Autowired
	TianYaPostService tianYaPostService;

	/**
	 * 天涯帖子索引文件生成
	 * 
	 * @return
	 */
	@RequestMapping("tianyaPostIndex")
	@ResponseBody
	public Json tianyaPostIndex() {
		Long time1=System.currentTimeMillis();
		IndexWriter writer = LuceneIndexUtil.getIndexWriter(Constant.Index_TianYaPost_Path);
		List<TianYaPost> posts = tianYaPostService.findAllPosts();
		Document doc = null;
		try {
			//生成索引之前 先删除所有此目录下的索引
			writer.deleteAll();
			for (TianYaPost post : posts) {
				doc = new Document();
				//ID
				doc.add(new Field("id", post.getId() + "", LuceneIndexUtil.IdFielType));
				//content TODO content为空
				doc.add(new Field("content", post.getContent() == null ? "" : post.getContent().toLowerCase(), LuceneIndexUtil.ContentFielType));
				//url
				doc.add(new StringField("url", post.getUrl(), Store.YES));
				//addUserId
				doc.add(new StringField("adduser", post.getAdduserId(), Store.YES));
				//addUsername
				doc.add(new TextField("addusername", post.getAdduserName(), Store.YES));
				//addTime TODO getAddTime为空
				doc.add(new LongField("addtime", post.getAddTime() == null ? System.currentTimeMillis() : post.getAddTime().getTime(), Store.YES));
				//lastReplyTime
				doc.add(new LongField("lastreplytime", post.getLastReplyTime().getTime(), Store.YES));
//				TODO 需要排序需另外处理
//				只有这样才可以对指定域进行排序(既要加这个DocValueField 也要加Field)
//				dfDocument.add(new NumericDocValuesField("id",Long.parseLong(id)));  
				writer.addDocument(doc);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return new Json(false);
		}finally{
			LuceneIndexUtil.closeWriter(writer);
		}
		Json json=new Json(true,String.format("生成成功,用了%s毫秒",System.currentTimeMillis()-time1+""));
		return json;
	}
}
