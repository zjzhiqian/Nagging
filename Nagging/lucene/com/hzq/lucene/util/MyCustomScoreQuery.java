package com.hzq.lucene.util;

import java.io.IOException;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.queries.function.FunctionQuery;
import org.apache.lucene.search.Query;


public class MyCustomScoreQuery extends CustomScoreQuery{
	
	public MyCustomScoreQuery(Query subQuery) {
		super(subQuery);
	}
	
	 public MyCustomScoreQuery(Query subQuery,FunctionQuery scoringQuery) {
		super(subQuery,scoringQuery);
	}
	@Override
	protected CustomScoreProvider getCustomScoreProvider(LeafReaderContext context) throws IOException {
		//默认 通过原有的评分*传入的评分域来确定最终评分
		return new MyCustomScoreProvider(context);
	}
	
}

class MyCustomScoreProvider extends CustomScoreProvider{

	public MyCustomScoreProvider(LeafReaderContext context) {
		super(context);
		
	}
	
	@Override
	public float customScore(int doc, float subQueryScore, float valSrcScore) throws IOException {
		//subQueryScore 默认文档打分
		// 评分域的打分
		//return subQueryScore * valSrcScore;默认两个相乘
		return super.customScore(doc, subQueryScore, valSrcScore);
	}
	
}
