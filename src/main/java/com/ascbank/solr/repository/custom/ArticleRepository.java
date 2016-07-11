/**
 * 
 */
package com.ascbank.solr.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;

import com.ascbank.model.Article;

/**
 * @author jie
 *
 */
public interface ArticleRepository {
	
	FacetPage<Article> findByContext(String context, Pageable page);

	Page<Article> findByClicks(Short clicks, Pageable page);
}
