/*
 * TianYaPostMapper.java
 * Copyright (huangzhiqian)
 * All rights reserved.
 * -----------------------------------------------
 * 2015-10-22 Created
 */
package com.hzq.lucene.dao;

import com.hzq.common.base.BaseMapper;
import com.hzq.lucene.entity.TianYaPost;


public interface TianYaPostMapper extends BaseMapper<TianYaPost> {
	/**
	 * 删除天涯帖子表格所有数据
	 */
	void deleteAllTianYaData();
	



}