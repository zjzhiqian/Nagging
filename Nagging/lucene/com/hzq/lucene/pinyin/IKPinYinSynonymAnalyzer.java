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
package com.hzq.lucene.pinyin;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKTokenizer;

import com.hzq.lucene.constant.ConstantLucene;
import com.hzq.lucene.synonym.SynonymEngine;
import com.hzq.lucene.synonym.SynonymFilter;

/**
 * 
 * 扩展了IK的分词器 新添加同义词分词功能
 * 
 * @author huangzhiqian
 */
public class IKPinYinSynonymAnalyzer extends IKAnalyzer {
	private SynonymEngine engine;
	
	public IKPinYinSynonymAnalyzer(SynonymEngine engine) {
		this.engine = engine;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer _IKTokenizer = new IKTokenizer(this.useSmart());
		TokenStream tokenStream = new StopFilter(_IKTokenizer, ConstantLucene.STOP_WORDS_SET);
		tokenStream = new LowerCaseFilter(tokenStream);
		tokenStream = new SynonymFilter(tokenStream, engine);
		// 转拼音
		tokenStream = new PinyinTokenFilter(tokenStream, false, 2);
		// 对拼音进行NGram处理
		tokenStream = new PinyinNGramTokenFilter(tokenStream, 2, 20);
		return new TokenStreamComponents(_IKTokenizer, tokenStream);
	}

}
