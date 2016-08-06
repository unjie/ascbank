package com.ascbank.myBatis.plugin;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.ascbank.myBatis.dialect.Dialect;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class SelectPagingPlugin implements Interceptor {
	
	// @Autowired
	private Dialect			dialect;

	private final Logger	log	= LoggerFactory.getLogger(SelectPagingPlugin.class);

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 *
	 * private String getCountSql(String sql) { return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage"; }
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		BaseStatementHandler delegate = (BaseStatementHandler) SystemMetaObject.forObject(invocation.getTarget()).getValue("delegate");
		
		MetaObject delegateMetaObj = SystemMetaObject.forObject(delegate);
		
		MappedStatement ms = (MappedStatement) delegateMetaObj.getValue("mappedStatement");
		
		if (SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
			
			ParameterHandler parameterHandler = delegate.getParameterHandler();
			
			Object params = parameterHandler.getParameterObject();
			log.debug("-------------{}-----------", params.getClass().getName());
			
			Pageable pageable = params instanceof Pageable ? (Pageable) params : null;
			
			/*
			 * for (String s : params.keySet()) { log.debug("..................{}..................", s); }
			 *
			 * for (Object o : params.values()) { if (o instanceof Pageable) { pageable = (Pageable) o; } }
			 */
			
			log.debug("===============================");
			if (pageable != null) {
				String querySql = delegate.getBoundSql().getSql();
				delegateMetaObj.setValue("boundSql.sql", dialect.getPagerSql(querySql, pageable));
				log.debug("---------------------query originalSql =>  {}------------------------------", querySql);
			}
			
		}
		
		return invocation.proceed();
		
	}
	
	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}
	
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	
	@Override
	public void setProperties(Properties arg0) {
	}
}
