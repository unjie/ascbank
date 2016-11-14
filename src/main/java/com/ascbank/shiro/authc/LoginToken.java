/**
 *
 */
package com.ascbank.shiro.authc;

import java.util.Arrays;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.ascbank.model.derive.Login;

/**
 * @author jie
 *
 */
public class LoginToken<E extends Login> extends UsernamePasswordToken {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 6617529955078763783L;

	private Login				Login;

	public LoginToken(E user) {
		super(user.getUsername(), user.getPassword(), user.getSave());
		this.Login = user;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the Login
	 */
	public Login getLogin() {
		return Login;
	}

	/**
	 * @param Login
	 *            the Login to set
	 */
	public void setLogin(E Login) {
		setUsername(Login.getUsername());
		setPassword(Login.getPassword().toCharArray());
		setRememberMe(Login.getSave());
		this.Login = Login;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginToken [Login=" + Login + ", getUsername()=" + getUsername() + ", getPassword()=" + Arrays.toString(getPassword()) + ", getPrincipal()=" + getPrincipal() + ", getCredentials()=" + getCredentials() + ", getHost()=" + getHost() + ", isRememberMe()=" + isRememberMe() + "]";
	}

}
