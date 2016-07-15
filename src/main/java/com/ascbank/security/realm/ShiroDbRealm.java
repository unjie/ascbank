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
	private final Logger	log	= LoggerFactory.getLogger(ShiroDbRealm.class);
	
	@Resource(name = "userService")
	private UserService		userService;
	
	// 获取认证信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo info = null;
		User user = null;
		final UsernamePasswordToken upt = (UsernamePasswordToken) token;
		// 通过表单接收的用户名
		final String username = upt.getUsername();
		try {
			user = userService.read(username);
			
		} catch (final RuntimeException e) {
			// TODO Auto-generated catch block
			throw new AuthenticationException("{User.name.not.exist}");
		}
		if (user != null) {
			info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(Encodes.decodeHex(user.getEncrypt())), getName());
		}
		
		log.debug("----->>>" + info);
		return info;
	}
	
	// 获取授权信息
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		final String username = (String) super.getAvailablePrincipal(principals);
		final User user = userService.read(username);
		
		final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		final Set<String> roles = new HashSet<String>();
		final Set<String> permissions = new HashSet<String>();
		for (final Role role : user.getRoles()) {
			roles.add(role.getRoleName());
			for (final Permission per : role.getPermissions()) {
				permissions.add(per.toPermissionString());
			}
		}
		info.addRoles(roles);
		info.addStringPermissions(permissions);
		return info;
	}
	
	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@SuppressWarnings("static-access")
	@PostConstruct()
	public void initCredentialsMatcher() {
		final HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
		matcher.setHashIterations(UserService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}