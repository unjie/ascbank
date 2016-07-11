package com.ascbank.solr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.ascbank.model.Article;
import com.ascbank.solr.repository.custom.ArticleRepository;

public interface ArticleStrengthenRepository extends ArticleRepository, CrudRepository<Article,Long>, SolrCrudRepository<Article, Long>{

	

}
