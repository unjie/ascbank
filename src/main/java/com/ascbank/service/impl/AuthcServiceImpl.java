package com.ascbank.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.exception.UserException;
import com.ascbank.model.User;
import com.ascbank.model.derive.Login;
import com.ascbank.service.AuthcService;
import com.ascbank.shiro.authc.LoginToken;

/**
 * @author jie
 *
 */

@Service("authcService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class AuthcServiceImpl implements AuthcService<Login> {
	
	private Logger log = LoggerFactory.getLogger(AuthcServiceImpl.class);
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.UserService#login(com.ascbank.model.User)
	 *
	 * 现为用户名登陆,手机与邮箱(Email) 登录暂未完成
	 */
	@Override
	@Transactional
	public User login(Login user) throws UserException {
		
		try {
			LoginToken<Login> upt = new LoginToken<Login>(user);
			Subject subject = SecurityUtils.getSubject();
			log.debug("--------------login----------------- user:   {}", user);
			subject.login(upt);
			log.debug("--------------login----------------- user:   {}", user);
			user = upt.getLogin();
			user.setPassword(null);
			log.debug("--------------login----------------- LoginToken:   {}", upt);
			upt.clear();
			return user;
		} catch (AuthenticationException e) {
			log.error("登录失败错误信息: {}", e);
			throw new UserException("{User.nameAndPassword.error}");
		}
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.UserService#logout() 用户退出
	 */
	@Override
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		
		if (subject != null) {
			String username = (String) subject.getPrincipal();
			log.debug("----logout {}-------------", username);
			subject.logout();
			return username;
		}
		return null;
	}
	
}
