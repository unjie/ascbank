/**
 *
 */
package com.ascbank.test.mybatis.diacelt.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ascbank.myBatis.dialect.utils.QueryUtils;

/**
 * @author jie
 *
 */
public class QueryUtilsTest {
	
	private static final Logger log = LoggerFactory.getLogger(QueryUtilsTest.class);
	
	@Test
	public void getTableOrAlias() {
		String query = "select " + "id, avatar, captcha, description, email, encrypt, is_online, lastloginip, lastlogintime, password, phone, real_name, regtime, save, username, wechat, wechat_name" + " from t_user";
		log.debug("--{}--", QueryUtils.detectTableOrAlias(query));

	}
}
