/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.model.UserPermission;
import com.ascbank.service.UserPermissionService;
import com.ascbank.web.UserPermissionController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/userpermission")
public class UserPermissionControllerImpl extends BaseAbstractController<Long, UserPermission, UserPermissionService<Long, UserPermission>> implements UserPermissionController<Long, UserPermission> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7746422836218600107L;
	
	@Override
	@ResponseBody
	@RequestMapping("/read/permissionid/{permissionId:[\\d]+}")
	public JsonResultInfo readPermissionId(@PathVariable("permissionId") Long permissionId) {
		JsonResultInfo jri = new JsonResultInfo();
		jri.setData(this.beanService.readPermissionId(permissionId));
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
