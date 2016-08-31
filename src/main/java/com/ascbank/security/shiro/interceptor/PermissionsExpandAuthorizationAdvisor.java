package com.ascbank.security.shiro.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import com.ascbank.security.shiro.aop.AopPermissionsExpandAnnotationsAuthorizingMethodInterceptor;
import com.ascbank.security.shiro.authz.annotation.AutoPermissions;

/**
 * 自定义的注解权限AOP扫描
 *
 * @author zhihua
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PermissionsExpandAuthorizationAdvisor extends AuthorizationAttributeSourceAdvisor {
	
	private static final Logger							log						= LoggerFactory.getLogger(PermissionsExpandAuthorizationAdvisor.class);
	
	// 权限注解
	private static final Class<? extends Annotation>[]	SELF_ANNOTATION_CLASSES	= new Class[] { AutoPermissions.class };
	
	private static final long							serialVersionUID		= 3706056055344294174L;
	
	/**
	 * Create a new AuthorizationAttributeSourceAdvisor.
	 */
	public PermissionsExpandAuthorizationAdvisor() {
		setAdvice(new AopPermissionsExpandAnnotationsAuthorizingMethodInterceptor());
	}
	
	private boolean isSelfAnnotationPresent(Method method) {
		for (Class<? extends Annotation> annClass : PermissionsExpandAuthorizationAdvisor.SELF_ANNOTATION_CLASSES) {
			Annotation a = AnnotationUtils.findAnnotation(method, annClass);
			if (a != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 匹配带有注解的方法
	 */
	@Override
	public boolean matches(Method method, Class targetClass) {
		boolean flag = super.matches(method, targetClass);
		
		// 如果方法上没有权限注解，尝试获取类上的默认权限注解
		if (!flag) {
			if (isSelfAnnotationPresent(method)) {
				PermissionsExpandAuthorizationAdvisor.log.debug("--------matches --- <method> {} --- <targetClass> {} --------", method, targetClass);
				return true;
			}
			if (targetClass != null) {
				
				try {
					method = targetClass.getMethod(method.getName(), method.getParameterTypes());
					if (isSelfAnnotationPresent(method)) {
						return true;
					}
				} catch (NoSuchMethodException ignored) {
					// default return value is false. If we can't find the method, then obviously
					// there is no annotation, so just use the default return value.
				}
			}
		}
		return flag;
	}
	
}