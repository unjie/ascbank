/**
 *
 */
package com.ascbank.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import com.ascbank.model.User;

/**
 * @author jie
 *
 */
public interface UserController {
	
	String exit(HttpServletRequest request);
	
	String login(HttpSession session, User user, BindingResult br);
	
	String register(HttpSession session, User user, BindingResult br);
	
}
