package com.hzq.lucene.util;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.hzq.system.constant.Constant;

public class LuceneUtil {
	private static Directory TianYaderectory = null;
	private static DirectoryReader TianYareader = null;
	
	static{
		try {
			TianYaderectory = FSDirectory.open(Paths.get(Constant.Index_TianYaPost_Path));
			TianYareader=DirectoryReader.open(TianYaderectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取天涯论坛Searcher
	 * @return
	 */
	public static IndexSearcher getTianYaSearcher(){
		try{
			if(TianYareader==null){
				TianYareader=DirectoryReader.open(TianYaderectory);
			}
			return new IndexSearcher(TianYareader);
		}catch(Exception e ){
			e.printStackTrace();
			throw new RuntimeException();
		}	
	}
	
	public static Analyzer getAnalyzer(){
		return new IKAnalyzer();
	}
	
	
}
