/**
 *
 */
package com.ascbank.myBatis.dialect.utils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Simple utility class to create JPA queries.
 *
 * @author Oliver Gierke
 * @author Kevin Raymond
 * @author Thomas Darimont
 * @author Komi Innocent
 */
public class QueryUtils {
	
	private static final Pattern	ALIAS_MATCH;
	private static final String		COMPLEX_COUNT_VALUE				= "$3$6";
	private static final Pattern	COUNT_MATCH;
	public static final String		COUNT_QUERY_STRING				= "select count(%s) from %s x";
	
	private static final String		COUNT_REPLACEMENT_TEMPLATE		= "select count(%s) $5$6$7";
	
	private static final String		DEFAULT_ALIAS					= "x";
	public static final String		DELETE_ALL_QUERY_STRING			= "delete from %s x";
	private static final String		EQUALS_CONDITION_STRING			= "%s.%s = :%s";
	private static final String		IDENTIFIER						= "[\\p{Lu}\\P{InBASIC_LATIN}\\p{Alnum}._$]+";
	private static final String		IDENTIFIER_GROUP				= String.format("(%s)", QueryUtils.IDENTIFIER);
	
	private static final String		JOIN							= "join\\s" + QueryUtils.IDENTIFIER + "\\s(as\\s)?" + QueryUtils.IDENTIFIER_GROUP;
	private static final Pattern	JOIN_PATTERN					= Pattern.compile(QueryUtils.JOIN, Pattern.CASE_INSENSITIVE);
	
	private static final Pattern	NAMED_PARAMETER					= Pattern.compile(":" + QueryUtils.IDENTIFIER + "|\\#" + QueryUtils.IDENTIFIER, Pattern.CASE_INSENSITIVE);
	private static final Pattern	ORDER_BY						= Pattern.compile(".*order\\s+by\\s+.*", Pattern.CASE_INSENSITIVE);
	
	private static final String		ORDER_BY_PART					= "(?iu)\\s+order\\s+by\\s+.*$";
	private static final int		QUERY_JOIN_ALIAS_GROUP_INDEX	= 2;
	
	private static final String		SIMPLE_COUNT_VALUE				= "$2";
	private static final Pattern	TABLE_MATCH;
	
	// private static final Pattern TABLE_MATCH;
	private static final int		VARIABLE_NAME_GROUP_INDEX		= 4;
	
	static {
		
		StringBuilder builder = new StringBuilder();
		builder.append("(?<=from)"); // from as starting delimiter 从开始的符号
		builder.append("(?:\\s)+"); // at least one space separating 至少一个空间分离
		builder.append(QueryUtils.IDENTIFIER_GROUP); // Entity name, can be qualified (any 实体名称，可以是限定的（任何
		TABLE_MATCH = Pattern.compile(builder.toString(), Pattern.CASE_INSENSITIVE);
		
		builder.append("(?:\\sas)*"); // exclude possible "as" keyword 排除可能的“as”关键字
		builder.append("(?:\\s)+"); // at least one space separating 至少一个空格分离
		builder.append("(\\w*)"); // the actual alias 实际的别名
		
		ALIAS_MATCH = Pattern.compile(builder.toString(), Pattern.CASE_INSENSITIVE);
		
		// TABLE_MATCH = compile(builder.toString(), CASE_INSENSITIVE);
		
		builder = new StringBuilder();
		builder.append("(select\\s+((distinct )?(.+?)?)\\s+)?(from\\s+");
		builder.append(QueryUtils.IDENTIFIER);
		builder.append("(?:\\s+as)?\\s+)");
		builder.append(QueryUtils.IDENTIFIER_GROUP);
		builder.append("(.*)");
		
		COUNT_MATCH = Pattern.compile(builder.toString(), Pattern.CASE_INSENSITIVE);
		
	}
	
	/**
	 * Adds {@literal order by} clause to the JPQL query. Uses the {@link #DEFAULT_ALIAS} to bind the sorting property to.
	 *
	 * @param query
	 * @param sort
	 * @return
	 */
	public static String applySorting(String query, Sort sort) {
		
		return QueryUtils.applySorting(query, sort, QueryUtils.DEFAULT_ALIAS);
	}
	
	/**
	 * Creates a where-clause referencing the given entities and appends it to the given query string. Binds the given entities to the query.
	 *
	 * @param <T>
	 * @param queryString
	 * @param entities
	 * @param entityManager
	 * @return
	 */
	/*
	 * public static <T> Query applyAndBind(String queryString, Iterable<T> entities, EntityManager entityManager) {
	 *
	 * Assert.notNull(queryString); Assert.notNull(entities); Assert.notNull(entityManager);
	 *
	 * Iterator<T> iterator = entities.iterator();
	 *
	 * if (!iterator.hasNext()) { return entityManager.createQuery(queryString); }
	 *
	 * String alias = detectAlias(queryString); StringBuilder builder = new StringBuilder(queryString); builder.append(" where");
	 *
	 * int i = 0;
	 *
	 * while (iterator.hasNext()) {
	 *
	 * iterator.next();
	 *
	 * builder.append(String.format(" %s = ?%d", alias, ++i));
	 *
	 * if (iterator.hasNext()) { builder.append(" or"); } }
	 *
	 * Query query = entityManager.createQuery(builder.toString());
	 *
	 * iterator = entities.iterator(); i = 0;
	 *
	 * while (iterator.hasNext()) { query.setParameter(++i, iterator.next()); }
	 *
	 * return query; }
	 */
	
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
		
		if (!QueryUtils.ORDER_BY.matcher(query).matches()) {
			builder.append(" order by ");
		} else {
			builder.append(", ");
		}
		
		Set<String> aliases = QueryUtils.getOuterJoinAliases(query);
		
		for (Order order : sort) {
			builder.append(QueryUtils.getOrderClause(aliases, alias, order)).append(", ");
		}
		
		builder.delete(builder.length() - 2, builder.length());
		
		return builder.toString();
	}
	
	/**
	 * Creates a count projected query from the given original query.
	 *
	 * @param originalQuery
	 *            must not be {@literal null} or empty.
	 * @return
	 */
	public static String createCountQueryFor(String originalQuery) {
		return QueryUtils.createCountQueryFor(originalQuery, null);
	}
	
	/**
	 * Creates a count projected query from the given original query.
	 *
	 * @param originalQuery
	 *            must not be {@literal null}.
	 * @param countProjection
	 *            may be {@literal null}.
	 * @return
	 * @since 1.6
	 */
	public static String createCountQueryFor(String originalQuery, String countProjection) {
		
		Assert.hasText(originalQuery, "OriginalQuery must not be null or empty!");
		
		Matcher matcher = QueryUtils.COUNT_MATCH.matcher(originalQuery);
		String countQuery = null;
		
		if (countProjection == null) {
			
			String variable = matcher.matches() ? matcher.group(QueryUtils.VARIABLE_NAME_GROUP_INDEX) : null;
			boolean useVariable = variable != null && StringUtils.hasText(variable) && !variable.startsWith("new") && !variable.startsWith("count(") && !variable.contains(",");
			
			String replacement = useVariable ? QueryUtils.SIMPLE_COUNT_VALUE : QueryUtils.COMPLEX_COUNT_VALUE;
			countQuery = matcher.replaceFirst(String.format(QueryUtils.COUNT_REPLACEMENT_TEMPLATE, replacement));
		} else {
			countQuery = matcher.replaceFirst(String.format(QueryUtils.COUNT_REPLACEMENT_TEMPLATE, countProjection));
		}
		
		return countQuery.replaceFirst(QueryUtils.ORDER_BY_PART, "");
	}
	
	/**
	 * Resolves the alias for the entity to be retrieved from the given JPA query.
	 *
	 * @param query
	 * @return
	 */
	public static String detectAlias(String query) {
		
		Matcher matcher = QueryUtils.ALIAS_MATCH.matcher(query);
		return matcher.find() ? matcher.group(2) : null;
	}
	
	/**
	 * Resolves the table name for the entity to be retrieved from the given JPA query.
	 *
	 * @param query
	 * @return
	 */
	public static String detectTableName(String query) {
		
		Matcher matcher = QueryUtils.TABLE_MATCH.matcher(query);
		return matcher.find() ? matcher.group(1) : null;
	}
	
	/**
	 * Resolves the table name for the entity to be retrieved from the given JPA query.
	 *
	 * @param query
	 * @return
	 */
	
	public static String detectTableOrAlias(String query) {
		
		Matcher matcher = QueryUtils.ALIAS_MATCH.matcher(query);
		
		String keyword = null;
		if (matcher.find()) {
			keyword = matcher.group(2);
			// log.debug(" keyword: {}", keyword);
			
		} else {
			matcher = QueryUtils.TABLE_MATCH.matcher(query);
			if (matcher.find()) {
				keyword = matcher.group(1);
			}
		}
		return keyword;
	}
	
	/**
	 * Returns the query string to execute an exists query for the given id attributes.
	 *
	 * @param entityName
	 *            the name of the entity to create the query for, must not be {@literal null}.
	 * @param countQueryPlaceHolder
	 *            the placeholder for the count clause, must not be {@literal null}.
	 * @param idAttributes
	 *            the id attributes for the entity, must not be {@literal null}.
	 * @return
	 */
	public static String getExistsQueryString(String entityName, String countQueryPlaceHolder,
			Iterable<String> idAttributes) {
		
		StringBuilder sb = new StringBuilder(String.format(QueryUtils.COUNT_QUERY_STRING, countQueryPlaceHolder, entityName));
		sb.append(" WHERE ");
		
		for (String idAttribute : idAttributes) {
			sb.append(String.format(QueryUtils.EQUALS_CONDITION_STRING, "x", idAttribute, idAttribute));
			sb.append(" AND ");
		}
		
		sb.append("1 = 1");
		return sb.toString();
	}
	
	/**
	 * Returns an existing join for the given attribute if one already exists or creates a new one if not.
	 *
	 * @param from
	 *            the {@link From} to get the current joins from.
	 * @param attribute
	 *            the {@link Attribute} to look for in the current joins.
	 * @return will never be {@literal null}.
	 */
	/*
	 * private static Join<?, ?> getOrCreateJoin(From<?, ?> from, String attribute) {
	 *
	 * for (Join<?, ?> join : from.getJoins()) {
	 *
	 * boolean sameName = join.getAttribute().getName().equals(attribute);
	 *
	 * if (sameName && join.getJoinType().equals(JoinType.LEFT)) { return join; } }
	 *
	 * return from.join(attribute, JoinType.LEFT); }
	 */
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
		
		return String.format("%s %s", wrapped, QueryUtils.toJpaDirection(order));
	}
	
	/**
	 * Returns the aliases used for {@code left (outer) join}s.
	 *
	 * @param query
	 * @return
	 */
	static Set<String> getOuterJoinAliases(String query) {
		
		Set<String> result = new HashSet<String>();
		Matcher matcher = QueryUtils.JOIN_PATTERN.matcher(query);
		
		while (matcher.find()) {
			
			String alias = matcher.group(QueryUtils.QUERY_JOIN_ALIAS_GROUP_INDEX);
			if (StringUtils.hasText(alias)) {
				result.add(alias);
			}
		}
		
		return result;
	}
	
	/**
	 * Returns the query string for the given class name.
	 *
	 * @param template
	 * @param entityName
	 * @return
	 */
	public static String getQueryString(String template, String entityName) {
		
		Assert.hasText(entityName, "Entity name must not be null or empty!");
		
		return String.format(template, entityName);
	}
	
	/**
	 * Returns whether the given {@link Query} contains named parameters.
	 *
	 * @param query
	 * @return
	 */
	/*
	 * public static boolean hasNamedParameter(Query query) {
	 *
	 * for (Parameter<?> parameter : query.getParameters()) { if (parameter.getName() != null) { return true; } }
	 *
	 * return false; }
	 */
	/**
	 * Returns whether the given query contains named parameters.
	 *
	 * @param query
	 *            can be {@literal null} or empty.
	 * @return
	 */
	public static boolean hasNamedParameter(String query) {
		return StringUtils.hasText(query) && QueryUtils.NAMED_PARAMETER.matcher(query).find();
	}
	
	/**
	 * Returns whether the given {@code propertyPathModel} requires the creation of a join. This is the case if we find a non-optional association.
	 *
	 * @param propertyPathModel
	 *            must not be {@literal null}.
	 * @param for
	 * @return
	 */
	/*
	 * private static boolean requiresJoin(Bindable<?> propertyPathModel, boolean forPluralAttribute) {
	 *
	 * if (propertyPathModel == null && forPluralAttribute) { return true; }
	 *
	 * if (!(propertyPathModel instanceof Attribute)) { return false; }
	 *
	 * Attribute<?, ?> attribute = (Attribute<?, ?>) propertyPathModel;
	 *
	 * if (!ASSOCIATION_TYPES.containsKey(attribute.getPersistentAttributeType())) { return false; }
	 *
	 * Class<? extends Annotation> associationAnnotation = ASSOCIATION_TYPES.get(attribute.getPersistentAttributeType());
	 *
	 * if (associationAnnotation == null) { return true; }
	 *
	 * Member member = attribute.getJavaMember();
	 *
	 * if (!(member instanceof AnnotatedElement)) { return true; }
	 *
	 * Annotation annotation = AnnotationUtils.getAnnotation((AnnotatedElement) member, associationAnnotation); return annotation == null ? true : (Boolean) AnnotationUtils.getValue(annotation, "optional"); }
	 */
	/*
	 * @SuppressWarnings("unchecked") static <T> Expression<T> toExpressionRecursively(From<?, ?> from, PropertyPath property) {
	 *
	 * Bindable<?> propertyPathModel = null; Bindable<?> model = from.getModel(); String segment = property.getSegment();
	 *
	 * if (model instanceof ManagedType) {
	 *
	 *
	 * Required to keep support for EclipseLink 2.4.x. TODO: Remove once we drop that (probably Dijkstra M1) See: https://bugs.eclipse.org/bugs/show_bug.cgi?id=413892
	 *
	 * propertyPathModel = (Bindable<?>) ((ManagedType<?>) model).getAttribute(segment); } else { propertyPathModel = from.get(segment).getModel(); }
	 *
	 * if (requiresJoin(propertyPathModel, model instanceof PluralAttribute) && !isAlreadyFetched(from, segment)) { Join<?, ?> join = getOrCreateJoin(from, segment); return (Expression<T>) (property.hasNext() ? toExpressionRecursively(join, property.next()) : join); } else { Path<Object> path = from.get(segment); return (Expression<T>) (property.hasNext() ? toExpressionRecursively(path, property.next()) : path); } }
	 */
	/*
	 * static Expression<Object> toExpressionRecursively(Path<Object> path, PropertyPath property) {
	 *
	 * Path<Object> result = path.get(property.getSegment()); return property.hasNext() ? toExpressionRecursively(result, property.next()) : result; }
	 */
	private static String toJpaDirection(Order order) {
		return order.getDirection().name().toLowerCase(Locale.US);
	}
	
	// private final Logger log = LoggerFactory.getLogger(QueryUtils.class);
	
	/**
	 * Private constructor to prevent instantiation.
	 */
	private QueryUtils() {
	}
	
	/**
	 * Return whether the given {@link From} contains a fetch declaration for the attribute with the given name.
	 *
	 * @param from
	 *            the {@link From} to check for fetches.
	 * @param attribute
	 *            the attribute name to check.
	 * @return
	 */
	/*
	 * private static boolean isAlreadyFetched(From<?, ?> from, String attribute) {
	 *
	 * for (Fetch<?, ?> f : from.getFetches()) {
	 *
	 * boolean sameName = f.getAttribute().getName().equals(attribute);
	 *
	 * if (sameName && f.getJoinType().equals(JoinType.LEFT)) { return true; } }
	 *
	 * return false; }
	 */
	
	/**
	 * Creates a criteria API {@link javax.persistence.criteria.Order} from the given {@link Order}.
	 *
	 * @param order
	 *            the order to transform into a JPA {@link javax.persistence.criteria.Order}
	 * @param root
	 *            the {@link Root} the {@link Order} expression is based on
	 * @param cb
	 *            the {@link CriteriaBuilder} to build the {@link javax.persistence.criteria.Order} with
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked") private static javax.persistence.criteria.Order toJpaOrder(Order order, Root<?> root, CriteriaBuilder cb) {
	 *
	 * PropertyPath property = PropertyPath.from(order.getProperty(), root.getJavaType()); Expression<?> expression = toExpressionRecursively(root, property);
	 *
	 * if (order.isIgnoreCase() && String.class.equals(expression.getJavaType())) { Expression<String> lower = cb.lower((Expression<String>) expression); return order.isAscending() ? cb.asc(lower) : cb.desc(lower); } else { return order.isAscending() ? cb.asc(expression) : cb.desc(expression); } }
	 */
	
	/**
	 * Turns the given {@link Sort} into {@link javax.persistence.criteria.Order}s.
	 *
	 * @param sort
	 *            the {@link Sort} instance to be transformed into JPA {@link javax.persistence.criteria.Order}s.
	 * @param root
	 *            must not be {@literal null}.
	 * @param cb
	 *            must not be {@literal null}.
	 * @return
	 */
	/*
	 * public static List<javax.persistence.criteria.Order> toOrders(Sort sort, Root<?> root, CriteriaBuilder cb) {
	 *
	 * List<javax.persistence.criteria.Order> orders = new ArrayList<javax.persistence.criteria.Order>();
	 *
	 * if (sort == null) { return orders; }
	 *
	 * Assert.notNull(root); Assert.notNull(cb);
	 *
	 * for (org.springframework.data.domain.Sort.Order order : sort) { orders.add(toJpaOrder(order, root, cb)); }
	 *
	 * return orders; }
	 */
	
}
