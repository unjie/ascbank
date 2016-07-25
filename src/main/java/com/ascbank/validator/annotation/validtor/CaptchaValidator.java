package com.ascbank.validator.annotation.validtor;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ascbank.model.base.CaptchaValidetorModel;
import com.ascbank.validator.annotation.Captcha;

// @SupportedValidationTarget(value = { ValidationTarget.PARAMETERS })
public class CaptchaValidator implements ConstraintValidator<Captcha, CaptchaValidetorModel> {
	
	private static final Logger	log	= LoggerFactory.getLogger(CaptchaValidator.class);

	private String				key;
	@Autowired
	private HttpSession			session;
	
	/**
	 * @Captcha 验证码验证
	 */
	@Override
	public void initialize(Captcha annotation) {
		// TODO Auto-generated method stub
		this.key = annotation.key();
	}

	@Override
	public boolean isValid(CaptchaValidetorModel model, ConstraintValidatorContext context) {
		
		if (model.getCaptcha() == null || !model.getCaptcha().equalsIgnoreCase((String) session.getAttribute(key))) {
			log.debug("================Captcha : {}===================", model.getCaptcha());
			return false;
		}

		return true;
		
	}
}
