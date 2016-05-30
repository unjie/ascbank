/**
 * 
 */
package com.ascbank.user.entity;

import java.io.Serializable;

/**
 * @author jie
 *
 */
public class UserRole implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3309272395201967050L;
	private Long userid;
	private Long roleid;
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getRoleid() {
		return roleid;
	}
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	@Override
	public String toString() {
		return "UserRole [userid=" + userid + ", roleid=" + roleid + "]";
	}
	
	
}
