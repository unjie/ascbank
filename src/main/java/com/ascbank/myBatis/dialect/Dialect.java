/**
 *
 */
package com.ascbank.myBatis.dialect;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author su446
 *
 */
public interface Dialect {

	String getCountSql(String querySql);

	String getLimitSql(String querySql, Pageable pageable);

	String getOrderSql(String querySql, Sort sort, String alias);

	String getPagerSql(String querySql, Pageable pageable);

	String getPagerSql(String querySql, Pageable pageable, String string);

	String getPagerSql(String querySql, String[] sort, String[] order, RowBounds rowBounds);

}
