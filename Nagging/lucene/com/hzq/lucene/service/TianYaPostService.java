package com.hzq.lucene.service;

import java.util.List;
import java.util.Map;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.lucene.entity.TaoBaoPost;
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
	/**
	 * 获取DataGrid数据
	 * @param condition
	 * @return
	 */
	Grid<TianYaPost> getDataGridResult(QueryCondition condition);
	/**
	 * 添加淘宝数据
	 * @param post
	 */
	void add(TaoBaoPost post);
	
	
	/**
	 * Excel导入
	 * @param posts
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	Map<String,Object> importExcel(List<TianYaPost> posts);
	/**
	 * 添加淘宝数据
	 * @param post
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月11日
	 */
	Json addTianYaPost(TianYaPost post);
}
