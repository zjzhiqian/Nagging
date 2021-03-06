package com.hzq.system.service;

import java.util.List;

import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.system.entity.ShiroUser;
import com.hzq.system.entity.SysUser;
import com.hzq.system.entity.SysUserRole;

/**
 * @author hzq
 *
 * 2015年8月31日 下午8:56:02 
 */
public interface SysUserService {

	/**根据用户名查找用户
	 * @param username
	 * @return
	 */
	SysUser findUserByUsername(String username);
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Json addUser(SysUser user);
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	boolean updateUser(SysUser user);
	/**
	 * 修改密码
	 * @param oldpsw
	 * @param newpsw
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月4日
	 */
	Json changPsw(String oldpsw, String newpsw);

	/**
	 * @return
	 */
	Grid<SysUser> getDataGridResult(QueryCondition condition);
	/**根据id删除用户
	 * @param string
	 * @return
	 */
	boolean deleteUser(String id);
	/**根据id查询用户
	 * @param id
	 * @return
	 */
	SysUser getUserById(int id);
	/**
	 * @param id
	 */
	List<SysUserRole>  getUserRolesByUserId(int id);
	/**修改用户角色
	 * @param id 用户编号
	 * @param ids 角色编号
	 * @return
	 */
	Json updateRole(int id, String ids);
	/**
	 * 启用或者禁用用户
	 * @param id
	 * @param state
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月24日
	 */
	Json changeState(int id, int state);
	/**
	 * 登陆成功后的操作
	 * @param id
	 * @param lastIp 更新ip
	 * @param date	更新最后登录时间,登录次数
	 * @author huangzhiqian
	 * @date 2015年9月29日
	 */
	void updateUserForLogin(Integer id, String lastIp);
	
	/**
	 * 登陆日志
	 * @param shirouser
	 * @param lastIp
	 * @author huangzhiqian
	 * @date 2016年3月1日
	 */
	void addLogInLog(ShiroUser shirouser, String lastIp);
	
}
