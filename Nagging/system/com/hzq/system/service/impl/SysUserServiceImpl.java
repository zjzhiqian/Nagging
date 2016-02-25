package com.hzq.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzq.common.base.BaseService;
import com.hzq.common.entity.Grid;
import com.hzq.common.entity.Json;
import com.hzq.common.entity.QueryCondition;
import com.hzq.common.util.SaltGenerator;
import com.hzq.system.constant.Constant;
import com.hzq.system.dao.SysUserMapper;
import com.hzq.system.dao.SysUserRoleMapper;
//import com.hzq.system.dao.SysUserRoleMapper;
import com.hzq.system.entity.ShiroUser;
import com.hzq.system.entity.SysUser;
import com.hzq.system.entity.SysUserRole;
import com.hzq.system.service.SysUserService;

/**
 * @author hzq
 *
 * 2015年8月31日 下午8:55:46 
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUser> implements SysUserService{
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	
	public SysUser findUserByUsername(String username) {
		return sysUserMapper.findUserByUsername(username);
	}

	@Override
	public Json addUser(SysUser user){
		Json json=new Json();
		//判断是否有用户名重复
		SysUser sysuser=findUserByUsername(user.getUsername().trim());
		if(sysuser!=null){
			json.setMsg("用户名重复");
			return json;
		}else{
			boolean flag=insert(user);	
			
			if("1".equals("1")){
				throw new RuntimeException();
			}
			json.setFlag(flag);
			if(flag){
				json.setMsg("添加用户["+user.getUsername()+"]成功");
			}else{
				json.setMsg("添加用户["+user.getUsername()+"]失败");
			}
			return json;
		}
		
	}
	
	public SysUser findUserById(int id){
		return findById(id);
	}
	
	public boolean updateUser(SysUser sysuser){
		return update(sysuser);
	}

	@Override
	public Json changPsw(String oldpsw, String newpsw) {
		ShiroUser user=getShiroUser();
		SysUser sysuser=findById(user.getId());
		//密码校验
		SimpleHash hash = new SimpleHash(Constant.ENCRYPT_TYPE, oldpsw, sysuser.getSalt(), Constant.ENCRYPT_TIMES);
		String psw=hash.toHex();
		//如果通过进行密码修改
		if(StringUtils.equalsIgnoreCase(psw, sysuser.getPassword())){
			SysUser updateUser=new SysUser();
			String updateSalt= SaltGenerator.getSalt(Constant.SALT_LENGTH_HALF);
			SimpleHash hash2 = new SimpleHash(Constant.ENCRYPT_TYPE, newpsw,updateSalt,Constant.ENCRYPT_TIMES);
			String updatepsw=hash2.toHex();
			updateUser.setId(user.getId());
			updateUser.setSalt(updateSalt);
			updateUser.setPassword(updatepsw);
			updateUser.setModifytime(new Date());
			updateUser.setModifyuserid(user.getId()+"");
			if(sysUserMapper.update(updateUser)>0){
				return new Json(true,"修改成功");
			}else{
				return new Json(false);
			}
		}else{
			return new Json(false,"密码错误 ");
		}
	}

	
	@Override
	public Grid<SysUser> getDataGridResult(QueryCondition condition) {
		List<SysUser> sysusers=conditionQuery(condition);
		
		int count=conditionCountQuery(condition);
		return new Grid<SysUser>(sysusers,count);
	}


	@Override
	public boolean deleteUser(String id) {
		QueryCondition con=new QueryCondition();
		con.getCondition().put("userid", id);
		if(sysUserRoleMapper.conditionCountQuery(con)>0){
			if(sysUserRoleMapper.deleteByUserId(id)>0){
				return deleteById(id)>0;
			}else {
				return false;
			}
		}else{
			return deleteById(id)>0;
		}
			
	}

	/* (non-Javadoc)
	 * @see com.hzq.system.service.SysUserService#getUserById(int)
	 */
	@Override
	public SysUser getUserById(int id) {
		QueryCondition con=new QueryCondition();
		con.getCondition().put("id", id);
		List<SysUser> users=sysUserMapper.conditionQuery(con);
		if(users!=null&&users.size()>0){
			return users.get(0);
		}
		return null;
	}

	@Override
	public List<SysUserRole> getUserRolesByUserId(int id) {
		return sysUserMapper.getRolesByUserId(id);
	}

	@Override
	public Json updateRole(int id, String ids) {
		ShiroUser user=getShiroUser();
		
		
		if(StringUtils.isNotEmpty(id+"")){
			 //最后修改人,需改时间
			SysUser sysu=new SysUser();
			sysu.setId(id);
			sysu.setModifyuserid(user.getId()+"");
			sysu.setModifytime(new Date());
			updateUser(sysu);
			
			if(sysUserMapper.deleteUserRoleByUserId(id)>=0){
				boolean flag=true;String msg="授权成功";
				if(StringUtils.isNotEmpty(ids)){
					String[] idArray=ids.split(",");
					for(int i=0;i<idArray.length;i++){
						flag=sysUserMapper.insertUserRole(id,idArray[i],user.getId())>0;
						if(flag==false){
							msg="授权出错";
							break;
						}
					}
				}
				return new Json(flag,msg);
			}else{
				return new Json(false);
			}
		}else{
			return new Json("用户编号为空");
		}
	}

	@Override
	public Json changeState(int id, int state) {
		ShiroUser u=getShiroUser();
		SysUser user=new SysUser();
		user.setId(id);
		user.setState(state+"");
		user.setModifytime(new Date());
		user.setModifyuserid(u.getId()+"");
		if(sysUserMapper.update(user)>0){
			return new Json(true,"修改用户状态成功");
		}else{
			return new Json(false);
		}
	}

	@Override
	public void updateUserForLogin(Integer id, String lastIp) {
		sysUserMapper.doAfterLogIn(id,lastIp);
		
	}
}
