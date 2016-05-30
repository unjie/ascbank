package com.ascbank.user.entity;

import java.io.Serializable;


/**
 * @author jie
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 3824245012971337839L;
	private Long userid;
	private String username;
	private String password;
	private String salt;
	private String phone;
	private String email;
	
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ ", phone=" + phone + ", email=" + email + "]";
	}


	
	
	

	


}
