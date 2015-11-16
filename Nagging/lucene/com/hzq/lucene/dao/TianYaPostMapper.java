/*
 * TianYaPostMapper.java
 * Copyright (huangzhiqian)
 * All rights reserved.
 * -----------------------------------------------
 * 2015-10-22 Created
 */
package com.hzq.lucene.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hzq.common.base.BaseMapper;
import com.hzq.lucene.entity.TaoBaoPost;
import com.hzq.lucene.entity.TianYaPost;

public interface TianYaPostMapper extends BaseMapper<TianYaPost> {
	/**
	 * 删除天涯帖子表格所有数据
	 */
	void deleteAllTianYaData();
	/**
	 * 抓取时的部分数据
	 * @param url
	 * @param title
	 * @param modual
	 * @param nextPage
	 */
	void add(@Param("url")String url, @Param("title")String title,@Param("modual")String modual,@Param("nextPage")String nextPage);
	/**
	 * 获取所有淘宝帖子
	 * @return
	 */
	List<TaoBaoPost> findAllTbPosts();
	

}