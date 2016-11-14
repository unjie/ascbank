/**
 *
 */
package com.ascbank.myBatis.dialect.pagination;

import org.springframework.data.domain.Pageable;

/**
 * A helper for dealing with LimitHandler implementations
 *
 * @author Lukasz Antoniak (lukasz dot antoniak at gmail dot com)
 */
public class LimitHelper {
	/**
	 * Retrieve the indicated first row for pagination
	 *
	 * @param selection
	 *            The row selection options
	 *
	 * @return The first row
	 */
	public static int getFirstRow(Pageable selection) {
		return selection == null ? 0 : selection.getPageNumber() * selection.getPageSize() + selection.getOffset();
	}

	public static int getMaxRows(Pageable selection) {
		return selection.getPageSize() * (selection.getPageNumber() + 1) + selection.getOffset();
	}

	/**
	 * Is a first row limit indicated?
	 *
	 * @param selection
	 *            The row selection options
	 *
	 * @return Whether a first row limit in indicated
	 */
	public static boolean hasFirstRow(Pageable selection) {
		return LimitHelper.getFirstRow(selection) > 0;
	}

	/**
	 * Is a max row limit indicated?
	 *
	 * @param selection
	 *            The row selection options
	 *
	 * @return Whether a max row limit was indicated
	 */
	public static boolean hasMaxRows(Pageable selection) {
		int maxRows = LimitHelper.getMaxRows(selection);
		return selection != null && maxRows > 0;
	}

	/**
	 * Should limit be applied?
	 *
	 * @param limitHandler
	 *            The limit handler
	 * @param selection
	 *            The row selection
	 *
	 * @return Whether limiting is indicated
	 */
	public static boolean useLimit(LimitHandler limitHandler, Pageable selection) {
		return limitHandler.supportsLimit() && LimitHelper.hasMaxRows(selection);
	}

	private LimitHelper() {
	}
}