/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascbank.model.Permission;
import com.ascbank.service.PermissionService;
import com.ascbank.web.PermissionController;
import com.ascbank.web.basis.BaseAbstractController;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/permission")
public class PermissionControllerImpl extends BaseAbstractController<Long, Permission, PermissionService<Long, Permission>> implements PermissionController<Long, Permission> {

	/**
	 *
	 */
	private static final long serialVersionUID = -7746422836218600107L;

}
