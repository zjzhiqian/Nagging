/**
 * @(#)MTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月9日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.CusHzqHighlighter;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import com.alibaba.druid.filter.config.ConfigTools;
import com.hzq.lucene.pinyin.IKPinYinSynonymAnalyzer;
import com.hzq.lucene.synonym.TxtSynonymEngine;
import com.hzq.lucene.util.AnalyzerUtil;


/**
 * 
 * 
 * @author huangzhiqian
 */
public class MTest {
	public static void main(String[] args) throws Exception {

		String text="教你六个方法拥有女主般的肌肤！";
		Analyzer analyzer= new IKPinYinSynonymAnalyzer(new TxtSynonymEngine());
		TokenStream tokenStream = analyzer.tokenStream("title",text);
		analyzer.close();
		String rs=AnalyzerUtil.displayAllTokenInfo(text,new IKPinYinSynonymAnalyzer(new TxtSynonymEngine()));
		
		System.out.println(rs);
		QueryParser parser=new QueryParser("1", new IKPinYinSynonymAnalyzer(new TxtSynonymEngine()));
		
		Query q = parser.parse("yongyou");
		System.out.println(q);
		CusHzqHighlighter highlighter = createHighlighter(q, null, null, 2000);
//		Highlighter highlighter = LuceneUtil.createHighlighter(q, null, null, 2000);
		
		String result=highlighter.getBestFragment(tokenStream, text);
		System.out.println(result);
		
		
		
		System.out.println(ConfigTools.encrypt("root"));
	}
	
	
	
	
	private static CusHzqHighlighter createHighlighter(Query query, String prefix, String suffix, int fragmenterLength) {
		Formatter formatter = new SimpleHTMLFormatter((prefix == null || prefix.trim().length() == 0) ? 
				"<font color=\"blue\">" : prefix, (suffix == null || suffix.trim().length() == 0)?"</font>" : suffix);
			Scorer fragmentScorer = new QueryScorer(query);
			CusHzqHighlighter highlighter = new CusHzqHighlighter(formatter, fragmentScorer);
			Fragmenter fragmenter = new SimpleFragmenter(fragmenterLength <= 0 ? 50 : fragmenterLength);
			highlighter.setTextFragmenter(fragmenter);
			return highlighter;
		}
}
