/**
 *
 */
package com.ascbank.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import com.ascbank.model.User;
import com.ascbank.web.basis.BaseInterfaceController;

/**
 * @author jie
 *
 */
public interface UserController<E extends User, O extends User> extends BaseInterfaceController<Long, User> {
	
	String exit(HttpServletRequest request);
	
	String login(E user, BindingResult br);
	
	String register(O user, BindingResult br);

}
