/**
 *
 */
package com.ascbank.validator.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ascbank.validator.annotation.validtor.CaptchaValidator;

/**
 * @author jie
 *
 */
@Constraint(validatedBy = { CaptchaValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface Captcha {
	
	// 指定多个时使用
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Captcha[] value();
	}
	
	Class<?>[] groups() default {};
	
	String key();
	
	int length() default 4;
	
	String message() default "{default.captcha.validated}";
	
	Class<? extends Payload>[] payload() default {};
	
	// ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
	
}