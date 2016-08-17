package com.ascbank.shiro.authz.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ascbank.util.StringUtil;

@Aspect
@Component
// 次方法根据spring aop贴入方法 进行权限验证
public class PermissionInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(PermissionInterceptor.class);
	
	/******************************************
	 * <bean id="profiler" class= "com.qinzero.security.annotation.PermissionInterceptor"/> <aop:config> <aop:aspect id="myAspect" ref="profiler"> <aop:pointcut id= "businessService" expression= "execution(* com.qinzero.*.*(..)) &amp;&amp;@annotation(entityPermissions)" /> <aop:before pointcut-ref="businessService" method="doInterceptor"/> </aop:aspect> </aop:config>
	 ******************************************/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Around("execution(* com.ascbank.*(..)) && @annotation(entityPermissions)")
	public Object doInterceptor(ProceedingJoinPoint pjp, EntityPermissions entityPermissions) throws Throwable {
		boolean isPermissioin = false;
		Subject currentUser = SecurityUtils.getSubject();
		String permissions = "";
		log.debug("------------------->（AOP）拦截到了:{}", entityPermissions);
		// 没有获得注解 及不需要权限-- 则直接运行
		if (null != entityPermissions) {
			String[] permission = entityPermissions.permission();
			String ids = entityPermissions.ids();
			String entityName = entityPermissions.entity();
			
			Object arg0 = pjp.getArgs()[0];
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
			
			if (Logical.AND.equals(entityPermissions.logical())) {
				currentUser.checkPermissions(permission);
			}
			
			if (Logical.OR.equals(entityPermissions.logical())) {
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
				
			}
			
		} else {
			isPermissioin = true;
		}
		log.debug("------------------->（AOP）拦截到了:{},{}", permissions, pjp.getSignature().getName());
		if (isPermissioin) {
			// 有执行方法或权限不拦截
			return pjp.proceed();
		} else {
			// 抛出无权限异常
			throw new UnauthorizedException("{default.not.permissions}");
		}
	}
}