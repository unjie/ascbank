/**
 *
 */
package com.ascbank.test.mybatis;

import java.util.Date;
import java.util.List;

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
import com.ascbank.service.impl.MenuServiceImpl;

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
		MenuServiceImpl am = (MenuServiceImpl) context.getBean("menuService");

		Menu record = new Menu();
		record.setAlias("test3");
		record.setAuthor("jiesun");
		record.setDescription("test");
		record.setEdittime(new Date());
		record.setIsNavigation(true);
		record.setIsPublish(true);
		record.setKeyword("key");
		record.setParentId(0L);
		record.setSort((short) 0);
		record.setStem(",0");
		record.setStyle("Article");
		record.setThumb("");
		record.setTitle("title3");
		record.setUrl("/");
		
		logger.info("=======================insert Before record = " + record + "=========================");
		
		try {
			logger.info("=======================return = " + am.add(record) + "=========================");
			logger.info("=======================insert After record = " + record + "=========================");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// @Test
	public void menuDeleteTest() {
		MenuMapper am = (MenuMapper) context.getBean("menuMapper");
		int record = am.deleteByLikeStem_(",18,");
		
		logger.info("=======================" + record + "=========================");
		
	}
	
	@Test
	public void menuQueryTest() {
		MenuMapper am = (MenuMapper) context.getBean("menuMapper");
		List<Menu> record = am.selectByLikeStem_(",18,");
		
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
