/**
 *
 */
package com.ascbank.web.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.model.User;
import com.ascbank.model.derive.Register;
import com.ascbank.service.UserService;
import com.ascbank.verify.AddCheck;
import com.ascbank.verify.CaptchaCheck;
import com.ascbank.verify.LoginCheck;
import com.ascbank.web.UserController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
@Controller
@RequestMapping("/user")
public class UserControllerImpl extends BaseAbstractController<Long, User, UserService<Long, User>> implements UserController<Long, User> {

	private static final long	serialVersionUID	= -6215656516167426274L;

	private final Logger		log					= LoggerFactory.getLogger(UserControllerImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 *
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/register" }, method = { RequestMethod.POST })
	public JsonResultInfo register(@RequestBody @Validated(value = { CaptchaCheck.class, LoginCheck.class, AddCheck.class }) Register user, BindingResult br) {
		log.debug("----------User : {}-----BR : {}----", user, br);
		JsonResultInfo info = new JsonResultInfo();
		if (br.hasErrors()) {
			log.debug("------------------BR {}-------------------", br.getAllErrors());
			info.setError(br.getAllErrors());
			info.setSuccess(false);
			info.setMessage(br.getAllErrors().get(0).getDefaultMessage());
		} else {
			try {
				log.debug("--------register:{}----------", user);
				getBeanService().add(user);
				info.setSuccess(true);
				info.setMessage("login successs");
				info.setData(this.systemConfig.get("user_register_successs").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// br.addError(new ObjectError("error", e.getMessage()));
				info.setError(e);
				info.setSuccess(false);
				info.setMessage("login failure ");
			}
			log.debug("-----------------{}--", user.toString());

		}
		return info;
	}
}
