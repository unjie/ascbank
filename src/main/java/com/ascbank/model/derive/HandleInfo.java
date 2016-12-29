/**
 *
 */
package com.ascbank.model.derive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author jie
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class HandleInfo {
	// private String compressedFilename;
	private String		content;
	private String		destination;
	// private String folderName;
	private String[]	items;
	//private String		newPath;
	private String		path;
	
	private String		perms;
	
	private String		permsCode;
	private Boolean		recursive;
	private String		toFilename;
	
	public String getContent() {
		return content;
	}
	
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	
	public String[] getItems() {
		return items;
	}
	
	
	
	public String getPath() {
		return path;
	}
	
	/**
	 * @return the perms
	 */
	public String getPerms() {
		return perms;
	}
	
	/**
	 * @return the permsCode
	 */
	public String getPermsCode() {
		return permsCode;
	}
	
	/**
	 * @return the recursive
	 */
	public Boolean getRecursive() {
		return recursive;
	}
	
	/**
	 * @return the toFilename
	 */
	public String getToFilename() {
		return toFilename;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setItems(String[] items) {
		this.items = items;
	}
	

	
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * @param perms
	 *            the perms to set
	 */
	public void setPerms(String perms) {
		this.perms = perms;
	}
	
	/**
	 * @param permsCode
	 *            the permsCode to set
	 */
	public void setPermsCode(String permsCode) {
		this.permsCode = permsCode;
	}
	
	/**
	 * @param recursive
	 *            the recursive to set
	 */
	public void setRecursive(Boolean recursive) {
		this.recursive = recursive;
	}
	
	/**
	 * @param toFilename
	 *            the toFilename to set
	 */
	public void setToFilename(String toFilename) {
		this.toFilename = toFilename;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HandleInfo [ content=" + content + ", destination=" + destination + ", items=" + items + ", path=" + path + ", perms=" + perms + ", permsCode=" + permsCode + ", recursive=" + recursive + ", toFilename=" + toFilename + "]";
	}
	
}
