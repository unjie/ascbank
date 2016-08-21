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
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
		if (a instanceof AutoPermissions) {
			
			AutoPermissions ap = (AutoPermissions) a;
			
			String[] permission = ap.permission();
			String ids = ap.ids();
			String entityName = ap.entity();
			Object[] arg0 = mi.getArguments();
			String authentication = "";
			log.debug("------------------->（AOP）拦截到了:{}", arg0);
			if (arg0 != null) {
				List list = null;
				for (Object obj : arg0) {
					if (obj instanceof Persistable) {
						entityName = obj.getClass().getSimpleName();
						ids = ((Persistable) obj).getId() + "";
						log.debug("------------------->0.（AOP）拦截到了:{}", entityName);
						break;
					} else if (obj.getClass().isArray() || obj instanceof Collection) {
						if (Persistable.class.isAssignableFrom(obj.getClass().getComponentType())) {
							list = obj.getClass().isArray() ? CollectionUtils.arrayToList(obj)
									: new ArrayList<Object>((Collection) obj);
							entityName = obj.getClass().getComponentType().getSimpleName();
							log.debug("------------------->1.（AOP）拦截到了:{}", entityName);
						}
						
					}
				}
				if (entityName.isEmpty()) {
					if (list == null) {
						Class returnType = mi.getMethod().getReturnType();
						if (Persistable.class.isAssignableFrom(returnType)) {
							entityName = returnType.getSimpleName();
							
							log.debug("->>>>>>>>{}                {}      ------------------------------------", mi.getThis().getClass().getSuperclass().getGenericSuperclass().getClass(), ResolvableType.forType(mi.getThis().getClass().getSuperclass().getGenericSuperclass()).getGenerics());
							ResolvableType.forType(mi.getThis().getClass().getSuperclass().getGenericSuperclass()).resolveGeneric(1);
							log.debug("------------------->3.（AOP）拦截到了:{}                 |||>{}", returnType, mi.getThis().getClass().getName());
						} else if (returnType.isArray() || Collection.class.isAssignableFrom(returnType)) {
							entityName = returnType.getComponentType().getSimpleName();
							log.debug("------------------->4.（AOP）拦截到了:{}", entityName);
						} else {
							entityName = "";
							log.debug("------------------->5.（AOP）拦截到了:{}", entityName);
						}
					}
				}
				authentication += entityName + (entityName.isEmpty() ? "" : ":");
				if (permission == null || permission.length == 0) {
					authentication += mi.getMethod().getName();
				} else {
					authentication += StringUtils.arrayToDelimitedString(permission, ",");
				}
				if (ids.isEmpty()) {
					if (list != null) {
						for (Object obj : list) {
							ids += (obj instanceof Persistable ? ((Persistable) obj).getId() : obj) + ",";
						}
						ids.substring(0, ids.length() - 1);
						
					} else {
						ids = "*";
					}
					
				}
				
				ap.authentication()[0].concat(authentication + ":" + ids);
				log.debug("------------------->{}（AOP）拦截到了:{}", (authentication + ":" + ids).toString(), ap.authentication()[0]);
			}
		}
		return a;
	}

}
