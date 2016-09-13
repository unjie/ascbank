/**
 *
 */
package com.ascbank.web.servlet.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * @author jie
 *
 */
public class GenericFileView extends AbstractUrlBasedView {
	
	// 默认内容类型
	private final static String	CONTENT_TYPE	= "text/plain";
	
	// http response conent
	private String				responseContent;
	
	public GenericFileView() {
		super();
		setContentType(CONTENT_TYPE);
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 设置 content type
		response.setContentType(getContentType());
		// 写入 response 内容
		response.getWriter().write(this.responseContent);
		response.getWriter().close();
	}
	
	@Override
	public void setContentType(String contentType) {
		super.setContentType(contentType);
	}
	
	/**
	 * 设置 http response content
	 *
	 * @param responseContent
	 */
	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

}