/**
 *
 */
package com.ascbank.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import com.ascbank.model.User;
import com.ascbank.model.base.PKEntity;
import com.ascbank.web.basis.BaseInterfaceController;

/**
 * @author jie
 *
 */
public interface UserController<T extends Serializable, E extends PKEntity<T>, L extends User, R extends User> extends BaseInterfaceController<T, E> {

	String exit(HttpServletRequest request);

	String login(L user, BindingResult br);

	String register(R user, BindingResult br);

}
