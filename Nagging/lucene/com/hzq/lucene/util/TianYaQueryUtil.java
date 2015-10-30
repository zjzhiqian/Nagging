package com.hzq.lucene.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.system.constant.Constant;


public class TianYaQueryUtil {
	private static Directory TianYaderectory = null;
	private static DirectoryReader TianYareader = null;
	
	static{
		try {
			TianYaderectory = FSDirectory.open(Paths.get(Constant.Index_TianYaPost_Path));
			TianYareader=DirectoryReader.open(TianYaderectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取天涯论坛Searcher
	 * @return
	 */
	private static IndexSearcher getTianYaSearcher(){
		try{
			if(TianYareader==null){
				TianYareader=DirectoryReader.open(TianYaderectory);
			}
			return new IndexSearcher(TianYareader);
		}catch(Exception e ){
			e.printStackTrace();
			throw new RuntimeException();
		}	
	}
	
	/**
	 * 天涯论坛的查询
	 * @param condition
	 * @return
	 */
	public static Grid<TianYaPost> getDataGridResult(QueryCondition condition) {
		Grid<TianYaPost> rs=new Grid<TianYaPost>();
		
		List<TianYaPost> rsList=new LinkedList<TianYaPost>();
		IndexSearcher searcher=getTianYaSearcher();
		Map<Object,Object> map=condition.getCondition();
		List<String> fieldList=new ArrayList<String>();
		List<String> queryList=new ArrayList<String>();
		int FieldCount=0;
		if(map.containsKey("content")){
			fieldList.add("content");
			String content=(String)map.get("content");
			queryList.add(content.toLowerCase());
			FieldCount++;
		}
		if(map.containsKey("title")){
			fieldList.add("title");
			String title=(String)map.get("title");
			queryList.add(title.toLowerCase());
			FieldCount++;
		}
		if(FieldCount==0){
			//如果查询条件没有模糊检索,提示输入
			rs.setO(new Json(false,"请输入检索条件!"));
			rs.setTotal(0);
			rs.setRows(rsList);
			return rs;
		}
		BooleanClause.Occur[] clauses = null;
		if(FieldCount==1){
			clauses=new  BooleanClause.Occur[]{BooleanClause.Occur.MUST};  
		}
		if(FieldCount==2){
			clauses= new  BooleanClause.Occur[]{BooleanClause.Occur.MUST,BooleanClause.Occur.MUST};
		}
		
		try {
			Long time1=System.currentTimeMillis();
			Query query=MultiFieldQueryParser.parse(queryList.toArray(new String[queryList.size()]), fieldList.toArray(new String[fieldList.size()]), clauses,LuceneUtil.getAnalyzer());
			//排序
			Sort sort=null;
			if(map.containsKey("orderSql")){
				String order=(String)map.get("orderSql");
				if(order.indexOf("add_time")!=-1){
					sort=new Sort(new SortField("addtime", SortField.Type.LONG,true));
				}
				if(order.indexOf("click")!=-1){
					sort=new Sort(new SortField("click", SortField.Type.LONG,true));
				}
				if(order.indexOf("reply")!=-1){
					sort=new Sort(new SortField("reply", SortField.Type.LONG,true));
				}
			}
			
			int page=Integer.parseInt((String)map.get("page"));
			int rows=(int) map.get("rows");
			System.err.println(System.currentTimeMillis()-time1);
			ScoreDoc lastScore=getLastScoreDocs(page,rows,query,searcher,sort,rs);
			TopDocs tds;
			if(sort==null){
				tds=searcher.searchAfter(lastScore, query, rows);
			}else{
				tds=searcher.searchAfter(lastScore, query, rows, sort);
			}
			rs.setO(new Json(true,System.currentTimeMillis()-time1+""));
			
			ScoreDoc[] docs=tds.scoreDocs;
			for(ScoreDoc sd:docs){
				Document doc=searcher.doc(sd.doc);
				TianYaPost post=parseDocToTianyaPost(doc);
				rsList.add(post);
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		
		rs.setRows(rsList);
		
		return rs;
	}
	
	/**
	 * 获取SearchAfter的分页之前的一条数据(lucene分页查询)
	 * @param page 第几页
	 * @param rows 每页的数据条数
	 * @param query 
	 * @param searcher
	 * @param rs 这里直接可以查出数量,所以直接传入rs
	 * @return
	 * @throws IOException
	 */
	private static ScoreDoc getLastScoreDocs(int page, int rows ,Query query,IndexSearcher searcher,Sort sort,Grid<TianYaPost> rs) throws IOException{
		if(page<=1){
			//如果是第一页 只要查出Count就行
			int totalCount=searcher.search(query, 1).totalHits;
			rs.setTotal(totalCount);
			return null;
			
		}
		int num=(page-1)*rows;
		TopDocs tds;
		if(sort!=null){
			tds=searcher.search(query, num,sort);
		}else{
			tds=searcher.search(query, num);
		}
		ScoreDoc[] sds=tds.scoreDocs;
		rs.setTotal(tds.totalHits);
		if(sds.length>0){
			return sds[sds.length-1];			
		}else{
			return null;
		}
	}
	
	
	
	
	
	
	
	/**
	 * 把doc转换成post对象
	 * @param doc
	 * @param sdf
	 * @return
	 */
	private static TianYaPost parseDocToTianyaPost(Document doc) {
		TianYaPost post=new TianYaPost();
		post.setId(Long.parseLong(doc.get("id")));
		if(StringUtils.isNotEmpty(doc.get("title"))){
			post.setTitle(doc.get("title"));
		}	
		if(StringUtils.isNotEmpty(doc.get("url"))){
			post.setUrl(doc.get("url"));
		}
		if(StringUtils.isNotEmpty(doc.get("adduser"))){
			post.setAdduserId(doc.get("adduser"));
		}
		if(StringUtils.isNotEmpty(doc.get("addusername"))){
			post.setAdduserName(doc.get("addusername"));
		}
		if(StringUtils.isNotEmpty(doc.get("addtime"))){
			Date addTime = new Date(Long.parseLong(doc.get("addtime")));
			post.setAddTime(addTime);
		}
		if(StringUtils.isNotEmpty(doc.get("lastreplytime"))){
			Date lastReplyTime = new Date(Long.parseLong(doc.get("lastreplytime")));
			post.setLastReplyTime(lastReplyTime);
		}	
		post.setClick(Long.parseLong(doc.get("click")));
		post.setReply(Long.parseLong(doc.get("reply")));
		if(StringUtils.isNotEmpty(doc.get("isBest"))){
			post.setIsBest(doc.get("isBest"));
		}
		return post;
	}
	
	
}
