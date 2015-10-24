package com.hzq.common.base;

import java.io.Serializable;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzq.common.entity.QueryCondition;
import com.hzq.system.entity.ShiroUser;

/**
 * @author hzq
 *
 * 2015年8月18日 下午10:42:37 
 */

public abstract class BaseService <T extends Serializable >{
	@Autowired
	protected BaseMapper<T> mapper;
	
	/**
	 * 获取登陆用户
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月4日
	 */
	public ShiroUser getShiroUser(){
		Subject subject=SecurityUtils.getSubject();
		ShiroUser shiroUser=(ShiroUser)subject.getPrincipal();		
		return shiroUser;
	}
	
	
	/**
	 * 更新
	 * @param t
	 * @return
	 */
	protected final boolean update(T t){
		return mapper.update(t)>0;
	}
	/**
	 * 新增
	 * @param t
	 * @return
	 */
	protected final boolean insert(T t){
		return mapper.insert(t)>0;
	}
	/**
	 * 根据主键id查找
	 * @param id
	 * @return
	 */
	protected final T findById(int id){
		return mapper.findById(id);
	}
	
	
	/**
	 * 查询出总数量
	 * @return
	 */
	protected final int findCount(){
		return mapper.findCount();
	}
	/**
	 * 根据主键删除数据
	 * @return
	 */
	protected final int deleteById(String id){
		return mapper.deleteById(id);
	}
	
	/**
	 * 根据条件查询数据
	 * @param queryCondition
	 * @return
	 */
	protected List<T> conditionQuery(QueryCondition queryCondition){
		return mapper.conditionQuery(queryCondition);
	}
	
	/**
	 * 根据条件查询出数量
	 * @param queryCondition
	 * @return
	 */
	protected int conditionCountQuery(QueryCondition queryCondition){
		return mapper.conditionCountQuery(queryCondition);
	}
	
	/**
	 * 条件删除
	 * @param queryCondition
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月11日
	 */
	protected boolean deleteCondition(QueryCondition queryCondition){
		return mapper.deleteCondition(queryCondition)>0;
	}
}
