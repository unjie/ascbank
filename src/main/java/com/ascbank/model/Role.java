package com.ascbank.model;

import java.util.List;

import com.ascbank.model.base.PKEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Role extends PKEntity<Long> {
	/**
	 *
	 */
	private static final long	serialVersionUID	= -99754940960858343L;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column role.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				description;

	@JsonIgnore
	private List<Permission>	permissions;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column role.role_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				roleName;

	public Role() {
		super();
	}

	public Role(Long id, String description, String roleName) {
		super();
		this.setId(id);
		this.description = description;
		this.roleName = roleName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column role.description
	 *
	 * @return the value of role.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getDescription() {
		return description;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column role.role_name
	 *
	 * @return the value of role.role_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column role.description
	 *
	 * @param description
	 *            the value for role.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column role.role_name
	 *
	 * @param roleName
	 *            the value for role.role_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	@Override
	public String toString() {
		return "Role [description=" + description + ", permissions=" + permissions + ", roleName=" + roleName + ", Id=" + getId() + "]";
	}

}