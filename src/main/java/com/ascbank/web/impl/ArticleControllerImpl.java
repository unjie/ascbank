/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascbank.model.Article;
import com.ascbank.service.ArticleService;
import com.ascbank.web.ArticleController;
import com.ascbank.web.basis.BaseAbstractController;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleControllerImpl extends BaseAbstractController<Long, Article, ArticleService<Long, Article>> implements ArticleController<Long, Article> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7713298953921279482L;

}
