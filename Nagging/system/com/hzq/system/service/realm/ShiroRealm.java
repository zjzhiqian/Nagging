
package com.hzq.system.service.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzq.system.entity.ShiroUser;
import com.hzq.system.entity.SysPermission;
import com.hzq.system.entity.SysRole;
import com.hzq.system.entity.SysUser;
import com.hzq.system.service.PermissionService;
import com.hzq.system.service.SysRoleService;
import com.hzq.system.service.SysUserService;
import com.hzq.system.util.PermissionUtil;
@Service
public class ShiroRealm extends AuthorizingRealm {
	@Autowired
	private SysUserService userService;
	@Autowired
	private SysRoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public void setName(String name) {
		super.setName("shiroRealm");
	}
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		// token是用户输入的用户名和密码 
		// 从token中取出用户名
		String username = (String) authcToken.getPrincipal();
		SysUser sysUser=userService.findUserByUsername(username);
		if(sysUser==null){
			return null;
		}
		if("0".equals(sysUser.getState())){
			throw new LockedAccountException();
		}
		ShiroUser shiroUser=new ShiroUser(sysUser.getId(),sysUser.getUsername(), sysUser.getName(),sysUser.getPhone());
		List<SysRole> roles=roleService.getRolesByUserId(sysUser.getId());
		shiroUser.setRoles(roles);
		
		List<SysPermission> permissions=new ArrayList<SysPermission>();
		if(roles.size()!=0){
			permissions=permissionService.getPermissionBySysRoles(roles);
		}
		shiroUser.setMenus(PermissionUtil.getMenus(permissions));
		shiroUser.setPermissions(PermissionUtil.getPermissions(permissions));
		String salt=sysUser.getSalt();
		return new SimpleAuthenticationInfo(shiroUser,sysUser.getPassword(),ByteSource.Util.bytes(salt),getName());
		
	}

	/**
	 * 授权查询回调函数, 进行授权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		for (SysRole role : shiroUser.getRoles()) {
//			 //基于Role的权限信息(与代码绑定太多)
//			 info.addRole(role.getAuthorityname());
//		}
		for (SysPermission permission : shiroUser.getPermissions()) {
			 //基于Permission的权限信息
			String auth=permission.getAuth();
			if(auth!=null){
				 info.addStringPermission(auth.trim());
			}
		}
		return info;
	};

	
	
	/**
	 * 清除缓存,权限修改时需要立即生效可用
	 */
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

}


