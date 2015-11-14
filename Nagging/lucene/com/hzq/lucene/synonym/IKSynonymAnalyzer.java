/**
 * @(#)IKSynonymAnalyzer.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月2日 huangzhiqian 创建版本
 */
package com.hzq.lucene.synonym;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.util.CharArraySet;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKTokenizer;



/**
 * 
 * 扩展了IK的分词器 新添加同义词分词功能
 * @author huangzhiqian
 */
public class IKSynonymAnalyzer extends IKAnalyzer{
	private SynonymEngine engine;
	/**停止词**/
	private static final CharArraySet STOP_WORDS_SET;
	public IKSynonymAnalyzer(SynonymEngine engine) {
		this.engine = engine;
	}
	
	static{
		 List<String> stopWords = Arrays.asList(new String[] {
				 "a", "an", "and", "are", "as", "at", "be", "but", "by",
			      "for", "if", "in", "into", "is", "it",
			      "no", "not", "of", "on", "or", "such",
			      "that", "the", "their", "then", "there", "these",
			      "they", "this", "to", "was", "will", "with"
		 });
		 CharArraySet stopSet = new CharArraySet(stopWords, false);
		 STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
	}
	
	
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer _IKTokenizer = new IKTokenizer(this.useSmart());
//		TokenStream tokenStream = new SynonymFilter(_IKTokenizer, engine);
//		tokenStream = new LowerCaseFilter(tokenStream);
//		tokenStream = new StopFilter(tokenStream,STOP_WORDS_SET);

		//首先移除停用词,这样的话,同义词考虑的项会少一点,但是这样停用词的同义词检索会查询不到
		//比如(are)有一个同义词(key) 查询key将查询不到带are的 
		TokenStream tokenStream = new StopFilter(_IKTokenizer, STOP_WORDS_SET);
		tokenStream = new LowerCaseFilter(tokenStream);
		tokenStream = new SynonymFilter(tokenStream,engine);
		return new TokenStreamComponents(_IKTokenizer,tokenStream);
	}
	
	
}

