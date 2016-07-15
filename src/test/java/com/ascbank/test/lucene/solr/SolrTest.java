package com.ascbank.test.lucene.solr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.repository.support.SolrRepositoryFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ascbank.model.Article;
import com.ascbank.solr.repository.ArticleStrengthenRepository;
import com.ascbank.solr.repository.impl.ArticleStrengthenRepositoryImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:solr/SpringContext-solr.xml")
public class SolrTest {
	
	private static Log					logger	= LogFactory.getLog(SolrTest.class);
	@Autowired
	ApplicationContext					context;
	
	private ArticleStrengthenRepository	repo;
	
	@Autowired
	SolrOperations						solrOperations;
	
	protected Article createArticle(int id) {
		Article article = new Article();
		Article initial = new Article();
		initial.setId((long) id);
		
		initial.setContext("baidu SEO book  test NO: 000" + id * 10);
		initial.setMenuId((long) id * 2);
		initial.setUploadtime(new Date());
		return article;
	}
	
	protected List<Article> createArticleList(int nrArticles) {
		List<Article> articles = new ArrayList<Article>(nrArticles);
		for (int i = 0; i < nrArticles; i++) {
			articles.add(createArticle(i));
		}
		return articles;
	}
	
	@Before
	public void setUp() {
		// Create new repository instance using Factory and inject custom
		// implementation
		repo = new SolrRepositoryFactory(this.solrOperations).getRepository(ArticleStrengthenRepository.class,
				new ArticleStrengthenRepositoryImpl(this.solrOperations));
	}
	
	@After
	public void tearDown() {
		solrOperations.delete(new SimpleQuery(new SimpleStringCriteria("*:*")));
		solrOperations.commit();
	}
	
	@Test
	public void testCRUD() {
		Assert.assertEquals(0, repo.count());
		
		Article initial = createArticle(1);
		
		repo.save(initial);
		Assert.assertEquals(1, repo.count());
		Article loaded = repo.findOne(initial.getId());
		// Assert.assertEquals(initial.getClicks(), loaded.getClicks());
		logger.info("-------------------------------------------" + loaded.getContext());
		loaded.setContext("changed named");
		repo.save(loaded);
		
		Assert.assertEquals(1, repo.count());
		
		loaded = repo.findOne(initial.getId());
		Assert.assertEquals("changed named", loaded.getContext());
		logger.info("-------------------------------------------" + loaded.getContext());
		repo.delete(loaded);
		Assert.assertEquals(0, repo.count());
	}
	
}