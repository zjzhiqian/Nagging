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

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKTokenizer;



/**
 * 
 * 扩展了IK的分词器 新添加同义词分词功能
 * @author huangzhiqian
 */
public class IKSynonymAnalyzer extends IKAnalyzer{
	private SynonymEngine engine;

	public IKSynonymAnalyzer(SynonymEngine engine) {
		this.engine = engine;
	}
	
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer _IKTokenizer = new IKTokenizer(this.useSmart());
		TokenStream tokenStream = new SynonymFilter(_IKTokenizer, engine);
		tokenStream = new LowerCaseFilter(tokenStream);
		tokenStream = new StopFilter(tokenStream,StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		return new TokenStreamComponents(_IKTokenizer,tokenStream);
	}
	
	
}

