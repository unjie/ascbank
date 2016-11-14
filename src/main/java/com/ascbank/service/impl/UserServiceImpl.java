package com.ascbank.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.ascbank.model.Permission;
import com.ascbank.model.User;
import com.ascbank.model.UserPermission;
import com.ascbank.service.UserService;
import com.ascbank.service.basis.BaseAbstractService;
import com.ascbank.util.Digests;
import com.ascbank.util.Encodes;

/**
 * @author jie
 *
 */
/**
 * @author jie
 *
 */
/**
 * @author jie
 *
 */
/**
 * @author jie
 *
 */
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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.basis.BaseAbstractService#add(com.ascbank.model.base.PKEntity)
	 *
	 * 添加用用户
	 */
	@Override
	@Transactional
	public User add(User user) {
		entryptPassword(user);

		// 插入 User
		// getBean().insertSelective(user);
		super.add(user);
		log.debug(" User  :  {}", user);
		// 创建Permission 对象
		Permission perm = new Permission(null, user.getId().toString(), "User", "read,update", "[ " + user.getUsername() + " ] User Permission(read,update) User table ID : " + user.getId());

		log.debug(" Permission  :  {}", perm);
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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.UserService#entryptPassword(com.ascbank.model.User)
	 *
	 * 用户密码加密,及盐的生成
	 */
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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.UserService#read(java.lang.String) 根据用户名获取用户信息
	 */
	@Override
	public User read(String username) {
		// TODO Auto-generated method stub
		return getBean().selectByUsername(username);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.basis.BaseAbstractService#update(com.ascbank.model.base.PKEntity)
	 *
	 * 更新用户信息
	 */
	@Override
	@Transactional
	public User update(User user) {
		if (StringUtils.isEmpty(user.getEncrypt()) && !StringUtils.isEmpty(user.getPassword())) {
			user = this.entryptPassword(user);
		}
		return super.update(user);
	}

}
