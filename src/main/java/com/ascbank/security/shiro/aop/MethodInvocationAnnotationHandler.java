/**
 *
 */
package com.ascbank.security.shiro.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.aop.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;

import com.ascbank.GenericityInterface;
import com.ascbank.security.shiro.authz.annotation.AutoPermissions;

/**
 * @author jie
 *
 */
public class MethodInvocationAnnotationHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MethodInvocationAnnotationHandler.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Annotation MethodAnnotationHandler(MethodInvocation mi, Annotation a) {
		// Subject currentUser = SecurityUtils.getSubject();
		if (!(a instanceof AutoPermissions)) {
			return a;
		}
		
		AutoPermissions ap = (AutoPermissions) a;
		
		String[] permission = ap.permission();
		String ids = ap.ids();
		String entityName = ap.entity();
		Object[] arg = mi.getArguments();
		String authentication = "";
		
		MethodInvocationAnnotationHandler.log.debug("------------------->（AOP）拦截到了:{}", arg);
		
		if (entityName.isEmpty()) {
			if (arg != null) {
				
				for (Object obj : arg) {
					MethodInvocationAnnotationHandler.log.debug("------------------->（AOP）拦截到了{}", obj);
					if (obj instanceof Persistable) {
						if (entityName.isEmpty()) {
							entityName = obj.getClass().getSimpleName();
						}
						if (ids.isEmpty()) {
							ids = ((Persistable) obj).getId() + "";
						}
						MethodInvocationAnnotationHandler.log.debug("------------------->0.（AOP）拦截到了:{}", entityName);
						break;
					} else if (obj.getClass().isArray() || obj instanceof Collection) {
						if (Persistable.class.isAssignableFrom(obj.getClass().getComponentType())) {
							List list = obj.getClass().isArray() ? CollectionUtils.arrayToList(obj)
									: new ArrayList<Object>((Collection) obj);
							if (entityName.isEmpty()) {
								entityName = obj.getClass().getComponentType().getSimpleName();
							}
							if (ids.isEmpty()) {
								for (Object ob : list) {
									ids += ((Persistable) ob).getId() + ",";
								}
								ids.substring(0, ids.length() - 1);
								
							}
							MethodInvocationAnnotationHandler.log.debug("------------------->1.（AOP）拦截到了:{}", entityName);
						}
						
					}
					MethodInvocationAnnotationHandler.log.debug("------------------->（AOP）拦截到了{}", obj);
					
				}
			}
			
			if (entityName.isEmpty()) {
				if (mi.getThis() instanceof GenericityInterface) {
					GenericityInterface gi = (GenericityInterface) mi.getThis();
					Class[] clazz = gi.getGenericitys();
					
					for (Class cls : clazz) {
						if (Persistable.class.isAssignableFrom(cls)) {
							entityName = cls.getSimpleName();
							
							if (ids.isEmpty() && arg != null && cls.getSuperclass().getConstructors()[0].equals(arg[0])) {
								ids = arg[0].toString();
							}
							break;
						}
					}
					MethodInvocationAnnotationHandler.log.debug("------------------->（AOP）拦截到了{}", entityName);
				}
			} else {
				MethodInvocationAnnotationHandler.log.debug("------------------->（AOP）拦截到了{}", 555);
			}
		}
		authentication += entityName + (entityName.isEmpty() ? "" : ":");
		if (!ids.isEmpty()) {
			ids = ":" + ids;
		}
		if (permission == null || permission.length == 0) {
			authentication += mi.getMethod().getName();
			AutoPermissions.AUTHENTICATION[0] = authentication + ids;
		} else {
			// authentication += StringUtils.arrayToDelimitedString(permission, ",");
			// String[] p = permission.clone();
			for (int i = 0; i < permission.length; i++) {
				permission[i] = authentication + permission[i] + ids;
			}
			AutoPermissions.AUTHENTICATION[0] = permission;
		}
		
		// ap.AUTHENTICATION[0] = authentication + (ids.isEmpty() ? "" : (":" + ids));
		
		MethodInvocationAnnotationHandler.log.debug("------------------->{}（AOP）拦截到了:{}", (authentication + ":" + ids).toString(), AutoPermissions.AUTHENTICATION[0]);
		
		return a;
	}
	
}
