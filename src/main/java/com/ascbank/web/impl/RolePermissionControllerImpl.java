/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.model.RolePermission;
import com.ascbank.service.RolePermissionService;
import com.ascbank.web.RolePermissionController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/rolepermission")
public class RolePermissionControllerImpl extends BaseAbstractController<Long, RolePermission, RolePermissionService<Long, RolePermission>> implements RolePermissionController<Long, RolePermission> {
	
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
	@RequestMapping("/read/roleid/{roleId:[\\d]+}")
	public JsonResultInfo readRoleId(@PathVariable("roleId") Long roleId) {
		JsonResultInfo jri = new JsonResultInfo();
		jri.setData(this.beanService.readRoleId(roleId));
		jri.setSuccess(true);
		jri.setMessage("reda Article success!");
		return jri;
	}

}
