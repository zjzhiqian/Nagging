package com.hzq.lucene.service;

import java.util.List;

import com.hzq.lucene.entity.TianYaPost;

public interface TianYaPostService {
	/**
	 * 保存数据
	 * @param dataList
	 */
	void savePosts(List<TianYaPost> dataList);
	/**
	 * 添加一条数据
	 * @param post
	 */
	void savePost(TianYaPost post);
	/**
	 * 删除所有数据
	 */
	void deleteAllTianYaData();
	
	/**
	 * 查询所有数据
	 * @return
	 */
	List<TianYaPost> findAllPosts();

}
