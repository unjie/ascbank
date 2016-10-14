/**
 *
 */
package com.ascbank.model.derive;

import java.util.Date;

/**
 * @author jie
 *
 */
public class FileInfo implements Cloneable {
	
	private String			content;
	private Date			date;
	private String			name;
	private String			path;
	private FilePermisssion	perms;
	private long			size;
	private FileType		type;
	
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
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
	public FilePermisssion getPerms() {
		return perms;
	}
	
	public long getSize() {
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setPerms(FilePermisssion perms) {
		this.perms = perms;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	public void setType(FileType type) {
		this.type = type;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileInfo [content=" + content + ", date=" + date + ", name=" + name + ", path=" + path + ", perms=" + perms + ", size=" + size + ", type=" + type + "]";
	}
	
}
