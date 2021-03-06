package com.ascbank.model;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ascbank.model.base.PKEntity;
import com.ascbank.model.derive.FileType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class FileInfo extends PKEntity<Long> implements Cloneable {
	private static final long	serialVersionUID	= -7136571190981433529L;
	
	private String				content;
	
	private Date				date;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_file.id
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	private Long				id;
	/**
	 *
	 */
	private final Logger		log					= LoggerFactory.getLogger(FileInfo.class);
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_file.md5
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	private String				md5;
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_file.id
	 *
	 * @return the value of t_file.id
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	
	private String				name;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_file.path
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	private String				path;
	
	private Permission			perms;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_file.premission
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	private String				premission;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_file.size
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	private Long				size;
	
	private FileType			type;
	
	public FileInfo() {
		super();
	}
	
	
	@Override
	public FileInfo clone() {
		FileInfo o = null;
		try {
			o = (FileInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getDate() {
		return date;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_file.md5
	 *
	 * @return the value of t_file.md5
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	public String getMd5() {
		return md5;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_file.path
	 *
	 * @return the value of t_file.path
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	public String getPath() {
		return path;
	}
	
	public Permission getPerms() {
		return perms;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_file.premission
	 *
	 * @return the value of t_file.premission
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	public String getPremission() {
		return premission;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_file.size
	 *
	 * @return the value of t_file.size
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	public Long getSize() {
		return size;
	}
	
	public FileType getType() {
		return type;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_file.id
	 *
	 * @param id
	 *            the value for t_file.id
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_file.md5
	 *
	 * @param md5
	 *            the value for t_file.md5
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	public void setMd5(String md5) {
		this.md5 = md5 == null ? null : md5.trim();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_file.path
	 *
	 * @param path
	 *            the value for t_file.path
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}
	
	public void setPerms(Permission perms) {
		this.perms = perms;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_file.premission
	 *
	 * @param premission
	 *            the value for t_file.premission
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	public void setPremission(String premission) {
		this.premission = premission == null ? null : premission.trim();
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_file.size
	 *
	 * @param size
	 *            the value for t_file.size
	 *
	 * @mbggenerated Tue Oct 18 14:02:37 CST 2016
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	public void setType(FileType type) {
		this.type = type;
	}
}