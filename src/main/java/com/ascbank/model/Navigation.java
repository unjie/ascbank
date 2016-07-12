package com.ascbank.model;

import com.ascbank.model.base.PKEntity;

public class Navigation extends PKEntity<Long> {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.alias
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private String alias;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.description
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private String description;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.is_publish
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private Boolean isPublish;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.keyword
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private String keyword;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.sort
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private Short sort;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.stem
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private String stem;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.style
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private String style;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.thumb
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private String thumb;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.title
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private String title;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.url
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private String url;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column navigation.article_id
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	private Long articleId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.alias
	 *
	 * @return the value of navigation.alias
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.alias
	 *
	 * @param alias
	 *            the value for navigation.alias
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setAlias(String alias) {
		this.alias = alias == null ? null : alias.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.description
	 *
	 * @return the value of navigation.description
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.description
	 *
	 * @param description
	 *            the value for navigation.description
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.is_publish
	 *
	 * @return the value of navigation.is_publish
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public Boolean getIsPublish() {
		return isPublish;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.is_publish
	 *
	 * @param isPublish
	 *            the value for navigation.is_publish
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.keyword
	 *
	 * @return the value of navigation.keyword
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.keyword
	 *
	 * @param keyword
	 *            the value for navigation.keyword
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.sort
	 *
	 * @return the value of navigation.sort
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public Short getSort() {
		return sort;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.sort
	 *
	 * @param sort
	 *            the value for navigation.sort
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setSort(Short sort) {
		this.sort = sort;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.stem
	 *
	 * @return the value of navigation.stem
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public String getStem() {
		return stem;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.stem
	 *
	 * @param stem
	 *            the value for navigation.stem
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setStem(String stem) {
		this.stem = stem == null ? null : stem.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.style
	 *
	 * @return the value of navigation.style
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.style
	 *
	 * @param style
	 *            the value for navigation.style
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setStyle(String style) {
		this.style = style == null ? null : style.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.thumb
	 *
	 * @return the value of navigation.thumb
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public String getThumb() {
		return thumb;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.thumb
	 *
	 * @param thumb
	 *            the value for navigation.thumb
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb == null ? null : thumb.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.title
	 *
	 * @return the value of navigation.title
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.title
	 *
	 * @param title
	 *            the value for navigation.title
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.url
	 *
	 * @return the value of navigation.url
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.url
	 *
	 * @param url
	 *            the value for navigation.url
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column navigation.article_id
	 *
	 * @return the value of navigation.article_id
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column navigation.article_id
	 *
	 * @param articleId
	 *            the value for navigation.article_id
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
}