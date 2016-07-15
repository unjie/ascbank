package com.ascbank.myBatis.plugin;

import org.apache.ibatis.session.RowBounds;

/**
 * 
 * 数据库方言接口
 * 
 * 
 * 
 * @author 赵凡
 * 
 * @date 2016年5月15日
 * 
 * @version 1.0
 *
 * 
 * 
 */
public interface Dialect {
	
	/**
	 * 
	 * 获取特定数据库的统计SQL
	 * 
	 * 
	 * 
	 * @param querySql
	 * 
	 *            查询SQL
	 * 
	 * @return 特定数据库的统计SQL
	 * 
	 */
	String getCountSql(String querySql);

	/**
	 * 
	 * 获取特定数据库的分页SQL
	 * 
	 * 
	 * 
	 * @param querySql
	 * 
	 *            查询SQL
	 * 
	 * @param sort
	 * 
	 *            排序字段列表
	 * 
	 * @param order
	 * 
	 *            排序方式列表
	 * 
	 * @param rowBounds
	 * 
	 *            分页参数
	 * 
	 * @return 特定数据库的分页SQL
	 * 
	 */
	String getPagerSql(String querySql, String[] sort, String[] order, RowBounds rowBounds);

}