package com.ascbank.model;

import java.util.Date;
import java.util.List;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.avatar
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String avatar;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.captcha
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String captcha;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.description
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.email
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.encrypt
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String encrypt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.is_online
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private Boolean isOnline;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.lastloginip
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String lastloginip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.lastlogintime
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private Date lastlogintime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.phone
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private Long phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.real_name
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String realName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.regtime
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private Date regtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.save
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private Boolean save;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.username
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.wechat
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String wechat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.wechat_name
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    private String wechatName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.avatar
     *
     * @return the value of user.avatar
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.avatar
     *
     * @param avatar the value for user.avatar
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.captcha
     *
     * @return the value of user.captcha
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.captcha
     *
     * @param captcha the value for user.captcha
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha == null ? null : captcha.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.description
     *
     * @return the value of user.description
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.description
     *
     * @param description the value for user.description
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.email
     *
     * @return the value of user.email
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.email
     *
     * @param email the value for user.email
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.encrypt
     *
     * @return the value of user.encrypt
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getEncrypt() {
        return encrypt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.encrypt
     *
     * @param encrypt the value for user.encrypt
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt == null ? null : encrypt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.is_online
     *
     * @return the value of user.is_online
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public Boolean getIsOnline() {
        return isOnline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.is_online
     *
     * @param isOnline the value for user.is_online
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.lastloginip
     *
     * @return the value of user.lastloginip
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getLastloginip() {
        return lastloginip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.lastloginip
     *
     * @param lastloginip the value for user.lastloginip
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip == null ? null : lastloginip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.lastlogintime
     *
     * @return the value of user.lastlogintime
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public Date getLastlogintime() {
        return lastlogintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.lastlogintime
     *
     * @param lastlogintime the value for user.lastlogintime
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password
     *
     * @return the value of user.password
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password
     *
     * @param password the value for user.password
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.phone
     *
     * @return the value of user.phone
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public Long getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.phone
     *
     * @param phone the value for user.phone
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setPhone(Long phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.real_name
     *
     * @return the value of user.real_name
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.real_name
     *
     * @param realName the value for user.real_name
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.regtime
     *
     * @return the value of user.regtime
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public Date getRegtime() {
        return regtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.regtime
     *
     * @param regtime the value for user.regtime
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.save
     *
     * @return the value of user.save
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public Boolean getSave() {
        return save;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.save
     *
     * @param save the value for user.save
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setSave(Boolean save) {
        this.save = save;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.username
     *
     * @return the value of user.username
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.username
     *
     * @param username the value for user.username
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.wechat
     *
     * @return the value of user.wechat
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.wechat
     *
     * @param wechat the value for user.wechat
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.wechat_name
     *
     * @return the value of user.wechat_name
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public String getWechatName() {
        return wechatName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.wechat_name
     *
     * @param wechatName the value for user.wechat_name
     *
     * @mbggenerated Tue Jun 07 15:09:14 CST 2016
     */
    public void setWechatName(String wechatName) {
        this.wechatName = wechatName == null ? null : wechatName.trim();
    }

	public List<Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}
}