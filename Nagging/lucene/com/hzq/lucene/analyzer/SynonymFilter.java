package com.hzq.lucene.analyzer;

import java.io.IOException;
import java.util.Stack;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeSource;

/**
 * 自定义同义词过滤器,实现同义词分词功能
 * @author huangzhiqian
 *
 */
public class SynonymFilter extends TokenFilter {

	private Stack<String> synonymStack;
	private SynonymEngine engine;
	private AttributeSource.State current;

	private final CharTermAttribute termAtt;
	private final PositionIncrementAttribute posIncrAtt;

	public SynonymFilter(TokenStream in, SynonymEngine engine) {
		super(in);
		synonymStack = new Stack<String>(); //定义同义词的Buffer
		this.engine = engine;

		this.termAtt = addAttribute(CharTermAttribute.class);
		this.posIncrAtt = addAttribute(PositionIncrementAttribute.class);
	}

	public boolean incrementToken() throws IOException {
		if (synonymStack.size() > 0) { 
			String syn = synonymStack.pop();
			restoreState(current);
			termAtt.copyBuffer(syn.toCharArray(), 0, syn.length());
			posIncrAtt.setPositionIncrement(0);
			return true;
		}

		if (!input.incrementToken())
			return false;

		if (addAliasesToStack()) {
			//如果找到了同义词,偏移量不变
			current = captureState(); 
		}

		return true;
	}
	
	/**
	 * 尝试查找同义词,有则返回true,没有找到则返回false
	 * @return
	 * @throws IOException
	 * @author huangzhiqian
	 * @date 2015年11月2日
	 */
	private boolean addAliasesToStack(){
		String[] synonyms = engine.getSynonyms(termAtt.toString());
		if (synonyms == null) {
			return false;
		}
		for (String synonym : synonyms) { 
			synonymStack.push(synonym);
		}
		return true;
	}
}

