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
			//filtercontexts过滤,input 输入, 显示2挑数据,每个Term都匹配,关键词高亮
			Long time1=System.currentTimeMillis();
			List<LookupResult> results = suggester.lookup(input, filtercontexts, 2, true, true);
			System.err.println(System.currentTimeMillis()-time1);
			for (LookupResult result : results) {
				System.out.println(result.key);
				System.out.println(result.highlightKey);
				//从payload中反序列化出Post对象
//				BytesRef bytesRef = result.payload;
//				InputStream in = new ByteArrayInputStream(bytesRef.bytes);
//				String title = CommonUtils.deSerialize(in,String.class);
//				System.out.println(title);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		getSuggestResult("天涯", "谈天说地");
	}
	
}

