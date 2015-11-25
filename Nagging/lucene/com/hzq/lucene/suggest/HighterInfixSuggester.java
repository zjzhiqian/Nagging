/**
 * @(#)CustomSuggester.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月23日 huangzhiqian 创建版本
 */
package com.hzq.lucene.suggest;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;

/**
 * 
 * InFixSuggester 覆盖Suggester的高亮部分
 * @author huangzhiqian
 */
public class HighterInfixSuggester extends AnalyzingInfixSuggester {

	public HighterInfixSuggester(Directory directory,Analyzer analyzer) throws IOException {
		super(directory,analyzer);
	}
	
	@Override
	protected void addWholeMatch(StringBuilder sb, String surface, String analyzed) {
		 sb.append("<font color=\"blue\">");
		 sb.append(surface);
		 sb.append("</font>");
	}
	
	@Override
    protected void addPrefixMatch(StringBuilder sb, String surface, String analyzed, String prefixToken) {
	    if (prefixToken.length() >= surface.length()) {
	      addWholeMatch(sb, surface, analyzed);
	      return;
	    }
	    sb.append("<font color=\"blue\">");
	    sb.append(surface.substring(0, prefixToken.length()));
	    sb.append("</font>");
	    sb.append(surface.substring(prefixToken.length()));
	  }
	
}	

