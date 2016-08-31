/**
 *
 */
package com.ascbank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.PermissionMapper;
import com.ascbank.model.Permission;
import com.ascbank.service.PermissionService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("permissionService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class PermissionServiceImpl extends BaseAbstractService<Long, Permission, PermissionMapper> implements PermissionService<Long, Permission> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -889644476025098304L;
	
}
