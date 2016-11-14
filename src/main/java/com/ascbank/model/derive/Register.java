/**
 *
 */
package com.ascbank.model.derive;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import com.ascbank.verify.CaptchaCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author jie
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class Register extends Login {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 8952853064094048512L;
	
	@Transient
	@NotEmpty(groups = { CaptchaCheck.class })
	private transient String	respassword;
	
	public String getRespassword() {
		return respassword;
	}
	
	public void setRespassword(String respassword) {
		this.respassword = respassword;
	}
	
}
