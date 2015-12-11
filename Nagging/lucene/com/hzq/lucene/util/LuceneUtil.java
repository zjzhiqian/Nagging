package com.hzq.lucene.util;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.CusHzqHighlighter;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.hzq.lucene.constant.ConstantLucene;
import com.hzq.lucene.pinyin.IKPinYinSynonymAnalyzer;
import com.hzq.lucene.suggest.HighterInfixSuggester;
import com.hzq.lucene.synonym.IKSynonymAnalyzer;
import com.hzq.lucene.synonym.TxtSynonymEngine;

/**
 * Lucene工具类,用于获取Writer,Searcher的公用方法
 * 
 * @author huangzhiqian
 * @date 2015年10月24日 下午5:57:17
 */
@Component
public class LuceneUtil {

	/** ID Field (保存内容,不分词,忽略权重) **/
	public static FieldType IdFielType = null;
	/** Content Field (不保存内容,分词,保留权重,偏移量等) **/
	public static FieldType ContentFielType = null;
	/** Title Field(保存内容,分词,保留权重,偏移量等) **/
	public static FieldType TitleFielType = null;
	/** 只用于存储一些信息 **/
	public static FieldType OnLyStoreFieldType = null;
	static {
		// Id
		IdFielType = new FieldType();
		IdFielType.setStored(true);
		IdFielType.setTokenized(false);
		IdFielType.setOmitNorms(true);
		IdFielType.setIndexOptions(IndexOptions.DOCS);
		IdFielType.freeze();
		// content
		ContentFielType = new FieldType();
		ContentFielType.setStored(false);
		ContentFielType.setTokenized(true);
		ContentFielType.setOmitNorms(false);
		ContentFielType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		ContentFielType.freeze();
		// title
		TitleFielType = new FieldType();
		TitleFielType.setStored(true);
		TitleFielType.setTokenized(true);
		TitleFielType.setOmitNorms(false);
		TitleFielType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		TitleFielType.freeze();
		// onlyForStore
		OnLyStoreFieldType = new FieldType();
		OnLyStoreFieldType.setStored(true);
		OnLyStoreFieldType.setTokenized(false);
		OnLyStoreFieldType.setOmitNorms(false);
		OnLyStoreFieldType.setIndexOptions(IndexOptions.NONE);
		OnLyStoreFieldType.freeze();

	}

	/**
	 * 指定目录,分词器获取写索引Writer
	 * 
	 * @param path
	 * @param analyzer
	 * @return
	 */
	public static IndexWriter getIndexWriter(String path, Analyzer analyzer) {
		IndexWriter writer = null;
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
	 * 
	 * @param writer
	 */
	public static void closeWriter(IndexWriter writer) {
		try {
			if (writer != null)
				writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static DirectoryReader TianYareader = null;
	private static IndexWriter TianYaWriter = null;
	public static IndexWriter getTianYaWriterOne(){
		if(TianYaWriter == null){
			try {
				Directory dir = FSDirectory.open(Paths.get(ConstantLucene.Index_TianYaPost_Path));
				TianYaWriter =new  IndexWriter(dir, new IndexWriterConfig(LuceneUtil.getQueryAnalyzer()));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("获取Writer失败");
			}
		}
		return TianYaWriter;
	}

	/**
	 * 获取天涯论坛Searcher(单目录)
	 * 
	 * @return
	 */
	public static IndexSearcher getTYSearcherOnePath() {
		if (TianYareader == null) {
			try {
				TianYareader = DirectoryReader.open(getTianYaWriterOne(), false);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("获取Writer失败");
			}
		}

		try {
			return new IndexSearcher(TianYareader);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取IndexSearcher出错");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static MultiReader TianYaMultireader = null;

	/**
	 * 获取天涯论坛Searcher(多目录)
	 * 
	 * @return
	 */
	public static IndexSearcher getTYSearcherMulti() {
		if (TianYaMultireader == null) {
			try {
				IndexReader[] readers = new IndexReader[ConstantLucene.Index_TianYaPost_MultiPathNum];
				IndexReader reader = null;
				for (int i = 0; i < ConstantLucene.Index_TianYaPost_MultiPathNum; i++) {
					Directory dic = FSDirectory.open(Paths.get(ConstantLucene.Index_TianYaPost_MultiPath + (i + 1)));
					reader = DirectoryReader.open(dic);
					readers[i] = reader;
				}
				TianYaMultireader = new MultiReader(readers);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			return new IndexSearcher(TianYaMultireader);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取IndexSearcher出错");
		}
	}

	private static HighterInfixSuggester tianyaSuggester = null;

	/**
	 * 提示部分索引
	 * 
	 * @param path
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	public static AnalyzingInfixSuggester getQuerySuggester(String path) {
		if (tianyaSuggester == null) {
			try {
				Directory indexDir = FSDirectory.open(Paths.get(path));
				tianyaSuggester = new HighterInfixSuggester(indexDir, getCreateAnalyzer());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("failure accured in create suggester");
			}
		}
		return tianyaSuggester;
	}

	/**
	 * 获取索引时的Suggester
	 * 
	 * @param path
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	public static AnalyzingInfixSuggester getIndexSuggester(String path) {
		try {
			Directory indexDir = FSDirectory.open(Paths.get(path));
			return new HighterInfixSuggester(indexDir, getCreateAnalyzer());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("failure accured in create suggester");
		}
	}

	public static HighterInfixSuggester taobaoSuggester = null;

	/**
	 * 提示部分索引
	 * 
	 * @param path
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	public static AnalyzingInfixSuggester getQuerySuggesterForTb(String path) {
		if (taobaoSuggester == null) {
			try {
				Directory indexDir = FSDirectory.open(Paths.get(path));
				taobaoSuggester = new HighterInfixSuggester(indexDir, getQueryAnalyzer());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("failure accured in create suggester");
			}
		}
		return taobaoSuggester;
	}

	/**
	 * 提示部分索引
	 * 
	 * @param path
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	public static AnalyzingInfixSuggester getIndexSuggesterForTb(String path) {
		try {
			Directory indexDir = FSDirectory.open(Paths.get(path));
			return new HighterInfixSuggester(indexDir, getQueryAnalyzer());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("failure accured in create suggester");
		}
	}

	/**
	 * 生成索引的分词器
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	public static Analyzer getCreateAnalyzer() {
		return new IKPinYinSynonymAnalyzer(new TxtSynonymEngine());
	}

	/**
	 * 生成查询时的默认分词器
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	public static Analyzer getQueryAnalyzer() {
		return new IKAnalyzer();
	}

	/**
	 * 返回标准分词器
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月24日
	 */
	public static Analyzer getStandardAnalyzer() {
		return new StandardAnalyzer();
	}

	/**
	 * 返回IK分词器
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月17日
	 */
	public static Analyzer getIKAnalyzer() {
		return new IKAnalyzer();
	}

	/**
	 * 返回IK同义词分词器
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月17日
	 */
	public static Analyzer getIKSynonymAnalyzer() {
		return new IKSynonymAnalyzer(new TxtSynonymEngine());

	}

	/**
	 * 返回IK同义词,拼音分词器
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月24日
	 */
	public static Analyzer getIKPinyinAnalyzer() {
		return new IKPinYinSynonymAnalyzer(new TxtSynonymEngine());
	}

	/**
	 * 
	 * @param query 索引查询对象
	 * @param prefix 高亮前缀
	 * @param suffix 高亮后缀
	 * @param fragmenterLength 摘要最大长度
	 * @param ifCustom 是否自定义Highlighter
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	public static Highlighter createHighlighter(Query query, String prefix, String suffix, int fragmenterLength,boolean ifCustom) {
		Formatter formatter = new SimpleHTMLFormatter((prefix == null || prefix.trim().length() == 0) ? "<font color=\"blue\">" : prefix, (suffix == null || suffix.trim().length() == 0) ? "</font>": suffix);
		Scorer fragmentScorer = new QueryScorer(query);
		Highlighter highlighter = null ;
		if(ifCustom){
			highlighter = new CusHzqHighlighter(formatter, fragmentScorer);
		}else{
			highlighter = new Highlighter(formatter, fragmentScorer);
		}
		Fragmenter fragmenter = new SimpleFragmenter(fragmenterLength <= 0 ? 50 : fragmenterLength);
		highlighter.setTextFragmenter(fragmenter);
		return highlighter;
	}



}
