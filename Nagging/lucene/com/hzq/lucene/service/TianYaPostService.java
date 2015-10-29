package com.hzq.lucene.service;

import java.util.List;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.QueryCondition;
import com.hzq.lucene.entity.TianYaPost;
import com.hzq.test.a.TaoBaoPost;

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
	/**
	 * 获取DataGrid数据
	 * @param condition
	 * @return
	 */
	Grid<TianYaPost> getDataGridResult(QueryCondition condition);
	void add(TaoBaoPost post);

}
