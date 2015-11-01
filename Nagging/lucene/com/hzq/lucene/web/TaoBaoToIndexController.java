package com.hzq.lucene.web;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzq.common.entity.Json;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.service.TianYaPostService;
import com.hzq.lucene.util.LuceneUtil;
import com.hzq.system.constant.Constant;

/**
 * 用于生成索引
 * @author huangzhiqian
 * @date 2015年11月1日 下午9:05:52
 */
@Controller
@RequestMapping("lucene")
public class TaoBaoToIndexController {
	@Autowired
	TianYaPostService tianYaPostService;
	/**
	 * 淘宝帖子索引文件生成
	 * 
	 * @return
	 */
	@RequestMapping("taobaoPostIndex")
	@ResponseBody
	public Json tianyaPostIndex() {
		
		Long time1=System.currentTimeMillis();
		IndexWriter writer = LuceneUtil.getIndexWriter(Constant.Index_TianYaPost_Path);
		List<TaoBaoPost> posts = tianYaPostService.findAllTbPosts();
		System.err.println("查询花费时间"+(System.currentTimeMillis()-time1));
		Document doc = null;
		try {
			//生成索引之前 先删除所有此目录下的索引
			writer.deleteAll();
			for (TaoBaoPost post : posts) {
				doc = new Document();
				//ID
				doc.add(new Field("id", post.getId() + "", LuceneUtil.IdFielType));
				doc.add(new Field("title", post.getTitle() + "", LuceneUtil.TitleFielType));
				doc.add(new Field("modual", post.getModual() + "", LuceneUtil.IdFielType));
				
				writer.addDocument(doc);
			}
		    System.err.println("索引查询一共花费时间"+(System.currentTimeMillis()-time1));
		} catch (Exception e) {
			e.printStackTrace();
			return new Json(false);
		}finally{
			LuceneUtil.closeWriter(writer);
		}
		Json json=new Json(true,String.format("生成成功,用了%s毫秒",System.currentTimeMillis()-time1+""));
		return json;
	}
	
	
	
}
