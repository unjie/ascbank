/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascbank.model.Role;
import com.ascbank.service.RoleService;
import com.ascbank.web.RoleController;
import com.ascbank.web.basis.BaseAbstractController;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/role")
public class RoleControllerImpl extends BaseAbstractController<Long, Role, RoleService> implements RoleController {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 4126041710492901883L;
	
}
