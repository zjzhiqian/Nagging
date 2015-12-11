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
package com.hzq.lucene.suggest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.util.BytesRef;

import com.hzq.common.util.Utils;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.util.LuceneUtil;
import com.hzq.lucene.constant.ConstantLucene;

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
		
		AnalyzingInfixSuggester suggester=LuceneUtil.getIndexSuggester(ConstantLucene.Index_TianYaSuggest_Path);
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
	 * 创建索引提示
	 * @param posts
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月20日
	 */
	public static boolean createSuggestForTb(List<TaoBaoPost> posts){
		
		AnalyzingInfixSuggester suggester=LuceneUtil.getIndexSuggester(ConstantLucene.Index_TaoBaoSuggest_Path);
		try {
			suggester.build(new TBPostIterator(posts.iterator()));
			suggester.commit();
			suggester.close();
			LuceneUtil.taobaoSuggester=null;
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
	public static List<Map<String,Object>> getSuggestResult(String input,String folumn){
		List<Map<String, Object>> ListMap=new ArrayList<Map<String,Object>>();
		try {
			AnalyzingInfixSuggester suggester=LuceneUtil.getQuerySuggester(ConstantLucene.Index_TianYaSuggest_Path);
			HashSet<BytesRef> filtercontexts = new HashSet<BytesRef>();
			filtercontexts.add(new BytesRef(folumn.getBytes("UTF8")));
			Long time1=System.currentTimeMillis();
			//filtercontexts过滤,input 输入, 显示5条数据,每个Term都匹配,关键词高亮
			List<LookupResult> results = suggester.lookup(input, filtercontexts, 5, true, true);
			Map<String,Object> map = null;
			for (LookupResult result : results) {
				map=new HashMap<String, Object>();
				//从payload中反序列化出Post对象
				BytesRef bytesRef = result.payload;
				InputStream in = new ByteArrayInputStream(bytesRef.bytes);
				String url = Utils.deSerialize(in,String.class);
				map.put("key", result.highlightKey);
				map.put("url", url);
				map.put("time", System.currentTimeMillis()-time1);
				ListMap.add(map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ListMap;
	}
	
	
	/**
	 *  获取查询结果提示
	 * @param input	  用户输入
	 * @param folumn  筛选板块
	 * @author huangzhiqian
	 * @date 2015年11月20日
	 */
	public static List<Map<String,Object>> getSuggestResultForTB(String input,String folumn){
		List<Map<String, Object>> ListMap=new ArrayList<Map<String,Object>>();
		try {
			AnalyzingInfixSuggester suggester=LuceneUtil.getQuerySuggesterForTb(ConstantLucene.Index_TaoBaoSuggest_Path);
			HashSet<BytesRef> filtercontexts = new HashSet<BytesRef>();
			filtercontexts.add(new BytesRef(folumn.getBytes("UTF8")));
			Long time1=System.currentTimeMillis();
			//filtercontexts过滤,input 输入, 显示5条数据,每个Term都匹配,关键词高亮
			List<LookupResult> results = suggester.lookup(input, filtercontexts, 5, true, true);
			Map<String,Object> map = null;
			for (LookupResult result : results) {
				map=new HashMap<String, Object>();
				//从payload中反序列化出Post对象
				BytesRef bytesRef = result.payload;
				InputStream in = new ByteArrayInputStream(bytesRef.bytes);
				String url = Utils.deSerialize(in,String.class);
				map.put("key", result.highlightKey);
				map.put("url", url);
				map.put("time", System.currentTimeMillis()-time1);
				ListMap.add(map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ListMap;
	}
	
}

