package com.ascbank.model;

import com.ascbank.model.base.PKEntity;

public class Shortcut extends PKEntity<Long> {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 152351507337870018L;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shortcut.app_class
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				appClass;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shortcut.icon_cls
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				iconCls;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shortcut.module
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				module;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shortcut.name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				name;
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shortcut.app_class
	 *
	 * @return the value of shortcut.app_class
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getAppClass() {
		return appClass;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shortcut.icon_cls
	 *
	 * @return the value of shortcut.icon_cls
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getIconCls() {
		return iconCls;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shortcut.module
	 *
	 * @return the value of shortcut.module
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getModule() {
		return module;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shortcut.name
	 *
	 * @return the value of shortcut.name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shortcut.app_class
	 *
	 * @param appClass
	 *            the value for shortcut.app_class
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setAppClass(String appClass) {
		this.appClass = appClass == null ? null : appClass.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shortcut.icon_cls
	 *
	 * @param iconCls
	 *            the value for shortcut.icon_cls
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls == null ? null : iconCls.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shortcut.module
	 *
	 * @param module
	 *            the value for shortcut.module
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setModule(String module) {
		this.module = module == null ? null : module.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shortcut.name
	 *
	 * @param name
	 *            the value for shortcut.name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
}