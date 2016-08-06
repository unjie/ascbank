/**
 *
 */
package com.ascbank.myBatis.drive.dialect;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author su446
 *
 */
public interface Dialect {
	
	String getCountSql(String querySql);
	
	String getOrderSql(String querySql, Sort sort, String alias);

	Object getPagerSql(String querySql, Pageable pageable, String string);
	
	Object getPagerSql(String querySql, String[] sort, String[] order, RowBounds rowBounds);

}
