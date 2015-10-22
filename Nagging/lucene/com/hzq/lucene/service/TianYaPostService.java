package com.hzq.lucene.service;

import java.util.List;

import com.hzq.lucene.entity.TianYaPost;

public interface TianYaPostService {
	/**
	 * 保存数据
	 * @param dataList
	 */
	void savePosts(List<TianYaPost> dataList);

}
