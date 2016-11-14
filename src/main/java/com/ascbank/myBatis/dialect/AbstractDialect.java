/**
 *
 */
package com.ascbank.myBatis.dialect;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ascbank.myBatis.dialect.utils.QueryUtils;

/**
 * @author jie
 *
 */
public abstract class AbstractDialect implements Dialect {

	private final Logger log = LoggerFactory.getLogger(AbstractDialect.class);

	@Override
	public String getCountSql(String querySql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrderSql(String querySql, Sort sort, String alias) {
		// TODO Auto-generated method stub
		return QueryUtils.applySorting(querySql, sort, alias);
	}

	@Override
	public String getPagerSql(String querySql, Pageable pageable) {
		return getPagerSql(querySql, pageable, QueryUtils.detectTableOrAlias(querySql));
	}

	@Override
	public String getPagerSql(String querySql, Pageable pageable, String alias) {
		// TODO Auto-generated method stub
		querySql = QueryUtils.applySorting(querySql, pageable.getSort(), alias);
		log.debug("-------------------------{}----------------------------", querySql);
		return getLimitSql(querySql, pageable);

	}

	@Override
	public String getPagerSql(String querySql, String[] sort, String[] order, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		return null;
	}

}
