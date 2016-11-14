/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.ascbank.repository.modules.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import com.ascbank.myBatis.dialect.Dialect;
import com.ascbank.myBatis.dialect.H2Dialect;
import com.ascbank.myBatis.dialect.MySQL5InnoDBDialect;
import com.ascbank.myBatis.dialect.Oracle10gDialect;
import com.ascbank.myBatis.dialect.PostgreSQL82Dialect;
import com.ascbank.myBatis.dialect.SQLServer2008Dialect;

// @Component
public class DataSoureInfo {
	/**
	 * 从DataSoure中取出connection, 根据connection的metadata中的jdbcUrl判断Dialect类型. 仅支持Oracle, H2, MySql, PostgreSql, SQLServer，如需更多数据库类型，请仿照此类自行编写。
	 */
	public static Dialect getDialect(DataSource dataSource) {
		String jdbcUrl = DataSoureInfo.getJdbcUrlFromDataSource(dataSource);

		// 根据jdbc url判断dialect
		if (StringUtils.contains(jdbcUrl, ":h2:")) {
			return new H2Dialect();
		} else if (StringUtils.contains(jdbcUrl, ":mysql:")) {
			return new MySQL5InnoDBDialect();
		} else if (StringUtils.contains(jdbcUrl, ":oracle:")) {
			return new Oracle10gDialect();
		} else if (StringUtils.contains(jdbcUrl, ":postgresql:")) {
			return new PostgreSQL82Dialect();
		} else if (StringUtils.contains(jdbcUrl, ":sqlserver:")) {
			return new SQLServer2008Dialect();
		} else {
			throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
		}
	}

	// @Resource(name="dataSource")
	private static String getJdbcUrlFromDataSource(DataSource dataSource) {
		Connection connection = null;
		try {
			System.out.println(dataSource);
			connection = dataSource.getConnection();
			if (connection == null) {
				throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
			}
			return connection.getMetaData().getURL();
		} catch (SQLException e) {
			throw new RuntimeException("Could not get database url", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
