/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.model.Article;
import com.ascbank.service.ArticleService;
import com.ascbank.web.ArticleController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

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

	@Override
	@ResponseBody
	@RequestMapping("/read/menuid/{menuId:[\\d]+}")
	public JsonResultInfo read(@PathVariable("menuId") Long menuId) {
		JsonResultInfo jri = new JsonResultInfo();
		jri.setData(this.beanService.readMenuIdForArticle(menuId));
		jri.setSuccess(true);
		jri.setMessage("reda Article success!");
		return jri;
	}

}
