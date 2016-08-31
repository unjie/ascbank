/**
 *
 */
package com.ascbank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.RoleMapper;
import com.ascbank.model.Role;
import com.ascbank.service.RoleService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("roleService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class RoleServiceImpl extends BaseAbstractService<Long, Role, RoleMapper> implements RoleService<Long, Role> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 7808011505169491578L;
	
}
