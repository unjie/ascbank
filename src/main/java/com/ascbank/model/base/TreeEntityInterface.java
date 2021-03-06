package com.ascbank.model.base;

import java.io.Serializable;

public interface TreeEntityInterface<S extends Serializable> {

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column menu.parent_id
	 *
	 * @return the value of menu.parent_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public S getParentId();

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column menu.stem
	 *
	 * @return the value of menu.stem
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getStem();

}
