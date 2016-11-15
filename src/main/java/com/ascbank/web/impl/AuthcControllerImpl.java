/**
 *
 */
package com.ascbank.web.impl;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.dependency.injection.InjectionInterface;
import com.ascbank.exception.UserException;
import com.ascbank.model.derive.Login;
import com.ascbank.service.AuthcService;
import com.ascbank.verify.CaptchaCheck;
import com.ascbank.verify.LoginCheck;
import com.ascbank.web.AuthcController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
@Controller
@RequestMapping(value = { "/auth" })
public class AuthcControllerImpl implements AuthcController<Login>, InjectionInterface<AuthcService<Login>> {
	
	// @Resource
	AuthcService<Login>		beanService;
	
	private final Logger	log	= LoggerFactory.getLogger(AuthcControllerImpl.class);
	@Autowired
	protected Properties	systemConfig;
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.qinzero.controller.UserControllerInterface#exit(javax.servlet.http .HttpServletRequest)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/exit" }, method = { RequestMethod.GET })
	public JsonResultInfo exit(HttpServletRequest request) {
		JsonResultInfo info = new JsonResultInfo();
		
		if (beanService.logout() != null) {
			info.setData(this.systemConfig.get("user_exit_successs").toString());
			info.setMessage("user exit successs");
			info.setSuccess(true);
		} else {
			info.setSuccess(false);
			info.setMessage("user exit failure");
		}
		
		return info;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.qinzero.controller.UserControllerInterface#login(javax.servlet.http .HttpSession, com.qinzero.entity.User, org.springframework.validation.BindingResult)
	 *
	 *
	 * 登录
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
	public JsonResultInfo login(@RequestBody @Validated(value = { CaptchaCheck.class, LoginCheck.class }) Login user, BindingResult br) {
		log.debug("----------User : {}-----BR : {}----", user, br);
		
		JsonResultInfo info = new JsonResultInfo();
		// 验证码以验证后面无需使用
		user.setCaptcha(null);
		if (user != null && (user.getUsername() != null || user.getEmail() != null || user.getPhone() != null)) {
			if (br.hasErrors()) {
				log.debug("------------------{}-------------------", br.getAllErrors());
				info.setError(br.getAllErrors());
				info.setMessage(br.getAllErrors().get(0).getDefaultMessage());
				info.setSuccess(false);
				
			} else {
				
				try {
					beanService.login(user);
					
					info.setData(user);
					info.setMessage("login successs");
					log.debug("-----------{}---", user.toString());
					info.setSuccess(true);
				} catch (UserException e) {
					// br.addError(new ObjectError("error", e.getMessage()));
					info.setError(e);
					info.setSuccess(false);
					info.setMessage("login failure ");
				}
			}
		}
		return info;
		
	}
	
	@Override
	@ResponseBody
	@RequestMapping(value = { "/readMe" }, method = { RequestMethod.GET })
	public JsonResultInfo readMe(HttpServletRequest request) {
		JsonResultInfo info = new JsonResultInfo();
		
		info.setData(SecurityUtils.getSubject().getSession());
		
		info.setSuccess(true);
		return info;
	}
	
	@Resource(name = "authcService")
	@Override
	public void setBean(AuthcService<Login> bean) {
		// TODO Auto-generated method stub
		this.beanService = bean;
	}
	
}
