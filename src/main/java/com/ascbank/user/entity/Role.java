/**
 * 
 */
package com.ascbank.user.entity;

import java.io.Serializable;

/**
 * @author jie
 *
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 1709215080460046448L;
	private long roleid;
	private String rolename;
	private String Info;
	
	
	public long getRoleid() {
		return roleid;
	}
	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getInfo() {
		return Info;
	}
	public void setInfo(String info) {
		Info = info;
	}
	@Override
	public String toString() {
		return "Role [roleid=" + roleid + ", rolename=" + rolename + ", Info=" + Info + "]";
	}

	
	
}
