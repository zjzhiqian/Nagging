/*
 * TianYaPostMapper.java
 * Copyright (huangzhiqian)
 * All rights reserved.
 * -----------------------------------------------
 * 2015-10-22 Created
 */
package com.hzq.lucene.dao;

import org.apache.ibatis.annotations.Param;

import com.hzq.common.base.BaseMapper;
import com.hzq.lucene.entity.TianYaPost;


public interface TianYaPostMapper extends BaseMapper<TianYaPost> {
	/**
	 * 删除天涯帖子表格所有数据
	 */
	void deleteAllTianYaData();
	
	void add(@Param("url")String url, @Param("title")String title,@Param("modual")String modual,@Param("nextPage")String nextPage);
	


}