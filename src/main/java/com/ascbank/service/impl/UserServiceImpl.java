package com.ascbank.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ascbank.dao.UserMapper;
import com.ascbank.dependency.injection.InjectionInterface;
import com.ascbank.exception.UserException;
import com.ascbank.model.Permission;
import com.ascbank.model.Role;
import com.ascbank.model.User;
import com.ascbank.service.BaseAbstractService;
import com.ascbank.service.UserService;
import com.ascbank.util.Digests;
import com.ascbank.util.Encodes;

@Service("userService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class UserServiceImpl extends BaseAbstractService<Long, User, UserMapper> implements UserService, InjectionInterface<UserMapper> {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 122684424253144556L;
	private final Logger		log					= LoggerFactory.getLogger(UserServiceImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.UserService_#login(com.ascbank.model.User)
	 */

	@Override
	public User add(User user) {
		final Role role = new Role(null, user.getUsername(), user.getUsername() + "_UserRole");
		user.getRoles().add(role);
		getBean().insertSelective(this.entryptPassword(user));

		final Permission perm = new Permission(null, user.getId().toString(), "User", "read,update",
				"[ " + user.getUsername() + " ] User Permission");
		role.getPermissions().add(perm);

		return user;

	}

	@Override
	public boolean canEvict(Cache userCache, Long id, String username) {
		final User cacheUser = userCache.get(id, User.class);
		if (cacheUser == null) {
			return false;
		}
		return !cacheUser.getUsername().equals(username);
	}

	@Override
	public User entryptPassword(User user) {
		byte[] salt = null;
		if (user.getEncrypt() == null || user.getEncrypt().isEmpty()) {
			salt = Digests.generateSalt(UserService.SALT_SIZE);
			user.setEncrypt(Encodes.encodeHex(salt));
		}
		salt = Encodes.decodeHex(user.getEncrypt());
		final byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, UserService.HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
		return user;
	}

	@Override
	public User login(User user) throws UserException {
		
		final UsernamePasswordToken upt = new UsernamePasswordToken(user.getUsername(), user.getPassword(), user.getSave());
		final Subject subject = SecurityUtils.getSubject();
		try {
			if (log.isDebugEnabled()) {
				log.debug("-------->>>>>" + user);
			}
			subject.login(upt);

		} catch (final AuthenticationException e) {
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
		final Subject subject = SecurityUtils.getSubject();

		if (subject != null) {
			
			final User user = this.read((String) subject.getPrincipal());
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
	public User update(User user) {
		if (StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(null);
		} else {
			user = this.entryptPassword(user);
		}
		return super.update(user);
	}

}
