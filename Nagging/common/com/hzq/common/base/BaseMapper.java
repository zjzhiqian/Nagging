package com.hzq.common.base;
import java.io.Serializable;
import java.util.List;

import com.hzq.common.entity.QueryCondition;
/**
 * @author hzq
 *
 * 2015年8月18日 下午10:43:58 
 */
public interface BaseMapper< T extends Serializable> {
	
	/**
	 * 通用的更新Mapper
	 * @param t
	 * @return
	 */
	int update(T t);
	
	/**
	 * 通用的插入方法
	 * @param t
	 * @return
	 */
	int insert(T t);
	
	/**
	 * 通用的根据id查询数据方法
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月2日
	 */
	T findById(int id);
	
	/**查询出所有数据
	 * @return
	 */
	List<T> findAll();
	
	/**查询出总数量
	 * @return
	 */
	int findCount();
	
	/**根据主键Id删除数据
	 * @return
	 */
	int deleteById(String id);
	/**
	 * 根据条件查询
	 * @param easyQuery
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月8日
	 */
	List<T> conditionQuery(QueryCondition queryCondition);
	
	/**
	 * 根据条件查询数量
	 * @param queryCondition
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月8日
	 */
	int conditionCountQuery(QueryCondition queryCondition);
	/**
	 * 条件删除
	 * @param queryCondition
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月11日
	 */
	int deleteCondition(QueryCondition queryCondition);
}
