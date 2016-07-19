/**
 *
 */
package com.ascbank.test.mybatis;

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

import com.ascbank.dao.RoleMapper;
import com.ascbank.model.Role;

/**
 * @author jie
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RoleTest {
	private static Log	logger	= LogFactory.getLog(RoleTest.class);
	
	@Autowired
	ApplicationContext	context;
	
	// @Test
	public void roleAddTest() {
		RoleMapper am = (RoleMapper) context.getBean("roleMapper");

		Role record = new Role();
		record.setRoleName("");
		record.setDescription("");
		logger.info("=======================insert Before record = " + record + "=========================");
		
		logger.info("=======================return = " + am.insertSelective(record) + "=========================");
		
		logger.info("=======================insert After record = " + record + "=========================");
		
	}
	
	// @Test
	public void roleDeleteTest() {
		RoleMapper am = (RoleMapper) context.getBean("roleMapper");
		int record = am.deleteByPrimaryKey(1L);
		
		logger.info("=======================" + record + "=========================");
		
	}

	@Test
	public void roleQueryTest() {
		RoleMapper am = (RoleMapper) context.getBean("roleMapper");
		Role record = am.selectByPrimaryKey(2L);
		
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
