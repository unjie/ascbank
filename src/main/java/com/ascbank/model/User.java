package com.ascbank.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.data.annotation.Transient;

import com.ascbank.model.base.CaptchaValidetorModel;
import com.ascbank.model.base.PKEntity;
import com.ascbank.validator.annotation.Captcha;
import com.ascbank.verify.AddCheck;
import com.ascbank.verify.LoginCheck;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User extends PKEntity<Long> implements CaptchaValidetorModel {
	
	private static final long	serialVersionUID	= 2814743973946334631L;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.avatar
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	
	private String				avatar;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.captcha
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Captcha(key = com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, groups = { LoginCheck.class, AddCheck.class }, message = "验证码错误")
	@Transient
	@JsonIgnore
	public transient String		captcha;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016 This whitelist allows a full range of text and structural body HTML: a, b, blockquote, br, caption, cite, code, col, colgroup, dd, div, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, span, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul
	 */
	@Length(max = 360)
	@SafeHtml(whitelistType = WhiteListType.RELAXED, message = "请勿尝试非法操作")
	private String				description;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.email
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Email(message = "Email 错误", groups = { AddCheck.class })
	private String				email;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.encrypt
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				encrypt;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.is_online
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Boolean				isOnline;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.lastloginip
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				lastloginip;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.lastlogintime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Date				lastlogintime;
	
	@Length(min = 6, max = 18, message = "密码必须为6-18位", groups = { LoginCheck.class, AddCheck.class })
	private String				password;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.password
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@JsonIgnore
	private List<Permission>	permissions;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.phone
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Range(max = 18999999999L, min = 13000000000L, message = "请输入正确的手机号码", groups = { AddCheck.class })
	private Long				phone;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.real_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Pattern(regexp = "([\u4E00-\u9FFF]){2,8}|([A-Za-z]){2,16}", message = "真实名称只能为文字2~8个或字母2-16个", groups = { AddCheck.class })
	private String				realName;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.regtime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private Date				regtime;
	
	@JsonIgnore
	private List<Role>			roles;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.save
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@JsonIgnore
	@Transient
	private transient Boolean	save				= false;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.username
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Pattern(regexp = "([\u4e00-\u9FFF]|\\w){3,18}", message = "用户名文字与字母数字及_(下划线),且长度为3-18个", groups = { LoginCheck.class, AddCheck.class })
	private String				username;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.wechat
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				wechat;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user.wechat_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	private String				wechatName;
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.avatar
	 *
	 * @return the value of user.avatar
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getAvatar() {
		return avatar;
	}
	
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
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.description
	 *
	 * @return the value of user.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.email
	 *
	 * @return the value of user.email
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.encrypt
	 *
	 * @return the value of user.encrypt
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getEncrypt() {
		return encrypt;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.is_online
	 *
	 * @return the value of user.is_online
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Boolean getIsOnline() {
		return isOnline;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.lastloginip
	 *
	 * @return the value of user.lastloginip
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getLastloginip() {
		return lastloginip;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.lastlogintime
	 *
	 * @return the value of user.lastlogintime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Date getLastlogintime() {
		return lastlogintime;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.password
	 *
	 * @return the value of user.password
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getPassword() {
		return password;
	}
	
	public List<Permission> getPermissions() {
		return permissions;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.phone
	 *
	 * @return the value of user.phone
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Long getPhone() {
		return phone;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.real_name
	 *
	 * @return the value of user.real_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getRealName() {
		return realName;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.regtime
	 *
	 * @return the value of user.regtime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public Date getRegtime() {
		return regtime;
	}
	
	public List<Role> getRoles() {
		return roles;
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
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.username
	 *
	 * @return the value of user.username
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.wechat
	 *
	 * @return the value of user.wechat
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getWechat() {
		return wechat;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user.wechat_name
	 *
	 * @return the value of user.wechat_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public String getWechatName() {
		return wechatName;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.avatar
	 *
	 * @param avatar
	 *            the value for user.avatar
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar == null ? null : avatar.trim();
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
		this.captcha = captcha == null ? null : captcha.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.description
	 *
	 * @param description
	 *            the value for user.description
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.email
	 *
	 * @param email
	 *            the value for user.email
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.encrypt
	 *
	 * @param encrypt
	 *            the value for user.encrypt
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt == null ? null : encrypt.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.is_online
	 *
	 * @param isOnline
	 *            the value for user.is_online
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.lastloginip
	 *
	 * @param lastloginip
	 *            the value for user.lastloginip
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip == null ? null : lastloginip.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.lastlogintime
	 *
	 * @param lastlogintime
	 *            the value for user.lastlogintime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.password
	 *
	 * @param password
	 *            the value for user.password
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}
	
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.phone
	 *
	 * @param phone
	 *            the value for user.phone
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.real_name
	 *
	 * @param realName
	 *            the value for user.real_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.regtime
	 *
	 * @param regtime
	 *            the value for user.regtime
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.username
	 *
	 * @param username
	 *            the value for user.username
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.wechat
	 *
	 * @param wechat
	 *            the value for user.wechat
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat == null ? null : wechat.trim();
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user.wechat_name
	 *
	 * @param wechatName
	 *            the value for user.wechat_name
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName == null ? null : wechatName.trim();
	}
	
	@Override
	public String toString() {
		return "User [avatar=" + avatar + ", captcha=" + captcha + ", description=" + description + ", email=" + email + ", encrypt=" + encrypt + ", isOnline=" + isOnline + ", lastloginip=" + lastloginip + ", lastlogintime=" + lastlogintime + ", password=" + password + ", phone=" + phone + ", realName=" + realName + ", regtime=" + regtime + ", roles=" + roles + ", save=" + save + ", username=" + username + ", wechat=" + wechat + ", wechatName=" + wechatName + ", Id=" + getId() + "]";
	}
	
}