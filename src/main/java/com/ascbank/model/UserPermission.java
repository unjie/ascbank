package com.ascbank.model;

public class UserPermission {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user_permission.permission_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Long	permissionId;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user_permission.user_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Long	userId;
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_permission.permission_id
	 *
	 * @return the value of user_permission.permission_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Long getPermissionId() {
		return permissionId;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_permission.user_id
	 *
	 * @return the value of user_permission.user_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_permission.permission_id
	 *
	 * @param permissionId
	 *            the value for user_permission.permission_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_permission.user_id
	 *
	 * @param userId
	 *            the value for user_permission.user_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}