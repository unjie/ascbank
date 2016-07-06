/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ascbank.solr.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;

import com.ascbank.model.Article;
import com.ascbank.solr.repository.ArticleRepository;
import com.ascbank.solr.repository.SolrSearchableFields;

/**
 * @author Christoph Strobl
 */
@NoRepositoryBean
public class SolrArticleRepository extends SimpleSolrRepository<Article, Long> implements ArticleRepository {

	@Override
	public Page<Article> findByClicks(Short clicks) {
		Query query = new SimpleQuery(new Criteria(SolrSearchableFields.CLICKS).is(clicks));
		return getSolrOperations().queryForPage(query, Article.class);
	}

	@Override
	public FacetPage<Article> findByContext(String context) {
		FacetQuery query = new SimpleFacetQuery(new Criteria(SolrSearchableFields.CONTEXT).startsWith(context));
		query.setFacetOptions(new FacetOptions(SolrSearchableFields.CONTEXT));
		return getSolrOperations().queryForFacetPage(query, Article.class);
	}

	
}
