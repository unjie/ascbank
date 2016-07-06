package com.ascbank.solr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.query.result.FacetPage;

import com.ascbank.model.Article;

public interface ArticleRepository extends CrudRepository<Article,Long>{

	FacetPage<Article> findByContext(String context);

	Page<Article> findByClicks(Short clicks);

}
