/**
 * @(#)AnalyserUtil.java
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

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

/**
 * 
 * 显示分词情况，包括Term的起始位置和结束位置(偏移量),位置增量,Term字符串，Term字符串类型)
 * @author huangzhiqian
 */
public class AnalyserUtil {
	
	
	/**
	 * 显示分词详细信息
	 * @param analyzer
	 * @param text
	 * @author huangzhiqian
	 * @date 2015年11月2日
	 */
	public static void displayAllTokenInfo(String text,Analyzer analyzer){
		try {
			TokenStream stream=analyzer.tokenStream("tokens", new StringReader(text));
			 //位置增量 语汇单元的距离(0可以作同义词)
			PositionIncrementAttribute pia=stream.addAttribute(PositionIncrementAttribute.class);
			//语汇单元的位置偏移量
			OffsetAttribute oa=stream.addAttribute(OffsetAttribute.class); 
			//分词信息
			CharTermAttribute cta=stream.addAttribute(CharTermAttribute.class); 
			//使用分词器类型信息
			TypeAttribute ta=stream.addAttribute(TypeAttribute.class); 
			stream.reset();
			int position = 0;
			while (stream.incrementToken()) {
				int increment = pia.getPositionIncrement();
				if(increment > 0) {
					position = position + increment;
					System.out.print(position + ":");
				}
			    int startOffset = oa.startOffset();
			    int endOffset = oa.endOffset();
			    String term = cta.toString();
			    System.out.println("[" + term + "]" + ":(" + startOffset + "-->" + endOffset + "):" + ta.type());
			}
			System.err.println("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Analyzer analyzer=new IKSynonymAnalyzer(new BaseSynonymEngine());
		AnalyserUtil.displayAllTokenInfo("可以什么呢", analyzer);
		
	}
	
}

