/**
 *
 */
package com.ascbank.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import com.ascbank.model.derive.Login;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
public interface AuthcController<E extends Login> {

	JsonResultInfo exit(HttpServletRequest request);

	JsonResultInfo login(E user, BindingResult br);

	JsonResultInfo readMe(HttpServletRequest request);

}
