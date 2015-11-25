package com.hzq.lucene.constant;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.util.CharArraySet;

/**
 * Lucene包的一些常量
 * @author huangzhiqian
 *
 */
public class ConstantLucene {
	
	//********************索引相关********************//
	
	/**
	 * TianYa帖子索引储存路径
	 */
	public static final String Index_TianYaPost_Path="C:\\luceneIndex\\TianYa";
	
	/**
	 * TianYa帖子索引多目录的数量
	 */
	public static final int Index_TianYaPost_MultiPathNum=5;
	
	/**
	 * TianYa帖子索引储存多目录路径
	 */
    public static final String Index_TianYaPost_MultiPath="C:\\luceneIndex\\TianYaMulti\\index";
	
    /**
     * TianYa检索 提示内容索引存储路径
     */
    public static final String Index_TianYaSuggest_Path="C:\\luceneIndex\\TianYaSuggest";
    
    /**
     * 淘宝帖子索引储存路径
     */
    public static final String Index_TaoBaoPost_Path="C:\\luceneIndex\\TaoBao";
    
    /**
     * TaoBao帖子索引储存多目录路径
     */
    public static final String Index_TaoBaoPost_MultiPath="C:\\luceneIndex\\TaoBaoMulty\\index";
    
    /**
     * TaoBao帖子索引多目录的数量
     */
    public static final int Index_TaoBaoPost_MultiPathNum=5;
    
    /**
     * TaoBao检索 提示内容索引存储路径
     */
    public static final String Index_TaoBaoSuggest_Path="C:\\luceneIndex\\TaoBaoSuggest";
    
	
	
	
	public static final CharArraySet STOP_WORDS_SET;
	static {
		List<String> stopWords = Arrays.asList(new String[] { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "such",
				"that", "the", "their", "then", "there", "these", "they", "this", "to", "was", "will", "with" });
		CharArraySet stopSet = new CharArraySet(stopWords, false);
		STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
	}
	
	
	
}
