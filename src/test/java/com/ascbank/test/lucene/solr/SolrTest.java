package com.ascbank.test.lucene.solr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ascbank.model.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:solr/SpringContext-solr.xml")
public class SolrTest {

	private static Log logger = LogFactory.getLog(SolrTest.class);

	HttpSolrClient clinet;

	@Autowired
	ApplicationContext context;

	@Test
	public void createsRepositoryAndEmbeddedServerCorrectly() {

		assertThat(context.getBean("solrClient", HttpSolrClient.class), is(notNullValue()));
	}

	@Before
	public void setUp() {
		// Create new repository instance using Factory and inject custom
		// implementation
		clinet = context.getBean("solrClient", HttpSolrClient.class);

	}

	@After
	public void tearDown() {

	}

	/**
	 * 添加文档
	 */
	@Test
	public void addDoc() {

		SolrInputDocument doc = new SolrInputDocument();

		doc.addField("id", "11");
		doc.addField("title", "this is my document !!");

		try {

			UpdateResponse response = clinet.add(doc);
			// 提交
			clinet.commit();

			logger.info("########## Query Time :" + response.getQTime());
			logger.info("########## Elapsed Time :" + response.getElapsedTime());
			logger.info("########## Status :" + response.getStatus());

		} catch (SolrServerException | IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 添加多个文档
	 */
	@Test
	public void addDocs() {

		String[] titles = new String[] { "aaaaaaa", "bbbbbbb", "ccccccc", "dddddd", "eeeeee" };

		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		int i = 0;
		for (String str : titles) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", i++);
			doc.addField("title", str);
			docs.add(doc);
		}

		try {

			UpdateResponse response = clinet.add(docs);
			clinet.commit();

			logger.info("########## Query Time :" + response.getQTime());
			logger.info("########## Elapsed Time :" + response.getElapsedTime());
			logger.info("########## Status :" + response.getStatus());

		} catch (SolrServerException | IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 添加一个Entity到索引库
	 */
	@Test
	public void addBean() {

		Article article = new Article();
		try {

			UpdateResponse response = clinet.addBean(article);
			clinet.commit();

			logger.info("########## Query Time :" + response.getQTime());
			logger.info("########## Elapsed Time :" + response.getElapsedTime());
			logger.info("########## Status :" + response.getStatus());

		} catch (SolrServerException | IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 添加多个Entity到索引库
	 */
	@Test
	public void addBeans() {

		List<Article> articles = new ArrayList<Article>();

		Article article = new Article();

		Article article2 = new Article();

		articles.add(article);
		articles.add(article2);

		try {

			UpdateResponse response = clinet.addBeans(articles);
			clinet.commit();

			logger.info("########## Query Time :" + response.getQTime());
			logger.info("########## Elapsed Time :" + response.getElapsedTime());
			logger.info("########## Status :" + response.getStatus());

		} catch (SolrServerException | IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 删除索引
	 */
	@Test
	public void deleteDoc() {
		try {
			clinet.deleteById("0");
			clinet.commit();
		} catch (SolrServerException | IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 更新索引<br>
	 * solr索引库不同于数据库，没有更新的功能。如果想更新，先通过id删除对应的文档，再添加新的文档。
	 */
	@Test
	public void updateDoc() {
		// ... ...
	}

	/**
	 * 查询
	 * 
	 * @throws IOException
	 */
	@Test
	public void testQuery() throws IOException {
		String queryStr = "*:*";
		SolrQuery params = new SolrQuery(queryStr);
		params.set("rows", 10);
		try {
			QueryResponse response = clinet.query(params);
			SolrDocumentList list = response.getResults();
			logger.info("########### 总共 ： " + list.getNumFound() + "条记录");
			for (SolrDocument doc : list) {
				logger.info("######### id : " + doc.get("id") + "  title : " + doc.get("title"));
			}
		} catch (SolrServerException e) {
			logger.error("", e);
		}
	}

	/**
	 * 简单查询(分页)
	 * 
	 * @throws IOException
	 */
	@Test
	public void querySimple() throws IOException {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "this my");
		params.set("q.op", "and");
		params.set("start", 0);
		params.set("rows", 5);
		params.set("fl", "*,score");
		try {
			QueryResponse response = clinet.query(params);
			SolrDocumentList list = response.getResults();
			logger.info("########### 总共 ： " + list.getNumFound() + "条记录");
			for (SolrDocument doc : list) {
				logger.info("######### id : " + doc.get("id") + "  title : " + doc.get("title"));
			}
		} catch (SolrServerException e) {
			logger.error("", e);
		}
	}

	/**
	 * 查询(分页,高亮)
	 * 
	 * @throws IOException
	 */
	@Test
	public void queryCase() throws IOException {
		String queryStr = "title:this";
		SolrQuery params = new SolrQuery(queryStr);
		params.set("start", 0);
		params.set("rows", 5);

		// 启用高亮组件, 设置高亮
		params.setHighlight(true).addHighlightField("title").setHighlightSimplePre("<span class=\"red\">")
				.setHighlightSimplePost("</span>").setHighlightSnippets(2).setHighlightFragsize(1000).setStart(0)
				.setRows(10).set("hl.useFastVectorHighlighter", "true").set("hl.fragsize", "200");

		try {
			QueryResponse response = clinet.query(params);
			SolrDocumentList list = response.getResults();
			logger.info("########### 总共 ： " + list.getNumFound() + "条记录");
			for (SolrDocument doc : list) {
				logger.info("######### id : " + doc.get("id") + "  title : " + doc.get("title"));
			}

			Map<String, Map<String, List<String>>> map = response.getHighlighting();
			Iterator<String> iterator = map.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				Map<String, List<String>> values = map.get(key);
				logger.info("############################################################");
				logger.info("############ id : " + key);

				for (Map.Entry<String, List<String>> entry : values.entrySet()) {
					String subKey = entry.getKey();
					List<String> subValues = entry.getValue();

					logger.info("############ subKey : " + subKey);
					for (String str : subValues) {
						logger.info("############ subValues : " + str);
					}
				}

			}

		} catch (SolrServerException e) {
			logger.error("", e);
		}
	}

}