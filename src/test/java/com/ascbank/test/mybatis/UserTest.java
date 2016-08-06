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

import com.ascbank.dao.UserMapper;
import com.ascbank.model.User;

/**
 * @author jie
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserTest {
	private static Log	logger	= LogFactory.getLog(UserTest.class);
	
	@Autowired
	ApplicationContext	context;
	
	@Before
	public void setUp() {
		// Create
		
	}
	
	@After
	public void tearDown() {
		
	}
	
	// @Test
	public void userAddTest() {
		UserMapper am = (UserMapper) context.getBean("userMapper");
		
		User record = new User();
		record.setAvatar("");
		
		logger.info("=======================insert Before record = " + record + "=========================");
		
		logger.info("=======================return = " + am.insertSelective(record) + "=========================");
		
		logger.info("=======================insert After record = " + record + "=========================");
		
	}
	
	// @Test
	public void userDeleteTest() {
		UserMapper am = (UserMapper) context.getBean("userMapper");
		int record = am.deleteByPrimaryKey(1L);
		
		logger.info("=======================" + record + "=========================");
		
	}
	
	@Test
	public void userQueryTest() {
		UserMapper am = (UserMapper) context.getBean("userMapper");
		User record = am.selectByUsername("admin");
		
		logger.info("=======================" + record + "=========================");
		
	}
}
