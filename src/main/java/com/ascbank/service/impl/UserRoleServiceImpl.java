/**
 *
 */
package com.ascbank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.UserRoleMapper;
import com.ascbank.model.UserRole;
import com.ascbank.service.UserRoleService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("userRoleService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class UserRoleServiceImpl extends BaseAbstractService<Long, UserRole, UserRoleMapper> implements UserRoleService<Long, UserRole> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -815822323760563958L;

	@Override
	public List<UserRole> readRoleId(Long toId) {
		
		return getBean().selecRoleId(toId);
	}

	@Override
	public List<UserRole> readUserId(Long fromId) {
		return getBean().selectUserId(fromId);
	}
	
}
