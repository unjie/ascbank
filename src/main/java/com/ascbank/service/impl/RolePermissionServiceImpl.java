/**
 *
 */
package com.ascbank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.RolePermissionMapper;
import com.ascbank.model.RolePermission;
import com.ascbank.service.RolePermissionService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("rolePermissionService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class RolePermissionServiceImpl extends BaseAbstractService<Long, RolePermission, RolePermissionMapper> implements RolePermissionService<Long, RolePermission> {

	/**
	 *
	 */
	private static final long serialVersionUID = -815822323760563958L;

	@Override
	public List<RolePermission> readPermissionId(Long permissionId) {

		return getBean().selectPermissionId(permissionId);
	}

	@Override
	public List<RolePermission> readRoleId(Long roleId) {
		return getBean().selectRoleId(roleId);
	}

}
