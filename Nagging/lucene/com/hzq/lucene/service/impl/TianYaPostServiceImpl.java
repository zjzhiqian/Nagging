package com.hzq.lucene.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzq.common.base.BaseService;
import com.hzq.lucene.dao.TianYaPostMapper;
import com.hzq.lucene.entity.TianYaPost;
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
	
}
