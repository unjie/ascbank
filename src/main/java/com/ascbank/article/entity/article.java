/**
 * 
 */
package com.ascbank.article.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jie
 *
 */
public class article implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8976834829776739786L;
	
	private Long articleId;
	private String title;
	private String keyword;
	private String descript;
	private String author;
	private String fulltext;
	private Date createData;
	private Date updataDate;
	private String outline;
	public Long getArticleId() {
		return articleId;
	}
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getFulltext() {
		return fulltext;
	}
	public void setFulltext(String fulltext) {
		this.fulltext = fulltext;
	}
	public Date getCreateData() {
		return createData;
	}
	public void setCreateData(Date createData) {
		this.createData = createData;
	}
	public Date getUpdataDate() {
		return updataDate;
	}
	public void setUpdataDate(Date updataDate) {
		this.updataDate = updataDate;
	}
	public String getOutline() {
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	@Override
	public String toString() {
		return "article [articleId=" + articleId + ", title=" + title + ", keyword=" + keyword + ", descript="
				+ descript + ", author=" + author + ", fulltext=" + fulltext + ", createData=" + createData
				+ ", updataDate=" + updataDate + ", outline=" + outline + "]";
	}
	
	
	
	

}
