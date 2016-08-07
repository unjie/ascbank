/**
 *
 */
package com.ascbank.myBatis.dialect;

import org.springframework.data.domain.Pageable;

/**
 * @author jie
 *
 */
public class H2Dialect extends AbstractDialect implements Dialect {
	
	@Override
	public String getLimitSql(String querySql, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
