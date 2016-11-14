/**
 *
 */
package com.ascbank.service;

import com.ascbank.exception.UserException;
import com.ascbank.model.User;
import com.ascbank.model.derive.Login;

/**
 * @author jie
 *
 */
public interface AuthcService<E extends Login> {

	User login(E user) throws UserException;

	String logout();
}
