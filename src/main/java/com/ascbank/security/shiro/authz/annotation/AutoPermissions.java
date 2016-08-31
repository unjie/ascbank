package com.ascbank.security.shiro.authz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.shiro.authz.annotation.Logical;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoPermissions {
	/**
	 * 注解处理转换为权限语句时使用
	 */
	Object[] AUTHENTICATION = { "" };
	
	/**
	 * The permission string which will be passed to {@link org.apache.shiro.subject.Subject#isPermitted(String)} to determine if the user is allowed to invoke the code protected by this annotation.
	 */
	/**
	 * 实体名称: 默认为参数的是实体名称 无参数,或参数不是entity必须填写,否出现EntityPermissionsExamples entity 必须继承
	 **/
	String entity() default "";
	
	/**
	 * 默认位参数实体Id,建议不填写 多个用,隔开
	 **/
	String ids() default "";
	
	/**
	 * The logical operation for the permission checks in case multiple roles are specified. AND is the default
	 *
	 * @since 1.1.0
	 */
	
	Logical logical() default Logical.AND;
	
	/**
	 * 操作权限 :add|upload|delelte|create|read 默认为方法名
	 **/
	String[] permission() default {};
	
}
