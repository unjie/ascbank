/**
 *
 */
package com.ascbank.security.shiro.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;

import com.ascbank.security.shiro.authz.annotation.AutoPermissions;
import com.ascbank.util.StringUtil;

/**
 * @author jie
 *
 */
public class MethodInvocationAnnotationHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MethodInvocationAnnotationHandler.class);

	public Annotation MethodAnnotationHandler(MethodInvocation mi, Annotation a) {
		Subject currentUser = SecurityUtils.getSubject();
		if (a instanceof AutoPermissions) {
			boolean isPermissioin = false;
			AutoPermissions ap = (AutoPermissions) a;
			String permissions = "";
			String[] permission = ap.permission();
			String ids = ap.ids();
			String entityName = ap.entity();
			Object arg0 = mi.getArguments();

			log.debug("------------------->（AOP）拦截到了:{}", arg0);
			if (ids.isEmpty() && arg0 != null) {
				if (arg0.getClass().isArray() || arg0 instanceof Collection) {
					List list = arg0.getClass().isArray() ? CollectionUtils.arrayToList(arg0)
							: new ArrayList<Object>((Collection) arg0);
					for (Object obj : list) {
						ids += (obj instanceof Persistable ? ((Persistable) obj).getId() : obj) + ",";
					}
					ids.substring(0, ids.length() - 1);
				} else {
					if (arg0 instanceof Persistable) {
						if (((Persistable) arg0).getId() == null) {
							throw new UnauthorizedException("{default.not.permissions}");// 拦截通过更新添加数据(id=null)
						}
						ids = ((Persistable) arg0).getId().toString();
					} else {
						ids = arg0.toString();
					}
				}
			}

			if (arg0 != null && entityName.isEmpty()) {
				entityName = arg0.getClass().getSimpleName();
			}
			ids = (StringUtil.isNullOrEmpty(ids) ? "" : (":" + ids));

			if (permission.length == 1) {
				currentUser.checkPermission(entityName + ":" + permission[0] + ids);
				// permission = new String[] { pjp.getSignature().getName() };
			}

			for (int i = 0; 1 <= permission.length; i++) {
				// 当前登录人 具有权限
				permission[i] = entityName + ":" + permission[i] + ids;
			}

			if (Logical.AND.equals(ap.logical())) {
				currentUser.checkPermissions(permission);
			}

			if (Logical.OR.equals(ap.logical())) {
				// Avoid processing exceptions unnecessarily - "delay" throwing
				// the exception by calling hasRole first
				boolean hasAtLeastOnePermission = false;
				for (String perm : permission) {
					if (currentUser.isPermitted(perm)) {
						hasAtLeastOnePermission = true;
					}
				}
				// Cause the exception if none of the role match, note that the
				// exception message will be a bit misleading
				if (!hasAtLeastOnePermission) {
					currentUser.checkPermission(permission[0]);
				}

			} else {
				isPermissioin = true;
			}
			log.debug("------------------->（AOP）拦截到了:{}", permissions);
			if (!isPermissioin) {
				// 抛出无权限异常
				throw new UnauthorizedException("{default.not.permissions}");
			}
		}

		return a;
	}
	
}
