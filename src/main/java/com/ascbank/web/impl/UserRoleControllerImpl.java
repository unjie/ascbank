/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.model.UserRole;
import com.ascbank.service.UserRoleService;
import com.ascbank.web.UserRoleController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/userrole")
public class UserRoleControllerImpl extends BaseAbstractController<Long, UserRole, UserRoleService<Long, UserRole>> implements UserRoleController<Long, UserRole> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7746422836218600107L;

	@Override
	@ResponseBody
	@RequestMapping("/read/roleid/{roleId:[\\d]+}")
	public JsonResultInfo readRoleId(@PathVariable("roleId") Long roleId) {
		JsonResultInfo jri = new JsonResultInfo();
		jri.setData(this.beanService.readRoleId(roleId));
		jri.setSuccess(true);
		jri.setMessage("reda Article success!");
		return jri;
	}
	
	@Override
	@ResponseBody
	@RequestMapping("/read/userid/{userId:[\\d]+}")
	public JsonResultInfo readUserId(@PathVariable("userId") Long userId) {
		JsonResultInfo jri = new JsonResultInfo();
		jri.setData(this.beanService.readUserId(userId));
		jri.setSuccess(true);
		jri.setMessage("reda Article success!");
		return jri;
	}
	
}
