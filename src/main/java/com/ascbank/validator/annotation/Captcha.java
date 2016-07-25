/**
 *
 */
package com.ascbank.validator.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ascbank.validator.annotation.validtor.UniqueValidator;

/**
 * @author jie
 *
 */
@Documented
@Constraint(validatedBy = { UniqueValidator.class })
@Target({ FIELD, TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Captcha {
	
	// 指定多个时使用
	@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Captcha[] value();
	}
	
	Class<?>[] groups() default {};
	
	String key();
	
	String message() default "{default.captcha.validated}";
	
	Class<? extends Payload>[] payload() default {};

}