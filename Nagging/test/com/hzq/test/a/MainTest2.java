/**
 * @(#)MainTest2.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月22日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.util.LuceneUtil;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class MainTest2 {
	
	public static Grid<TianYaPost> getDataGridResult(QueryCondition condition) {
		Grid<TianYaPost> rs=new Grid<TianYaPost>();
		IndexSearcher searcher=LuceneUtil.getTianYaSearcher();
		Map<Object,Object> map=condition.getCondition();
		List<String> fieldList=new ArrayList<String>();
		List<String> queryList=new ArrayList<String>();
		int FieldCount=0;
		if(map.containsKey("content")){
			fieldList.add("content");
			queryList.add((String)map.get("content"));
			FieldCount++;
		}
		if(map.containsKey("title")){
			fieldList.add("title");
			queryList.add((String)map.get("title"));
			FieldCount++;
		}
		if(FieldCount==0){
			//如果查询条件没有模糊检索,提示输入
			rs.setO(new Json(false,"请输入检索条件!"));
			return rs;
		}
		BooleanClause.Occur[] clauses = null;
		if(FieldCount==1){
			clauses=new  BooleanClause.Occur[]{BooleanClause.Occur.SHOULD};  
		}
		if(FieldCount==2){
			clauses= new  BooleanClause.Occur[]{BooleanClause.Occur.MUST,BooleanClause.Occur.MUST};
		}
		
		try {
			Long time1=System.currentTimeMillis();
			Query query=MultiFieldQueryParser.parse(queryList.toArray(new String[queryList.size()]), fieldList.toArray(new String[fieldList.size()]), clauses, LuceneUtil.getAnalyzer());
//			query=new org.apache.lucene.queryparser.classic.QueryParser("title", LuceneUtil.getAnalyzer()).parse("婚纱");
			
			rs.setO(new Json(true,System.currentTimeMillis()-time1+""));
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
			TopDocs tds;
			if(sort!=null){
				tds=searcher.search(query, 30000,sort);
			}else{
				tds=searcher.search(query, 30000);
			}
			
			ScoreDoc[] docs=tds.scoreDocs;
			System.err.println(docs.length);
			for(ScoreDoc sd:docs){
				Document doc=searcher.doc(sd.doc);
				
				
				System.out.println(sd.doc+"..title:["+doc.get("click")+"]..[Email:["+doc.get("email")+"]..id:["+doc.get("id")+"]..Date:["+doc.get("date")+"]..attatch:["+doc.get("attach")+"]");
			}
		
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		
		
		
		return new Grid<TianYaPost>();
	}
	
	
	
	public static void main(String[] args) {
		QueryCondition condition=new QueryCondition();
		condition.getCondition().put("title", "婚纱");
		condition.getCondition().put("content", "婚纱");
		condition.getCondition().put("orderSql", "click");
		
		getDataGridResult(condition);
	}
}

