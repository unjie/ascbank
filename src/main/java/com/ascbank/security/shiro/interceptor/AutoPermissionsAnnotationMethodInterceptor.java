package com.ascbank.security.shiro.interceptor;

import java.lang.annotation.Annotation;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

import com.ascbank.security.shiro.aop.AutoPermissionsAnnotationHandler;
import com.ascbank.security.shiro.aop.MethodInvocationAnnotationHandler;

public class AutoPermissionsAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {
	
	public AutoPermissionsAnnotationMethodInterceptor() {
		super(new AutoPermissionsAnnotationHandler());
	}
	
	public AutoPermissionsAnnotationMethodInterceptor(AnnotationResolver resolver) {
		super(new AutoPermissionsAnnotationHandler(), resolver);
	}

	@Override
	public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
		try {
			
			Annotation a = getAnnotation(mi);
			MethodInvocationAnnotationHandler miah = new MethodInvocationAnnotationHandler();
			a = miah.MethodAnnotationHandler(mi, a);
			((AuthorizingAnnotationHandler) getHandler()).assertAuthorized(a);
		} catch (AuthorizationException ae) {
			// Annotation handler doesn't know why it was called, so add the information here if possible.
			// Don't wrap the exception here since we don't want to mask the specific exception, such as
			// UnauthenticatedException etc.
			if (ae.getCause() == null) {
				ae.initCause(new AuthorizationException("Not authorized to invoke method: " + mi.getMethod()));
			}
			throw ae;
		}
	}
	
}