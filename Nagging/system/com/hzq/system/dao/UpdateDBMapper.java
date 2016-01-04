/**
 * @(#)UpdateDBMapper.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年1月4日 huangzhiqian 创建版本
 */
package com.hzq.system.dao;

import com.hzq.system.entity.UpdateDB;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface UpdateDBMapper {

	int isHasUpdateDB();

	void createUpdateDB();

	UpdateDB getUpdateDBByName(String fileName);

	void addUpdateDBLog(UpdateDB addUpdateDB);

}

