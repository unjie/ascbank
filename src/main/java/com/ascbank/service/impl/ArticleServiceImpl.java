/**
 *
 */
package com.ascbank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.ArticleMapper;
import com.ascbank.model.Article;
import com.ascbank.service.ArticleService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("articleService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class ArticleServiceImpl extends BaseAbstractService<Long, Article, ArticleMapper> implements ArticleService<Long, Article> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -4394290178373662665L;
	
	@Override
	public Article readMenuIdForArticle(Long menuId) {
		
		return this.getBean().selelctByMenuId(menuId);
	}

}
