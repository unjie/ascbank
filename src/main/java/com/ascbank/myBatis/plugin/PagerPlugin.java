package com.ascbank.myBatis.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
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
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.ascbank.model.base.Page;
import com.ascbank.myBatis.drive.dialect.Dialect;
import com.ascbank.util.StringUtil;

/**
 *
 * 分页插件
 *
 *
 *
 * @author 赵凡
 *
 * @date 2016年5月12日
 *
 * @version 1.0
 *
 *
 *
 */
@Component
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }),
		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class })
})
public class PagerPlugin implements Interceptor {
	
	private Dialect						dialect;
	
	private final ThreadLocal<Integer>	tl	= new ThreadLocal<Integer>();
	
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object result = null;
		final String method = invocation.getMethod().getName();
		if ("prepare".equals(method)) {
			final BaseStatementHandler delegate = (BaseStatementHandler) SystemMetaObject.forObject(invocation.getTarget()).getValue("delegate");
			final MetaObject delegateMetaObj = SystemMetaObject.forObject(delegate);
			final MappedStatement ms = (MappedStatement) delegateMetaObj.getValue("mappedStatement");
			if (SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
				final ParameterHandler parameterHandler = delegate.getParameterHandler();
				final Map<String, Object> params = (Map<String, Object>) parameterHandler.getParameterObject();
				String[] sort = null;
				String[] order = null;
				if (params.containsKey("sort")) {
					String sortStr = (String) params.get("sort");
					sortStr = StringUtil.convertColumnName(sortStr);
					final String orderStr = (String) params.get("order");
					sort = sortStr.split(",");
					order = orderStr.split(",");
				}
				final String querySql = delegate.getBoundSql().getSql();
				final RowBounds rowBounds = (RowBounds) delegateMetaObj.getValue("rowBounds");
				delegateMetaObj.setValue("boundSql.sql", dialect.getPagerSql(querySql, sort, order, rowBounds));
				final String countSql = dialect.getCountSql(querySql);
				final Connection conn = (Connection) invocation.getArgs()[0];
				final PreparedStatement ps = conn.prepareStatement(countSql);
				parameterHandler.setParameters(ps);
				final ResultSet rs = ps.executeQuery();
				rs.next();
				final int count = rs.getInt(1);
				tl.set(count);
			}
			result = invocation.proceed();
		} else if ("handleResultSets".equals(method)) {
			final List<?> list = (List<?>) invocation.proceed();
			result = new Page<>(list, tl.get());
		}
		return result;
	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	public void setProperties(Properties properties) {
		
	}

}