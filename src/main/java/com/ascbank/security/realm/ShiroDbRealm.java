package com.ascbank.security.realm;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.ascbank.model.Permission;
import com.ascbank.model.Role;
import com.ascbank.model.User;
import com.ascbank.service.UserService;
import com.ascbank.util.Encodes;

@DependsOn(value = "userService")
@Component("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm {
	private Logger		log	= LoggerFactory.getLogger(ShiroDbRealm.class);
	
	@Resource(name = "userService")
	private UserService	userService;
	
	// 获取认证信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo info = null;
		User user = null;
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		// 通过表单接收的用户名
		String username = upt.getUsername();
		
		user = userService.read(username);
		log.debug(".................................doGetAuthenticationInfo  user= {}.......................................", user);
		
		if (user != null) {
			info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(Encodes.decodeHex(user.getEncrypt())), getName());
		} else {
			throw new AuthenticationException("{User.name.not.exist}");
		}
		
		log.debug("----->>>" + info);
		return info;
	}
	
	// 获取授权信息
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) super.getAvailablePrincipal(principals);
		User user = userService.read(username);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roles = new HashSet<String>();
		Set<String> permissions = new HashSet<String>();

		for (Permission per : user.getPermissions()) {
			permissions.add(per.toPermissionString());
		}

		for (Role role : user.getRoles()) {
			roles.add(role.getRoleName());
			for (Permission per : role.getPermissions()) {
				permissions.add(per.toPermissionString());
			}
		}
		
		info.addRoles(roles); // 添加权限到用户信息
		info.addStringPermissions(permissions);// 添加权限到用户信息
		return info;
	}
	
	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct()// class初始化时执行
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
		matcher.setHashIterations(UserService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}