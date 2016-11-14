/**
 *
 */
package com.ascbank.web.viewresolver;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.ascbank.web.servlet.view.GenericFileView;

/**
 * @author jie
 *
 */
public class GenericFileViewResolver extends UrlBasedViewResolver {

	private Logger log = LoggerFactory.getLogger(GenericFileViewResolver.class);

	@Override
	protected View loadView(String viewName, Locale location) throws Exception {
		log.debug("--------{}----{}-----{}------------", getPrefix(), viewName, getSuffix());

		if (location == null) {
			throw new Exception("No location specified for GenericFileViewResolver.");
		}
		log.debug("--------{}----{}-----{}------------", getPrefix(), viewName, getSuffix());
		String requestedFilePath = getPrefix() + viewName + getSuffix();// location + viewName
		Resource resource = null;
		log.debug(requestedFilePath);

		resource = getApplicationContext().getResource(requestedFilePath);
		if (!resource.exists()) {
			return null;
		}

		log.debug("Requested file found: {},  viewName: {}", requestedFilePath, viewName);
		// 根据视图名，获取相应的 view 对象
		GenericFileView view = (GenericFileView) BeanUtils.instantiateClass(getViewClass());
		view.setUrl(requestedFilePath);

		String contentType = getContentType();
		if (contentType != null) {
			view.setContentType(contentType);
		}
		// 写入 view 内容
		view.setResponseContent(resource.getDescription());
		return view;
	}

}
