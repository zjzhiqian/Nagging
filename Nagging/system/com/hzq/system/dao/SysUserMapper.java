package com.hzq.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hzq.common.base.BaseMapper;
import com.hzq.system.entity.SysUser;
import com.hzq.system.entity.SysUserRole;


public interface SysUserMapper extends BaseMapper<SysUser>{

	/**根据用户名查找用户
	 * @param username
	 * @return
	 */
	SysUser findUserByUsername(String username);

	/**根据用户id查询关联表
	 * @param id
	 * @return
	 */
	List<SysUserRole> getRolesByUserId(int id);

	/**根据用户id删除用户角色关联表信息
	 * @param id
	 * @return
	 */
	int deleteUserRoleByUserId(@Param("id") int id);

	/**添加用户角色关联表信息
	 * @param id	用户id
	 * @param string  角色id
	 * @param id2	添加人id
	 * @return
	 */
	int insertUserRole(@Param("userid")int id, @Param("roleid")String string,@Param("adduserid") Integer id2);

	
	
}