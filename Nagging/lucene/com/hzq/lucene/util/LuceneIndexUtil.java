package com.hzq.lucene.util;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;
/**
 * Lucene生成索引的工具类,生成时默认分词器为IKAnalyser
 * @author huangzhiqian
 * @date 2015年10月24日 下午5:57:17
 */
public class LuceneIndexUtil {
	
	/**ID Field (保存内容,不分词,忽略权重)**/
	public static FieldType IdFielType =null;
	/**Content Field (不保存内容,分词,保留权重,偏移量等)**/
	public static FieldType ContentFielType =null;
	/**Title Field(保存内容,分词,保留权重,偏移量等)**/
	public static FieldType TitleFielType =null;
	
	/**
	 * 默认分词器
	 */
	private static Analyzer defaultAnalyzer = null;
	static {
		defaultAnalyzer=new IKAnalyzer();
		
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
	
}
