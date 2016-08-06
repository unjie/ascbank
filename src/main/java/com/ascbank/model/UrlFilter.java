package com.ascbank.model;

import com.ascbank.model.base.PKEntity;

public class UrlFilter extends PKEntity<Long> {
	/**
	 *
	 */
	private static final long	serialVersionUID	= -1799134367484354066L;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column url_filter.name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				name;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column url_filter.permissions
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				permissions;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column url_filter.roles
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				roles;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column url_filter.sort
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Long				sort;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column url_filter.url
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				url;
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column url_filter.name
	 *
	 * @return the value of url_filter.name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column url_filter.permissions
	 *
	 * @return the value of url_filter.permissions
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getPermissions() {
		return permissions;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column url_filter.roles
	 *
	 * @return the value of url_filter.roles
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getRoles() {
		return roles;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column url_filter.sort
	 *
	 * @return the value of url_filter.sort
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Long getSort() {
		return sort;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column url_filter.url
	 *
	 * @return the value of url_filter.url
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column url_filter.name
	 *
	 * @param name
	 *            the value for url_filter.name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column url_filter.permissions
	 *
	 * @param permissions
	 *            the value for url_filter.permissions
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setPermissions(String permissions) {
		this.permissions = permissions == null ? null : permissions.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column url_filter.roles
	 *
	 * @param roles
	 *            the value for url_filter.roles
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setRoles(String roles) {
		this.roles = roles == null ? null : roles.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column url_filter.sort
	 *
	 * @param sort
	 *            the value for url_filter.sort
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column url_filter.url
	 *
	 * @param url
	 *            the value for url_filter.url
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}
}