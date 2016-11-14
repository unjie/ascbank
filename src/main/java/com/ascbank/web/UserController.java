/**
 *
 */
package com.ascbank.web;

import java.io.Serializable;

import org.springframework.validation.BindingResult;

import com.ascbank.model.base.PKEntity;
import com.ascbank.model.derive.Register;
import com.ascbank.web.basis.BaseInterfaceController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
public interface UserController<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceController<T, E> {

	JsonResultInfo register(Register user, BindingResult br);

}
