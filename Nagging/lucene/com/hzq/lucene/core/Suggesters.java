/**
 * @(#)SuggesterCreator.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月20日 huangzhiqian 创建版本
 */
package com.hzq.lucene.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.util.BytesRef;

import com.hzq.common.util.CommonUtils;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.util.LuceneUtil;
import com.hzq.system.constant.Constant;

/**
 * 
 * 生成搜索提示内容
 * @author huangzhiqian
 */
public class Suggesters {
	
	/**
	 * 创建索引提示
	 * @param posts
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月20日
	 */
	public static boolean createSuggest(List<TianYaPost> posts){
		
		AnalyzingInfixSuggester suggester=LuceneUtil.getSuggester(Constant.Index_TianYaSuggest_Path);
		try {
			suggester.build(new PostIterator(posts.iterator()));
			suggester.commit();
			suggester.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	/**
	 *  获取查询结果提示
	 * @param input	  用户输入
	 * @param folumn  筛选板块
	 * @author huangzhiqian
	 * @date 2015年11月20日
	 */
	public static void getSuggestResult(String input,String folumn){
		try {
			AnalyzingInfixSuggester suggester=LuceneUtil.getSuggester(Constant.Index_TianYaSuggest_Path);
			
			HashSet<BytesRef> filtercontexts = new HashSet<BytesRef>();
			filtercontexts.add(new BytesRef(folumn.getBytes("UTF8")));
			//先以contexts为过滤条件进行过滤，再以name为关键字进行筛选，根据weight值排序返回前2条
			//第3个布尔值即是否每个Term都要匹配，第4个参数表示是否需要关键字高亮
			List<LookupResult> results = suggester.lookup(input, filtercontexts, 2, true, true);
			for (LookupResult result : results) {
				System.out.println(result.key);
				//从payload中反序列化出Post对象
				BytesRef bytesRef = result.payload;
				InputStream in = new ByteArrayInputStream(bytesRef.bytes);
				TianYaPost post = CommonUtils.deSerialize(in,TianYaPost.class);
				System.out.println(post);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		getSuggestResult("天涯", "谈天说地");
	}
	
}

