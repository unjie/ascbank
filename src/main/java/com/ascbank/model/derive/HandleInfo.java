/**
 *
 */
package com.ascbank.model.derive;

/**
 * @author jie
 *
 */
public class HandleInfo {
	private String		compressedFilename;
	private String		content;
	private String		destination;
	private String		folderName;
	private String[]	items;
	private String		newPath;
	private String		path;

	private String		perms;

	private Integer		permsCode;
	private Boolean		recursive;
	private String		toFilename;
	
	/**
	 * @return the compressedFilename
	 */
	public String getCompressedFilename() {
		return compressedFilename;
	}
	
	public String getContent() {
		return content;
	}
	
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	
	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}
	
	public String[] getItems() {
		return items;
	}
	
	public String getNewPath() {
		return newPath;
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
	public Integer getPermsCode() {
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
	
	/**
	 * @param compressedFilename
	 *            the compressedFilename to set
	 */
	public void setCompressedFilename(String compressedFilename) {
		this.compressedFilename = compressedFilename;
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
	
	/**
	 * @param folderName
	 *            the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public void setItems(String[] items) {
		this.items = items;
	}
	
	public void setNewPath(String newPath) {
		this.newPath = newPath;
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
	public void setPermsCode(Integer permsCode) {
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
		return "HandleInfo [compressedFilename=" + compressedFilename + ", content=" + content + ", destination=" + destination + ", folderName=" + folderName + ", items=" + items + ", newPath=" + newPath + ", path=" + path + ", perms=" + perms + ", permsCode=" + permsCode + ", recursive=" + recursive + ", toFilename=" + toFilename + "]";
	}

}
