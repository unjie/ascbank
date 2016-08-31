package com.ascbank.security.shiro.aop;

import java.lang.annotation.Annotation;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.subject.Subject;
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
		if (!(a instanceof AutoPermissions)) {
			return;
		}
		AutoPermissions ap = (AutoPermissions) a;
		Object obj = AutoPermissions.AUTHENTICATION[0];
		String[] perms;
		if (obj.getClass().isArray()) {
			perms = ((String[]) obj);
		} else {
			perms = new String[1];
			perms[0] = (String) obj;
		}
		Subject subject = getSubject();
		// log.debug("-------------assertAuthorized >{}--------------", perms);
		if (perms.length == 1) {
			subject.checkPermission(perms[0]);
			return;
		}
		// log.debug("-------------assertAuthorized >{}--------------", perms);
		if (Logical.AND.equals(ap.logical())) {
			getSubject().checkPermissions(perms);
			return;
		}
		// log.debug("-------------assertAuthorized >{}--------------", perms);
		if (Logical.OR.equals(ap.logical())) {
			// Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
			boolean hasAtLeastOnePermission = false;
			for (String permission : perms) {
				if (getSubject().isPermitted(permission)) {
					hasAtLeastOnePermission = true;
				}
			}
			// Cause the exception if none of the role match, note that the exception message will be a bit misleading
			if (!hasAtLeastOnePermission) {
				getSubject().checkPermission(perms[0]);
			}

		}

		AutoPermissionsAnnotationHandler.log.debug("-------------assertAuthorized >{}--------------", AutoPermissions.AUTHENTICATION.toString());

	}

}