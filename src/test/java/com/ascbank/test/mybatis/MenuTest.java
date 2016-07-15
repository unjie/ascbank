/**
 *
 */
package com.ascbank.test.mybatis;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ascbank.dao.MenuMapper;
import com.ascbank.model.Menu;

/**
 * @author jie
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MenuTest {
	private static Log	logger	= LogFactory.getLog(MenuTest.class);

	@Autowired
	ApplicationContext	context;
	
	// @Test
	public void menuAddTest() {
		final MenuMapper am = (MenuMapper) context.getBean("menuMapper");

		final Menu record = new Menu();
		record.setAlias("test");
		record.setAuthor("jiesun");
		record.setDescription("test");
		record.setEdittime(new Date());
		record.setIsNavigation(true);
		record.setIsPublish(true);
		record.setKeyword("key");
		record.setParentId(null);
		record.setSort((short) 0);
		record.setStem("0");
		record.setStyle("Article");
		record.setThumb("");
		record.setTitle("title");
		record.setUrl("/");

		logger.info("=======================insert Before record = " + record + "=========================");

		logger.info("=======================return = " + am.insertSelective(record) + "=========================");

		logger.info("=======================insert After record = " + record + "=========================");

	}

	// @Test
	public void menuDeleteTest() {
		final MenuMapper am = (MenuMapper) context.getBean("menuMapper");
		final int record = am.deleteByPrimaryKey(1L);

		logger.info("=======================" + record + "=========================");

	}

	@Test
	public void menuQueryTest() {
		final MenuMapper am = (MenuMapper) context.getBean("menuMapper");
		final Menu record = am.selectByPrimaryKey(2L);

		logger.info("=======================" + record + "=========================");

	}

	@Before
	public void setUp() {
		// Create

	}

	@After
	public void tearDown() {
		
	}
}
