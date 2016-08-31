package com.ascbank.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ascbank.dao.PermissionMapper;
import com.ascbank.dao.UserMapper;
import com.ascbank.dao.UserPermissionMapper;
import com.ascbank.dependency.injection.InjectionInterface;
import com.ascbank.exception.UserException;
import com.ascbank.model.Login;
import com.ascbank.model.Permission;
import com.ascbank.model.User;
import com.ascbank.model.UserPermission;
import com.ascbank.service.UserService;
import com.ascbank.service.basis.BaseAbstractService;
import com.ascbank.util.Digests;
import com.ascbank.util.Encodes;

@Service("userService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class UserServiceImpl extends BaseAbstractService<Long, User, UserMapper> implements UserService<Long, User>, InjectionInterface<UserMapper> {
	
	/**
	 *
	 */
	private final static long		serialVersionUID	= 122684424253144556L;
	private Logger					log					= LoggerFactory.getLogger(UserServiceImpl.class);
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.UserService_#login(com.ascbank.model.User)
	 */
	
	@Autowired(required = false)
	private PermissionMapper		permissionMap;
	
	@Autowired(required = false)
	private UserPermissionMapper	userPermissionMap;
	
	@Override
	@Transactional
	public User add(User user) {
		entryptPassword(user);
		
		// 插入 User
		getBean().insertSelective(user);
		
		// 创建Permission 对象
		Permission perm = new Permission(null, user.getId().toString(), "User", "read,update", "[ " + user.getUsername() + " ] User Permission");
		// 插入perm对象
		permissionMap.insertSelective(perm);
		
		// UserPermission 关联对象
		UserPermission up = new UserPermission();
		up.setUserId(user.getId());
		up.setPermissionId(perm.getId());
		// 插入关联
		userPermissionMap.insert(up);
		
		List<Permission> permis = user.getPermissions();
		if (permis == null) {
			permis = new ArrayList<Permission>();
		}
		permis.add(perm);
		user.setPermissions(permis);
		
		return user;
		
	}
	
	@Override
	public boolean canEvict(Cache userCache, Long id, String username) {
		User cacheUser = userCache.get(id, User.class);
		if (cacheUser == null) {
			return false;
		}
		return !cacheUser.getUsername().equals(username);
	}
	
	@Override
	public User entryptPassword(User user) {
		byte[] salt = null;
		if (StringUtils.isEmpty(user.getEncrypt())) {
			salt = Digests.generateSalt(UserService.SALT_SIZE);
			user.setEncrypt(Encodes.encodeHex(salt));
		}
		salt = Encodes.decodeHex(user.getEncrypt());
		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, UserService.HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
		return user;
	}
	
	@Override
	public User login(User user) throws UserException {
		
		UsernamePasswordToken upt = new UsernamePasswordToken(user.getUsername(), user.getPassword(), ((Login) user).getSave());
		Subject subject = SecurityUtils.getSubject();
		try {
			if (log.isDebugEnabled()) {
				log.debug("-------->>>>>" + user);
			}
			subject.login(upt);
			
		} catch (AuthenticationException e) {
			if (log.isInfoEnabled()) {
				log.error("登录失败错误信息:" + e);
			}
			upt.clear();
			throw new UserException("{User.nameAndPassword.error}");
		}
		
		return user;
		
	}
	
	@Override
	public User logout() {
		Subject subject = SecurityUtils.getSubject();
		
		if (subject != null) {
			
			User user = this.read((String) subject.getPrincipal());
			log.debug("----logout {}-------------", user);
			subject.logout();
			return user;
		}
		return null;
	}
	
	@Override
	public User read(String username) {
		// TODO Auto-generated method stub
		return getBean().selectByUsername(username);
	}
	
	@Override
	@Transactional
	public User update(User user) {
		if (StringUtils.isEmpty(user.getEncrypt()) && !StringUtils.isEmpty(user.getPassword())) {
			user = this.entryptPassword(user);
		}
		return super.update(user);
	}
	
}
