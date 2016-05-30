/**
 * 
 */
package com.ascbank.user.entity;

import java.io.Serializable;

/**
 * @author jie
 *
 */
public class RolePermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3342746239540220117L;
	private Long roleId;
	private Long permission;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getPermission() {
		return permission;
	}
	public void setPermission(Long permission) {
		this.permission = permission;
	}
	@Override
	public String toString() {
		return "RolePermission [roleId=" + roleId + ", permission=" + permission + "]";
	}
	
}
