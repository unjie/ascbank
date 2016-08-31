/**
 *
 */
package com.ascbank.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import com.ascbank.verify.CaptchaCheck;

/**
 * @author jie
 *
 */
public class Register extends Login {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 8952853064094048512L;
	
	@NotEmpty(groups = { CaptchaCheck.class })
	@Transient
	private transient String	respassword;
	
	public String getRespassword() {
		return respassword;
	}
	
	public void setRespassword(String respassword) {
		this.respassword = respassword;
	}
	
}
