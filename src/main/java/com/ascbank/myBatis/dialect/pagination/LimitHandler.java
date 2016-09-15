package com.ascbank.myBatis.dialect.pagination;

/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

import org.springframework.data.domain.Pageable;

/**
 * Contract defining dialect-specific LIMIT clause handling. Typically implementers might consider extending {@link AbstractLimitHandler} class.
 *
 * @author Lukasz Antoniak (lukasz dot antoniak at gmail dot com)
 */
public interface LimitHandler {
	/**
	 * Return processed SQL query.
	 *
	 * @param sql
	 *            the SQL query to process.
	 * @param selection
	 *            the selection criteria for rows.
	 *
	 * @return Query statement with LIMIT clause applied.
	 */
	String processSql(String sql, Pageable selection);
	
	/**
	 * Does this handler support some form of limiting query results via a SQL clause?
	 *
	 * @return True if this handler supports some form of LIMIT.
	 */
	boolean supportsLimit();
	
	/**
	 * Does this handler's LIMIT support (if any) additionally support specifying an offset?
	 *
	 * @return True if the handler supports an offset within the limit support.
	 */
	boolean supportsLimitOffset();
}