package com.hzq.lucene.service;

import java.util.List;

import com.hzq.lucene.entity.TaoBaoPost;

public interface TaoBaoPostService {
	/**
	 * 查询所有帖子
	 * @return
	 */
	List<TaoBaoPost> findLimitedPost(int beginIndex,int rows);
	/**
	 * 更新帖子数据
	 * @param savePost
	 * @return
	 */
	boolean updatePost(TaoBaoPost savePost);

}
