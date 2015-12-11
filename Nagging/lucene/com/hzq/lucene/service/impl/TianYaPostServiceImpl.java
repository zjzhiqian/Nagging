package com.hzq.lucene.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzq.common.base.BaseService;
import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.lucene.dao.TianYaPostMapper;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.lucene.job.LuceneNRTJob;
import com.hzq.lucene.service.TianYaPostService;

@Service
public class TianYaPostServiceImpl extends BaseService<TianYaPost> implements TianYaPostService {
	@Autowired
	TianYaPostMapper tianYaPostMapper;
	
	@Override
	public void savePosts(List<TianYaPost> dataList) {
		for(TianYaPost post:dataList){
			insert(post);
		}
		
	}

	@Override
	public void savePost(TianYaPost post) {
		insert(post);
		
	}

	@Override
	public void deleteAllTianYaData() {
		tianYaPostMapper.deleteAllTianYaData();
		
	}

	@Override
	public List<TianYaPost> findAllPosts() {
		return tianYaPostMapper.conditionQuery(new QueryCondition());
	}

	@Override
	public Grid<TianYaPost> getDataGridResult(QueryCondition condition) {
		List<TianYaPost> gridResult=tianYaPostMapper.conditionQuery(condition);
		int count=tianYaPostMapper.conditionCountQuery(condition);
		return new Grid<TianYaPost>(gridResult,count);
	}

	@Override
	public void add(TaoBaoPost post) {
		tianYaPostMapper.add(post.getUrl(),post.getTitle(),post.getModual(),post.getNextpageurl());
	}

	
	@Override
	public Map<String, Object> importExcel(List<TianYaPost> posts) {
		Map<String, Object> map=new HashMap<String, Object>();
		for(TianYaPost post:posts){
			savePost(post);
		}
		map.put("flag", true);
		return map;
	}

	@Override
	
	
	public Json addTianYaPost(TianYaPost post) {
		if(insert(post)){
			LuceneNRTJob.posts.push(post);
			return new Json(true,"添加成功");
		}
		return new Json(false);
	}

	
}
