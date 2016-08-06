/**
 *
 */
package com.ascbank.myBatis.drive.dialect;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author jie
 *
 */
@SuppressWarnings("unused")
public abstract class AbstractDialect implements Dialect {
	
	private static final Pattern	ALIAS_MATCH;

	private static final String		COMPLEX_COUNT_VALUE				= "$3$6";
	private static final Pattern	CONSTRUCTOR_EXPRESSION;
	private static final Pattern	COUNT_MATCH;
	public static final String		COUNT_QUERY_STRING				= "select count(%s) from %s x";
	private static final String		COUNT_REPLACEMENT_TEMPLATE		= "select count(%s) $5$6$7";

	private static final String		DEFAULT_ALIAS					= "x";
	public static final String		DELETE_ALL_QUERY_STRING			= "delete from %s x";
	private static final String		EQUALS_CONDITION_STRING			= "%s.%s = :%s";

	private static final String		IDENTIFIER						= "[\\p{Lu}\\P{InBASIC_LATIN}\\p{Alnum}._$]+";
	private static final String		IDENTIFIER_GROUP				= String.format("(%s)", IDENTIFIER);
	private static final String		JOIN							= "join\\s" + IDENTIFIER + "\\s(as\\s)?" + IDENTIFIER_GROUP;

	private static final Pattern	JOIN_PATTERN					= Pattern.compile(JOIN, Pattern.CASE_INSENSITIVE);
	private static final Pattern	NAMED_PARAMETER					= Pattern.compile(":" + IDENTIFIER + "|\\#" + IDENTIFIER, CASE_INSENSITIVE);

	private static final Pattern	NO_DIGITS						= Pattern.compile("\\D+");
	private static final Pattern	ORDER_BY						= Pattern.compile(".*order\\s+by\\s+.*", CASE_INSENSITIVE);

	private static final String		ORDER_BY_PART					= "(?iu)\\s+order\\s+by\\s+.*$";

	private static final Pattern	PROJECTION_CLAUSE				= Pattern.compile("select\\s+(.+)\\s+from");

	private static final int		QUERY_JOIN_ALIAS_GROUP_INDEX	= 2;

	private static final String		SIMPLE_COUNT_VALUE				= "$2";
	private static final int		VARIABLE_NAME_GROUP_INDEX		= 4;

	static {
		
		StringBuilder builder = new StringBuilder();
		builder.append("(?<=from)"); // from as starting delimiter
		builder.append("(?:\\s)+"); // at least one space separating
		builder.append(IDENTIFIER_GROUP); // Entity name, can be qualified (any
		builder.append("(?:\\sas)*"); // exclude possible "as" keyword
		builder.append("(?:\\s)+"); // at least one space separating
		builder.append("(\\w*)"); // the actual alias

		ALIAS_MATCH = compile(builder.toString(), CASE_INSENSITIVE);

		builder = new StringBuilder();
		builder.append("(select\\s+((distinct )?(.+?)?)\\s+)?(from\\s+");
		builder.append(IDENTIFIER);
		builder.append("(?:\\s+as)?\\s+)");
		builder.append(IDENTIFIER_GROUP);
		builder.append("(.*)");

		COUNT_MATCH = compile(builder.toString(), CASE_INSENSITIVE);

		builder = new StringBuilder();
		builder.append("select");
		builder.append("\\s+"); // at least one space separating
		builder.append("new");
		builder.append("\\s+"); // at least one space separating
		builder.append(IDENTIFIER);
		builder.append("\\s*"); // zero to unlimited space separating
		builder.append("\\(");
		builder.append(".*");
		builder.append("\\)");

		CONSTRUCTOR_EXPRESSION = compile(builder.toString(), CASE_INSENSITIVE);
	}

	/**
	 * Adds {@literal order by} clause to the JPQL query.
	 *
	 * @param query
	 * @param sort
	 * @param alias
	 * @return
	 */
	public static String applySorting(String query, Sort sort, String alias) {
		
		Assert.hasText(query);

		if (null == sort || !sort.iterator().hasNext()) {
			return query;
		}

		StringBuilder builder = new StringBuilder(query);

		if (!ORDER_BY.matcher(query).matches()) {
			builder.append(" order by ");
		} else {
			builder.append(", ");
		}

		Set<String> aliases = getOuterJoinAliases(query);

		for (Order order : sort) {
			builder.append(getOrderClause(aliases, alias, order)).append(", ");
		}

		builder.delete(builder.length() - 2, builder.length());

		return builder.toString();
	}

	/**
	 * Returns the order clause for the given {@link Order}. Will prefix the clause with the given alias if the referenced property refers to a join alias, i.e. starts with {@code $alias.}.
	 *
	 * @param joinAliases
	 *            the join aliases of the original query.
	 * @param alias
	 *            the alias for the root entity.
	 * @param order
	 *            the order object to build the clause for.
	 * @return
	 */
	private static String getOrderClause(Set<String> joinAliases, String alias, Order order) {
		
		String property = order.getProperty();
		boolean qualifyReference = !property.contains("("); // ( indicates a function

		for (String joinAlias : joinAliases) {
			if (property.startsWith(joinAlias.concat("."))) {
				qualifyReference = false;
				break;
			}
		}

		String reference = qualifyReference ? String.format("%s.%s", alias, property) : property;
		String wrapped = order.isIgnoreCase() ? String.format("lower(%s)", reference) : reference;

		return String.format("%s %s", wrapped, toJpaDirection(order));
	}
	
	/**
	 * Returns the aliases used for {@code left (outer) join}s.
	 *
	 * @param query
	 * @return
	 */
	static Set<String> getOuterJoinAliases(String query) {
		
		Set<String> result = new HashSet<String>();
		Matcher matcher = JOIN_PATTERN.matcher(query);
		
		while (matcher.find()) {
			
			String alias = matcher.group(QUERY_JOIN_ALIAS_GROUP_INDEX);
			if (StringUtils.hasText(alias)) {
				result.add(alias);
			}
		}
		
		return result;
	}
	
	private static String toJpaDirection(Order order) {
		return order.getDirection().name().toLowerCase(Locale.US);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.myBatis.drive.dialect.Dialect#getCountSql(java.lang.String)
	 */
	@Override
	public String getCountSql(String querySql) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getOrderSql(String querySql, Sort sort, String alias) {
		return applySorting(querySql, sort, alias);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.myBatis.drive.dialect.Dialect#getPagerSql(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public String getPagerSql(String querySql, Pageable pageable, String alias) {
		
		querySql = getOrderSql(querySql, pageable.getSort(), alias);

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.myBatis.drive.dialect.Dialect#getPagerSql(java.lang.String, java.lang.String[], java.lang.String[], org.apache.ibatis.session.RowBounds)
	 */
	@Override
	public String getPagerSql(String querySql, String[] sort, String[] order, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		return null;
	}

}
