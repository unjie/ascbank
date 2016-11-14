/**
 *
 */
package com.ascbank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.UserPermissionMapper;
import com.ascbank.model.UserPermission;
import com.ascbank.service.UserPermissionService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("userPermissionService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class UserPermissionServiceImpl extends BaseAbstractService<Long, UserPermission, UserPermissionMapper> implements UserPermissionService<Long, UserPermission> {

	/**
	 *
	 */
	private static final long serialVersionUID = -815822323760563958L;

	@Override
	public List<UserPermission> readPermissionId(Long permissionId) {

		return getBean().selectPermissionId(permissionId);
	}

	@Override
	public List<UserPermission> readUserId(Long userId) {
		return getBean().selectUserId(userId);
	}

}
