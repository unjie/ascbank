package com.ascbank.security.shiro.aop;

import java.lang.annotation.Annotation;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ascbank.security.shiro.authz.annotation.AutoPermissions;

public class AutoPermissionsAnnotationHandler extends AuthorizingAnnotationHandler {
	
	private static final Logger log = LoggerFactory.getLogger(AutoPermissionsAnnotationHandler.class);
	
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
		String authentication = ap.authentication()[0];

		log.debug("-------------assertAuthorized >{}--------------", authentication);
		getSubject().checkPermission(authentication);
		
	}
	
}