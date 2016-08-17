package com.ascbank.security.shiro.aop;

import java.lang.annotation.Annotation;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;

import com.ascbank.security.shiro.authz.annotation.AutoPermissions;

public class AutoPermissionsAnnotationHandler extends AuthorizingAnnotationHandler {
	
	/**
	 * 构造函数
	 *
	 * @param annotationClass
	 */
	public AutoPermissionsAnnotationHandler() {
		super(AutoPermissions.class);
	}

	@Override
	public void assertAuthorized(Annotation a) throws AuthorizationException {
		AutoPermissions ap = (AutoPermissions) a;
		
		String[] permission = ap.permission();
		String ids = ap.ids();
		String entityName = ap.entity();
		
		//
		// getSubject().checkRoles(Arrays.asList(roles));
		
	}

}