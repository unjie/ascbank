/**
 *
 */
package com.ascbank.web.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ascbank.exception.UserException;
import com.ascbank.model.Login;
import com.ascbank.model.Register;
import com.ascbank.model.User;
import com.ascbank.service.UserService;
import com.ascbank.verify.AddCheck;
import com.ascbank.verify.CaptchaCheck;
import com.ascbank.verify.LoginCheck;
import com.ascbank.web.UserController;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
@Controller
@RequestMapping("/user")
public class UserControllerImpl extends com.ascbank.web.basis.BaseAbstractController<Long, User, UserService> implements UserController<Login, Register> {
	
	private static final long	serialVersionUID	= -6215656516167426274L;
	
	// @Resource
	// UserService userService;
	
	private final Logger		log					= LoggerFactory.getLogger(UserControllerImpl.class);
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.qinzero.controller.UserControllerInterface#exit(javax.servlet.http .HttpServletRequest)
	 */
	@Override
	@RequestMapping(value = { "/exit" }, method = { RequestMethod.GET })
	public String exit(HttpServletRequest request) {
		if (getBeanService().logout() != null) {
			return systemConfig.getProperty("user_exit");
		}
		request.setAttribute("error", "{500.error}");
		return systemConfig.getProperty("error_500");
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.qinzero.controller.UserControllerInterface#login(javax.servlet.http .HttpSession, com.qinzero.entity.User, org.springframework.validation.BindingResult)
	 */
	@Override
	@RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
	public String login(@Validated(value = { CaptchaCheck.class, LoginCheck.class }) Login user, BindingResult br) {
		log.debug("----------User : {}-----BR : {}----", user, br);
		if (user != null && (user.getUsername() != null || user.getEmail() != null || user.getPhone() != null)) {
			if (br.hasErrors()) {
				log.debug("------------------{}-------------------", br.getAllErrors());
				return systemConfig.getProperty("user_login");
			} else {
				
				try {
					getBeanService().login(user);
					if (log.isDebugEnabled()) {
						log.debug("-----------{}---", user.toString());
					}
				} catch (UserException e) {
					br.addError(new ObjectError("error", e.getMessage()));
					return systemConfig.getProperty("user_login");
				}
			}
		}
		return systemConfig.getProperty("user_login_successs");
		
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.qinzero.controller.UserControllerInterface#register(javax.servlet .http.HttpSession, com.qinzero.entity.User, org.springframework.validation.BindingResult)
	 */
	@Override
	@RequestMapping(value = { "/register" }, method = { RequestMethod.POST })
	public String register(@Validated(value = { CaptchaCheck.class, AddCheck.class }) Register user, BindingResult br) {
		log.debug("----------User : {}-----BR : {}----", user, br);
		if (br.hasErrors()) {
			log.debug("------------------BR {}-------------------", br.getAllErrors());
			return systemConfig.getProperty("user_register");
		} else {
			try {
				log.debug("--------register:{}----------", user);
				getBeanService().add(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				br.addError(new ObjectError("error", e.getMessage()));
				return systemConfig.getProperty("user_register");
			}
			log.info(user.toString());
			return systemConfig.getProperty("user_register_success");
		}
	}

}
