package com.ascbank.validator.annotation.validtor;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ascbank.validator.annotation.Captcha;

@Component
// @SupportedValidationTarget(value = { ValidationTarget.PARAMETERS,
// ValidationTarget.ANNOTATED_ELEMENT })
public class CaptchaValidator implements ConstraintValidator<Captcha, String> {
	
	private static final Logger	log	= LoggerFactory.getLogger(CaptchaValidator.class);
	
	private String				key;
	private int					length;
	@Autowired
	private HttpSession			session;
	
	/**
	 * @Captcha 验证码验证
	 */
	@Override
	public void initialize(Captcha annotation) {
		// TODO Auto-generated method stub
		this.key = annotation.key();
		this.length = annotation.length();
		
		log.debug("-------------------------key :{}   length :{}--------------------------------", key, length);
	}
	
	@Override
	public boolean isValid(String captcha, ConstraintValidatorContext context) {
		log.debug("-------------------------{}--------------------------------", captcha);
		return captcha != null && captcha.length() == length && captcha.equalsIgnoreCase((String) session.getAttribute(key));
		
	}
	
}
