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

import com.ascbank.validator.annotation.interfaced.ValidetorInterface;
import com.ascbank.validator.annotation.validtor.UniqueValidator;

/**
 * @Unque 验证 bean 需要实现ValidetorInterface 接口 verifyBean与verifyClass 只需要传入一个,verifyBean优先于verifyClass 如果都不设置则默认验证不通过
 */
@Documented
@Constraint(validatedBy = { UniqueValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Unique {
	
	// 指定多个时使用
	@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Unique[] value();
	}
	
	String[] attributes();
	
	Class<?>[] groups() default {};
	
	String message() default "{default.update.unique.validated}";
	
	Class<? extends Payload>[] payload() default {};
	
	// ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
	
	String verifyBean() default "";
	
	@SuppressWarnings("rawtypes")
	Class<? extends ValidetorInterface> verifyClass() default ValidetorInterface.class;
	
}
