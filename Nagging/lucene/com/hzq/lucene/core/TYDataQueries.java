package com.hzq.lucene.core;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.flexible.standard.QueryParserUtil;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.util.LuceneUtil;
import com.hzq.system.constant.Constant;

public class TYDataQueries {
	private static Directory TianYaderectory = null;
	private static DirectoryReader TianYareader = null;
	private static MultiReader TianYaMultireader = null;
	static {
		try {
			TianYaderectory = FSDirectory.open(Paths.get(Constant.Index_TianYaPost_Path));
			TianYareader = DirectoryReader.open(TianYaderectory);
			setMultiReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化多目录索引 
	 * @author huangzhiqian
	 * @date 2015年11月18日
	 */
	private static void setMultiReader(){
		try {
			
			IndexReader[] readers=new IndexReader[Constant.Index_TianYaPost_MultiPathNum];
			IndexReader reader=null;
			for(int i=0;i<Constant.Index_TianYaPost_MultiPathNum;i++){
				Directory dic=FSDirectory.open(Paths.get(Constant.Index_TianYaPost_MultiPath+(i+1)));
				reader=DirectoryReader.open(dic);
				readers[i]=reader;
			}
			TianYaMultireader=new MultiReader(readers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取天涯论坛Searcher(单目录)
	 * 
	 * @return
	 */
	private static IndexSearcher getTianYaSearcherOnePath() {
		try {
			if (TianYareader == null) {
				TianYareader = DirectoryReader.open(TianYaderectory);
			}
			return new IndexSearcher(TianYareader);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取IndexSearcher出错");
		}
	}
	
	/**
	 * 获取天涯论坛Searcher(多目录)
	 * 
	 * @return
	 */
	private static IndexSearcher getTianYaSearcherMultiPath() {
		try {
			if (TianYaMultireader == null) {
				setMultiReader();
			}
			return new IndexSearcher(TianYaMultireader);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取IndexSearcher出错");
		}
	}

	/**
	 * 天涯论坛的查询
	 * 
	 * @param condition
	 * @param type  1表示但目录的检索,2表示多目录检索
	 * @return
	 */
	public static Grid<TianYaPost> getDataGridResult(QueryCondition condition,String type) {
		Grid<TianYaPost> rs = new Grid<TianYaPost>();

		List<TianYaPost> rsList = new LinkedList<TianYaPost>();
		
		IndexSearcher searcher = null;
		if("1".equals(type)){
			searcher=getTianYaSearcherOnePath();
		}else if("2".equals(type)){
			//TODO 可以开启多个线程进行单目录查询 Union查询结果来再次提高查询效率  那排序怎么处理?
			searcher=getTianYaSearcherMultiPath();
		}else{
			return null;
		}
		Map<Object, Object> map = condition.getCondition();
		List<String> fieldList = new ArrayList<String>();
		List<String> queryList = new ArrayList<String>();
		String title = "";
		int FieldCount = 0;
		if (map.containsKey("content")) {
			fieldList.add("content");
			String content = QueryParserUtil.escape((String) map.get("content"));
			queryList.add(content.toLowerCase());

			FieldCount++;
		}
		if (map.containsKey("title")) {
			fieldList.add("title");
			title = QueryParserUtil.escape((String) map.get("title")).toLowerCase();
			queryList.add(title);
			FieldCount++;
		}
		if (FieldCount == 0) {
			// 如果查询条件没有模糊检索,提示输入
			rs.setO(new Json(false, "请输入检索条件!"));
			rs.setTotal(0);
			rs.setRows(rsList);
			return rs;
		}
		BooleanClause.Occur[] clauses = null;
		if (FieldCount == 1) {
			clauses = new BooleanClause.Occur[] { BooleanClause.Occur.MUST };
		}
		if (FieldCount == 2) {
			//查询时Title必须要包含,content可以不包含
			clauses = new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD, BooleanClause.Occur.MUST };
		}

		try {
			Long time1 = System.currentTimeMillis();
			//在索引的时候使用了同义词分词,在查询的时候就没必要用了
			Query query = MultiFieldQueryParser.parse(queryList.toArray(new String[queryList.size()]), fieldList.toArray(new String[fieldList.size()]), clauses, LuceneUtil.getIKAnalyzer());

			System.err.println(query.toString());
			// 排序
			Sort sort = null;
			if (map.containsKey("orderSql")) {
				String order = (String) map.get("orderSql");
				if (order.indexOf("add_time") != -1) {
					sort = new Sort(new SortField("addtime", SortField.Type.LONG, true));
				}
				if (order.indexOf("click") != -1) {
					sort = new Sort(new SortField("click", SortField.Type.LONG, true));
				}
				if (order.indexOf("reply") != -1) {
					sort = new Sort(new SortField("reply", SortField.Type.LONG, true));
				}
			}

			int page = Integer.parseInt((String) map.get("page"));
			int rows = (int) map.get("rows");
			ScoreDoc lastScore = getLastScoreDocs(page, rows, query, searcher, sort, rs);
			TopDocs tds;
			if (sort == null) {
				tds = searcher.searchAfter(lastScore, query, rows);
			} else {
				tds = searcher.searchAfter(lastScore, query, rows, sort);
			}
			rs.setO(new Json(true, System.currentTimeMillis() - time1 + ""));

			// 高亮
			Highlighter highlighter = LuceneUtil.createHighlighter(query, null, null, 50);
			ScoreDoc[] docs = tds.scoreDocs;
			
			for (ScoreDoc sd : docs) {
				Document doc = searcher.doc(sd.doc);
				TianYaPost post = parseDocToTianyaPost(doc, highlighter);
				if (post != null) {
					rsList.add(post);
				}
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}

		rs.setRows(rsList);

		return rs;
	}

	/**
	 * 获取SearchAfter的分页之前的一条数据(lucene分页查询)
	 * 
	 * @param page
	 *            第几页
	 * @param rows
	 *            每页的数据条数
	 * @param query
	 * @param searcher
	 * @param rs
	 *            这里直接可以查出数量,所以直接传入rs
	 * @return
	 * @throws IOException
	 */
	private static ScoreDoc getLastScoreDocs(int page, int rows, Query query, IndexSearcher searcher, Sort sort, Grid<TianYaPost> rs) throws IOException {
		if (page <= 1) {
			// 如果是第一页 只要查出Count就行
			int totalCount = searcher.search(query, 1).totalHits;
			rs.setTotal(totalCount);
			return null;
		}
		int num = (page - 1) * rows;
		TopDocs tds;
		if (sort != null) {
			tds = searcher.search(query, num, sort);
		} else {
			tds = searcher.search(query, num);
		}
		ScoreDoc[] sds = tds.scoreDocs;
		rs.setTotal(tds.totalHits);
		if (sds.length > 0) {
			return sds[sds.length - 1];
		} else {
			return null;
		}
	}

	/**
	 * 把doc转换成post对象
	 * 
	 * @param doc
	 * @param highlighter
	 *            用于高亮显示部分结果
	 * @return
	 */
	private static TianYaPost parseDocToTianyaPost(Document doc, Highlighter highlighter) {
		TianYaPost post = null;
		try {
			post = new TianYaPost();
			post.setId(Long.parseLong(doc.get("id")));

			if (StringUtils.isNotEmpty(doc.get("title"))) {
				// title的高亮处理
				TokenStream tokenStream = LuceneUtil.getIKSynonymAnalyzer().tokenStream("title", doc.get("title"));
				String result=highlighter.getBestFragment(tokenStream, doc.get("title"));
				if (StringUtils.isEmpty(result)) {
					result = doc.get("title");
				}
				post.setTitle(result);
			}
			if (StringUtils.isNotEmpty(doc.get("storedcontent"))) {
				TokenStream tokenStream = LuceneUtil.getIKSynonymAnalyzer().tokenStream("storedcontent", doc.get("storedcontent"));
				String result = highlighter.getBestFragment(tokenStream, doc.get("storedcontent"));
				if (StringUtils.isEmpty(result)) {
					result = doc.get("storedcontent");
				}
				post.setContent(result);

			}
			if (StringUtils.isNotEmpty(doc.get("url"))) {
				post.setUrl(doc.get("url"));
			}
			if (StringUtils.isNotEmpty(doc.get("adduser"))) {
				post.setAdduserId(doc.get("adduser"));
			}
			if (StringUtils.isNotEmpty(doc.get("addusername"))) {
				post.setAdduserName(doc.get("addusername"));
			}
			if (StringUtils.isNotEmpty(doc.get("addtime"))) {
				Date addTime = new Date(Long.parseLong(doc.get("addtime")));
				post.setAddTime(addTime);
			}
			if (StringUtils.isNotEmpty(doc.get("lastreplytime"))) {
				Date lastReplyTime = new Date(Long.parseLong(doc.get("lastreplytime")));
				post.setLastReplyTime(lastReplyTime);
			}
			post.setClick(Long.parseLong(doc.get("click")));
			post.setReply(Long.parseLong(doc.get("reply")));
			if (StringUtils.isNotEmpty(doc.get("isBest"))) {
				post.setIsBest(doc.get("isBest"));
			}
		} catch (NumberFormatException | IOException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
			return null;
		}
		return post;
	}

}
