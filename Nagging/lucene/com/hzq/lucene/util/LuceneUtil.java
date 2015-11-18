package com.hzq.lucene.util;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.hzq.lucene.synonym.IKSynonymAnalyzer;
import com.hzq.lucene.synonym.TxtSynonymEngine;
/**
 * Lucene工具类,默认分词器为IKAnalyser
 * @author huangzhiqian
 * @date 2015年10月24日 下午5:57:17
 */
public class LuceneUtil {
	
	/**ID Field (保存内容,不分词,忽略权重)**/
	public static FieldType IdFielType =null;
	/**Content Field (不保存内容,分词,保留权重,偏移量等)**/
	public static FieldType ContentFielType =null;
	/**Title Field(保存内容,分词,保留权重,偏移量等)**/
	public static FieldType TitleFielType =null;
	/**只用于存储一些信息**/
	public static FieldType OnLyStoreFieldType = null;
	
	private static Analyzer defaultAnalyzer = null;
	static {
		defaultAnalyzer=getAnalyzer();
		//  Id
		IdFielType = new FieldType();
		IdFielType.setStored(true);
		IdFielType.setTokenized(false);
		IdFielType.setOmitNorms(true);
		IdFielType.setIndexOptions(IndexOptions.DOCS);
		IdFielType.freeze();
		
		// content
		ContentFielType= new FieldType();
		ContentFielType.setStored(false);
		ContentFielType.setTokenized(true);
		ContentFielType.setOmitNorms(false);
		ContentFielType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		ContentFielType.freeze();
		
		// title
		TitleFielType= new FieldType();
		TitleFielType.setStored(true);
		TitleFielType.setTokenized(true);
		TitleFielType.setOmitNorms(false);
		TitleFielType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		TitleFielType.freeze();
		
		//onlyForStore
		OnLyStoreFieldType= new FieldType();
		OnLyStoreFieldType.setStored(true);
		OnLyStoreFieldType.setTokenized(false);
		OnLyStoreFieldType.setOmitNorms(false);
		OnLyStoreFieldType.setIndexOptions(IndexOptions.NONE);
		OnLyStoreFieldType.freeze();
	}
	/**
	 * 指定目录,使用默认分词器获取写索引Writer
	 * @param path
	 * @return
	 */
	public static IndexWriter getIndexWriter(String path){
		return getIndexWriter(path,defaultAnalyzer);
	}
	
	/**
	 * 指定目录,分词器获取写索引Writer
	 * @param path
	 * @param analyzer
	 * @return
	 */
	public static IndexWriter getIndexWriter(String path,Analyzer analyzer){
		IndexWriter writer =null;
		try {
			Directory directory = FSDirectory.open(Paths.get(path));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, config);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return writer;
	}
	
	/**
	 * 关闭Writer
	 * @param writer
	 */
	public static void closeWriter(IndexWriter writer){
		try {
			if (writer != null)
				writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 返回IK分词器
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月17日
	 */
	public static Analyzer getAnalyzer(){
		return new IKAnalyzer();
	}
	
	/**
	 * 返回IK同义词分词器
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月17日
	 */
	public static Analyzer getSynonymAnalyzer(){
		return new IKSynonymAnalyzer(new TxtSynonymEngine());
		
	}
	
	/**
	 * 
	 * @param query 索引查询对象
	 * @param prefix 高亮前缀字符串
	 * @param suffix 高亮后缀字符串
	 * @param fragmenterLength 摘要最大长度
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月18日
	 */
	public static Highlighter createHighlighter(Query query, String prefix, String suffix, int fragmenterLength) {
		Formatter formatter = new SimpleHTMLFormatter((prefix == null || prefix.trim().length() == 0) ? 
			"<font color=\"blue\">" : prefix, (suffix == null || suffix.trim().length() == 0)?"</font>" : suffix);
		Scorer fragmentScorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
		Fragmenter fragmenter = new SimpleFragmenter(fragmenterLength <= 0 ? 50 : fragmenterLength);
		highlighter.setTextFragmenter(fragmenter);
		return highlighter;
	}

	
}
