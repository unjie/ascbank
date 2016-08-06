/**
 *
 */
package com.ascbank.myBatis.dialect;

import org.springframework.data.domain.Pageable;

import com.ascbank.myBatis.dialect.pagination.AbstractLimitHandler;
import com.ascbank.myBatis.dialect.pagination.LimitHelper;

/**
 * @author jie
 *
 */
public class PostgreSQL82Dialect extends AbstractDialect implements Dialect {
	
	private static final AbstractLimitHandler LIMIT_HANDLER = new AbstractLimitHandler() {
		@Override
		public boolean bindLimitParametersInReverseOrder() {
			return true;
		}
		
		@Override
		public String processSql(String sql, Pageable selection) {
			final boolean hasOffset = LimitHelper.hasFirstRow(selection);
			int first = LimitHelper.getFirstRow(selection);
			int max = LimitHelper.getMaxRows(selection);
			return sql + (hasOffset ? String.format(" limit %s offset %s", first, max) : String.format(" limit %s", max));
		}
		
		@Override
		public boolean supportsLimit() {
			return true;
		}
	};
	
	@Override
	public String getLimitSql(String querySql, Pageable pageable) {
		// TODO Auto-generated method stub
		return LIMIT_HANDLER.processSql(querySql, pageable);
	}
	
}
