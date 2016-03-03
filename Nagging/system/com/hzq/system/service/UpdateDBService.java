/**
 * @(#)UpdateDBService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年1月4日 huangzhiqian 创建版本
 */
package com.hzq.system.service;
/**
 * 
 * 
 * @author huangzhiqian
 */
public interface UpdateDBService {
	/**
	 * 数据库更新
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年3月3日
	 */
	boolean updateDB()throws Exception;
	/**
	 * 重置数据库
	 * @return
	 * @author huangzhiqian
	 * @date 2016年3月3日
	 */
	void restDB()throws Exception;
}

