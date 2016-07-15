package com.ascbank.myBatis.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class SelectPagingPlugin implements Interceptor {
	private final Logger log = LoggerFactory.getLogger(SelectPagingPlugin.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		log.info("_____________________intercept__________{}______________________________________", invocation);
		return invocation.proceed();
	}
	
	@Override
	public Object plugin(Object target) {
		log.info("______________________plugin_________{}______________________________________", target);
		return Plugin.wrap(target, this);
	}
	
	@Override
	public void setProperties(Properties properties) {
		log.info("______________________setProperties_________{}______________________________________", properties);

	}
}
