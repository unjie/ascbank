package com.ascbank.security.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ascbank.model.UrlFilter;
import com.ascbank.service.UrlFilterService;

/**
 * 基于URL的权限判断过滤器
 * <p>
 * 我们自动根据URL产生所谓的权限字符串，这一项在Shiro示例中是写在配置文件里面的，默认认为权限不可动态配置
 * <p>
 * URL举例：/User/create.do?***=*** -->权限字符串：/User/create.do
 *
 * @author zhengwei lastmodified 2013年8月15日
 *
 */
public class URLPermissionsFilter extends AuthorizationFilter {
	private final Logger		log	= LoggerFactory.getLogger(URLPermissionsFilter.class);
	private UrlFilterService	urlFilterService;

	/**
	 * 根据请求URL产生权限字符串，这里只产生，而比对的事交给Realm
	 *
	 * @param request
	 * @return
	 *
	 */
	protected UrlFilter buildPermissions(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getServletPath().toLowerCase();
		UrlFilter urlFilter = urlFilterService.read(path);

		return urlFilter;
	}

	/**
	 * @param mappedValue
	 *            指的是在声明url时指定的权限字符串，如/User/create.do=perms[User:create].
	 *            我们要动态产生这个权限字符串，所以这个配置对我们没用
	 */
	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {
		Boolean bool = true;// super.isAccessAllowed(request, response,
		// buildPermissions(request));

		UrlFilter urlFilter = buildPermissions(request);
		if (urlFilter != null) {
			
			Subject subject = getSubject(request, response);
			if (!StringUtils.isEmpty(urlFilter.getRoles())) {
				for (String role : urlFilter.getRoles().split(",")) {
					if (subject.hasRole(role)) {
						return true;
					}
				}
			}
			if (!StringUtils.isEmpty(urlFilter.getPermissions())) {
				bool = subject.isPermittedAll(urlFilter.getPermissions().split(","));
			}
		}

		log.debug("-------------{}-------------", bool);
		return bool;
	}

	@Resource(name = "urlFilterService")
	public void setUrlFilterService(UrlFilterService urlFilterService) {
		this.urlFilterService = urlFilterService;
	}
}
