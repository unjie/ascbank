/**
 *
 */
package com.ascbank.service;

import java.io.Serializable;
import java.util.List;

import com.ascbank.model.UserRole;
import com.ascbank.model.base.PKEntity;
import com.ascbank.service.basis.BaseInterfaceService;

/**
 * @author jie
 *
 */
public interface UserRoleService<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceService<T, E> {

	List<UserRole> readRoleId(Long toId);

	List<UserRole> readUserId(Long fromId);

}
