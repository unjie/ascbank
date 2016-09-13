/**
 *
 */
package com.ascbank.web.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jie
 *
 */
@Controller
public class IndexController {
	private Logger log = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = { "/**/*.html", "/*.html" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		log.debug("======{}===========", request.getServletPath());
		return request.getServletPath();
		
	}
}
