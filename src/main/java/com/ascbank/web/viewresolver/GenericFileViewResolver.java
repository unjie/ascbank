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
		
		// If this resolver is not supposed to handle the given view,
		// return null to pass on to the next resolver in the chain.
		if (!canHandle(viewName, location)) {
			return null;
		}

		if (location == null) {
			throw new Exception("No location specified for GenericFileViewResolver.");
		}
		String requestedFilePath = getPrefix() + viewName + getSuffix();// location + viewName
		Resource resource = null;
		
		try {
			log.debug(requestedFilePath);
			resource = getApplicationContext().getResource(requestedFilePath);
			
		} catch (Exception e) {
			// 返回 null, 以便被下一个 resolver 处理
			log.debug("No file found for file: " + requestedFilePath);
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
		view.setResponseContent(resource.getURI().getPath());
		return view;
	}

}
