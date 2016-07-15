/**
 *
 */
package com.ascbank.web.impl;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ascbank.exception.UserException;
import com.ascbank.model.User;
import com.ascbank.service.UserService;
import com.ascbank.verify.FormatCheck;
import com.ascbank.verify.LoginCheck;
import com.ascbank.verify.UnqueCheck;
import com.ascbank.web.BaseAbstractController;
import com.ascbank.web.UserController;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
@Controller
@RequestMapping("/user")
public class UserControllerImpl extends BaseAbstractController<Long, User, UserService> implements UserController {
	
	private static final long	serialVersionUID	= -6215656516167426274L;

	// @Resource
	// UserService userService;

	private final Logger		log					= LoggerFactory.getLogger(UserControllerImpl.class);

	private Properties			systemConfig;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.qinzero.controller.UserControllerInterface#exit(javax.servlet.http
	 * .HttpServletRequest)
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
	 * @see
	 * com.qinzero.controller.UserControllerInterface#login(javax.servlet.http
	 * .HttpSession, com.qinzero.entity.User,
	 * org.springframework.validation.BindingResult)
	 */
	@Override
	@RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
	public String login(HttpSession session, @Validated(value = { LoginCheck.class }) User user, BindingResult br) {
		if (br.hasErrors()) {
			return systemConfig.getProperty("user_login");
		} else {
			if (user.getCaptcha() != null && !user.getCaptcha().equalsIgnoreCase(
					(String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY))) {
				br.addError(new ObjectError("captcha", "{User.captcha.input}"));
				return systemConfig.getProperty("user_login");
			}
			try {
				getBeanService().login(user);
				if (log.isDebugEnabled()) {
					log.debug("-----------{}---", user.toString());
				}
			} catch (UserException e) {
				br.addError(new ObjectError("error", e.getMessage()));
				return systemConfig.getProperty("user_login");
			}
			// SecurityUtils.getSubject().getSession().getAttribute("username");
			return systemConfig.getProperty("user_login_successs");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.qinzero.controller.UserControllerInterface#register(javax.servlet
	 * .http.HttpSession, com.qinzero.entity.User,
	 * org.springframework.validation.BindingResult)
	 */
	@Override
	@RequestMapping(value = { "/register" }, method = { RequestMethod.POST })
	public String register(HttpSession session, @Validated(value = { FormatCheck.class, UnqueCheck.class }) User user,
			BindingResult br) {
		if (br.hasErrors()) {
			return systemConfig.getProperty("user_register");
		} else {
			if (user.getCaptcha() != null && !user.getCaptcha().equalsIgnoreCase(
					(String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY))) {
				br.addError(new ObjectError("captcha", "{User.captcha.input}"));
				return systemConfig.getProperty("user_register");
			}
			try {
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
