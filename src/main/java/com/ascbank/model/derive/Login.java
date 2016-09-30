/**
 *
 */
package com.ascbank.model.derive;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import com.ascbank.model.User;
import com.ascbank.model.base.CaptchaValidetorModel;
import com.ascbank.validator.annotation.Captcha;
import com.ascbank.verify.CaptchaCheck;
import com.google.code.kaptcha.Constants;

/**
 * @author jie
 *
 */
public class Login extends User implements CaptchaValidetorModel {
	/**
	 *
	 */
	private static final long	serialVersionUID	= -8979722259388244815L;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.captcha
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Transient
	// @JsonIgnore
	@NotEmpty(groups = { CaptchaCheck.class })
	@Captcha(key = Constants.KAPTCHA_SESSION_KEY, groups = { CaptchaCheck.class }, message = "验证码错误")
	public transient String		captcha;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.save
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Transient
	// @JsonIgnore
	private transient Boolean	save				= false;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.captcha
	 *
	 * @return the value of user.captcha
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.save
	 *
	 * @return the value of user.save
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Boolean getSave() {
		return save;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.captcha
	 *
	 * @param captcha
	 *            the value for user.captcha
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setCaptcha(String captcha) {
		this.captcha = (captcha == null ? null : captcha.trim());
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.save
	 *
	 * @param save
	 *            the value for user.save
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setSave(Boolean save) {
		this.save = save;
	}
	
	@Override
	public String toString() {
		return "Login [captcha=" + captcha + ", save=" + save + ", getAvatar()=" + getAvatar() + ", getDescription()=" + getDescription() + ", getEmail()=" + getEmail() + ", getEncrypt()=" + getEncrypt() + ", getIsOnline()=" + getIsOnline() + ", getLastloginip()=" + getLastloginip() + ", getLastlogintime()=" + getLastlogintime() + ", getPassword()=" + getPassword() + ", getPermissions()=" + getPermissions() + ", getPhone()=" + getPhone() + ", getRealName()=" + getRealName() + ", getRegtime()=" + getRegtime() + ", getRoles()=" + getRoles() + ", getUsername()=" + getUsername() + ", getWechat()=" + getWechat() + ", getWechatName()=" + getWechatName() + ", toString()=" + super.toString() + ", getId()=" + getId() + ", hashCode()=" + hashCode() + ", isNew()=" + isNew() + "]";
	}
	
}