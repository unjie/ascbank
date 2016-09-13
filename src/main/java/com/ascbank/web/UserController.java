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
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
public interface UserController<T extends Serializable, E extends PKEntity<T>, L extends User, R extends User> extends BaseInterfaceController<T, E> {
	
	JsonResultInfo exit(HttpServletRequest request);
	
	JsonResultInfo login(L user, BindingResult br);
	
	JsonResultInfo register(R user, BindingResult br);
	
}
