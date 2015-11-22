package com.hzq.lucene.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hzq.common.base.BaseService;
import com.hzq.common.entity.QueryCondition;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.service.TaoBaoPostService;

@Service
public class TaoBaoPostServiceImpl extends BaseService<TaoBaoPost> implements TaoBaoPostService {
	
	@Override
	public List<TaoBaoPost> findLimitedPost(int beginIndex,int rows) {
		QueryCondition con=new QueryCondition();
		con.getCondition().put("beginIndex", beginIndex);
		con.getCondition().put("rows", rows);
		return mapper.conditionQuery(con);
	}

	@Override
	public boolean updatePost(TaoBaoPost savePost) {
		return mapper.update(savePost)>0;
	}

	

}
