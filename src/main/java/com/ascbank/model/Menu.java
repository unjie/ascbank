package com.ascbank.model;

import java.util.Date;

import com.ascbank.model.base.PKEntity;

public class Menu extends PKEntity<Long> {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 3484437899130507705L;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.alias
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				alias;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.author
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				author;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.clicks
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Integer				clicks;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				description;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.edittime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Date				edittime;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.is_navigation
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Boolean				isNavigation;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.is_publish
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Boolean				isPublish;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.keyword
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				keyword;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.parent_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Long				parentId;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.sort
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Short				sort;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.stem
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				stem;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.style
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				style;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.thumb
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				thumb;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.title
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				title;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column menu.url
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				url;
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.alias
	 *
	 * @return the value of menu.alias
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getAlias() {
		return alias;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.author
	 *
	 * @return the value of menu.author
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.clicks
	 *
	 * @return the value of menu.clicks
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Integer getClicks() {
		return clicks;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.description
	 *
	 * @return the value of menu.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.edittime
	 *
	 * @return the value of menu.edittime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Date getEdittime() {
		return edittime;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.is_navigation
	 *
	 * @return the value of menu.is_navigation
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Boolean getIsNavigation() {
		return isNavigation;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.is_publish
	 *
	 * @return the value of menu.is_publish
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Boolean getIsPublish() {
		return isPublish;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.keyword
	 *
	 * @return the value of menu.keyword
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getKeyword() {
		return keyword;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.parent_id
	 *
	 * @return the value of menu.parent_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Long getParentId() {
		return parentId;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.sort
	 *
	 * @return the value of menu.sort
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Short getSort() {
		return sort;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.stem
	 *
	 * @return the value of menu.stem
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getStem() {
		return stem;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.style
	 *
	 * @return the value of menu.style
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getStyle() {
		return style;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.thumb
	 *
	 * @return the value of menu.thumb
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getThumb() {
		return thumb;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.title
	 *
	 * @return the value of menu.title
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column menu.url
	 *
	 * @return the value of menu.url
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.alias
	 *
	 * @param alias
	 *            the value for menu.alias
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setAlias(String alias) {
		this.alias = alias == null ? null : alias.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.author
	 *
	 * @param author
	 *            the value for menu.author
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setAuthor(String author) {
		this.author = author == null ? null : author.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.clicks
	 *
	 * @param clicks
	 *            the value for menu.clicks
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.description
	 *
	 * @param description
	 *            the value for menu.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.edittime
	 *
	 * @param edittime
	 *            the value for menu.edittime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.is_navigation
	 *
	 * @param isNavigation
	 *            the value for menu.is_navigation
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setIsNavigation(Boolean isNavigation) {
		this.isNavigation = isNavigation;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.is_publish
	 *
	 * @param isPublish
	 *            the value for menu.is_publish
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.keyword
	 *
	 * @param keyword
	 *            the value for menu.keyword
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.parent_id
	 *
	 * @param parentId
	 *            the value for menu.parent_id
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.sort
	 *
	 * @param sort
	 *            the value for menu.sort
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setSort(Short sort) {
		this.sort = sort;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.stem
	 *
	 * @param stem
	 *            the value for menu.stem
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setStem(String stem) {
		this.stem = stem == null ? null : stem.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.style
	 *
	 * @param style
	 *            the value for menu.style
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setStyle(String style) {
		this.style = style == null ? null : style.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.thumb
	 *
	 * @param thumb
	 *            the value for menu.thumb
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb == null ? null : thumb.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.title
	 *
	 * @param title
	 *            the value for menu.title
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column menu.url
	 *
	 * @param url
	 *            the value for menu.url
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}
}