/**
 * 
 */
package com.ascbank.user.entity;

import java.io.Serializable;

/**
 * @author jie
 *
 */
public class Permission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 326003134956351288L;
	private Long permissionId;
	private String permissionName;
	private String entitys;
	private String type;
	private String ids;
	
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getEntitys() {
		return entitys;
	}
	public void setEntitys(String entitys) {
		this.entitys = entitys;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Override
	public String toString() {
		return "Permission [permissionId=" + permissionId + ", permissionName=" + permissionName + ", entitys="
				+ entitys + ", type=" + type + ", ids=" + ids + "]";
	}
	
	
	
}
