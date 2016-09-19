/**
 *
 */
package com.ascbank.web.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/manage")
public class ManageController {
	
	private final Logger log = LoggerFactory.getLogger(ManageController.class);

	/*
	 * @RequestMapping(value = { "/", "/{pagename:[^\\.]+}**" }, path = {}, method = RequestMethod.GET, consumes = { "text/plain", "application/*" }) public String getHtml(@PathVariable("pagename") String pagename) { RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class); log.debug("==========rm => {}  , pageName = > {}=========", rm, pagename); return rm.value()[0] + "/" + ((pagename == null) ? "index" : pagename); }
	 */

}
