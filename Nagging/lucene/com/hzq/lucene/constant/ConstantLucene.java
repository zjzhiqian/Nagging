package com.hzq.lucene.constant;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.util.CharArraySet;

import com.hzq.lucene.util.LuceneUtil;

/**
 * Lucene包的一些常量
 * @author huangzhiqian
 *
 */
public class ConstantLucene {
	
	//********************索引相关********************//
//	private static final String PathPreFix = getIndexPath() ;
	private static final String PathPreFix = "c:\\luceneIndex\\" ;
	
	
	/**
	 * TianYa帖子索引储存路径
	 */
	public static final String Index_TianYaPost_Path= PathPreFix+"TianYa";
	
	/**
	 * TianYa帖子索引多目录的数量
	 */
	public static final int Index_TianYaPost_MultiPathNum=5;
	
	/**
	 * TianYa帖子索引储存多目录路径
	 */
    public static final String Index_TianYaPost_MultiPath= PathPreFix+"TianYaMulty"+File.separator+"index";
	
    /**
     * TianYa检索 提示内容索引存储路径
     */
    public static final String Index_TianYaSuggest_Path= PathPreFix+"TianYaSuggest";
    
    /**
     * 淘宝帖子索引储存路径
     */
    public static final String Index_TaoBaoPost_Path= PathPreFix+"TaoBao";
    
    /**
     * TaoBao帖子索引储存多目录路径
     */
    public static final String Index_TaoBaoPost_MultiPath= PathPreFix+"TaoBaoMulty"+File.separator+"index";
    
    /**
     * TaoBao帖子索引多目录的数量
     */
    public static final int Index_TaoBaoPost_MultiPathNum=5;
    
    /**
     * TaoBao检索 提示内容索引存储路径
     */
    public static final String Index_TaoBaoSuggest_Path= PathPreFix+"TaoBaoSuggest";
    
	
	
	/**
	 * 停止词集合
	 */
	public static final CharArraySet STOP_WORDS_SET;
	static {
		List<String> stopWords = Arrays.asList(new String[] { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "such",
				"that", "the", "their", "then", "there", "these", "they", "this", "to", "was", "will", "with" });
		CharArraySet stopSet = new CharArraySet(stopWords, false);
		STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
	}
	
	
	private static String getIndexPath(){
		String osName = System.getProperty("os.name");
		if(StringUtils.equalsIgnoreCase(osName, "linux")){
			return File.separator+"netdata"+File.separator+"index"+File.separator;
		}
		String path = LuceneUtil.class.getClassLoader().getResource("").getPath().replaceAll("%20", " ");
		int pos = -1;
		if (path.indexOf("/") != -1) {
			pos = path.indexOf("/WEB-INF/");
		} else if (path.indexOf("\\") != -1) {
			pos = path.indexOf("\\WEB-INF\\");
		}
		if (pos != -1) {
			path = path.substring(0, pos);
			if (path.indexOf("/") != -1) {
				path = path + "/Index/";
			} else if (path.indexOf("\\") != -1) {
				path = path + "\\Index\\";
			}
		}
		if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
			path = path.replaceFirst("/", "");
		}
	    return path;
	}
	
}
