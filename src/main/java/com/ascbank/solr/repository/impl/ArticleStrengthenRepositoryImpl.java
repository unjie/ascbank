/**
 * 
 */
package com.ascbank.solr.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;

import com.ascbank.model.Article;
import com.ascbank.solr.repository.ArticleStrengthenRepository;

/**
 * @author jie
 *
 */
public class ArticleStrengthenRepositoryImpl extends SimpleSolrRepository<Article, Long> implements ArticleStrengthenRepository {

	public ArticleStrengthenRepositoryImpl() {
		super();
	}

	public ArticleStrengthenRepositoryImpl(SolrOperations solrOperations) {
		super();
		this.setSolrOperations(solrOperations);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ascbank.solr.repository.custom.ArticleRepository#findByContext(java.
	 * lang.String)
	 */
	@Override
	public FacetPage<Article> findByContext(String context, Pageable page) {
		FacetQuery query = new SimpleFacetQuery(new Criteria("context").startsWith(context), page);

		return getSolrOperations().queryForFacetPage(query, Article.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ascbank.solr.repository.custom.ArticleRepository#findByClicks(java.
	 * lang.Short)
	 */
	@Override
	public Page<Article> findByClicks(Short clicks, Pageable page) {
		// TODO Auto-generated method stub
		return this.getSolrOperations().queryForPage(new SimpleQuery(new SimpleStringCriteria("clicks:" + clicks)).setPageRequest(page), Article.class);
	}

}
