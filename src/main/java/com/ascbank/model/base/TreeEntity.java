package com.ascbank.model.base;

import java.io.Serializable;
import java.util.List;

public abstract class TreeEntity<T extends Serializable, E extends PKEntity<T>> extends PKEntity<T> implements TreeEntityInterface<T> {
	/**
	 *
	 */
	private static final long	serialVersionUID	= -2013556186137945488L;

	private List<E>				children;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column menu.parent_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private T					parentId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column menu.stem
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				stem;

	public List<E> getChildren() {
		return children;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column menu.parent_id
	 *
	 * @return the value of menu.parent_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	public T getParentId() {
		return parentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column menu.stem
	 *
	 * @return the value of menu.stem
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	public String getStem() {
		return stem;
	}

	public void setChildren(List<E> children) {
		this.children = children;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column menu.parent_id
	 *
	 * @param parentId
	 *            the value for menu.parent_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setParentId(T parentId) {
		this.parentId = parentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column menu.stem
	 *
	 * @param stem
	 *            the value for menu.stem
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setStem(String stem) {
		this.stem = stem == null ? null : stem.trim();
	}
}
