/**
 *
 */
package com.ascbank.service;

import java.io.Serializable;
import java.util.List;

import com.ascbank.model.UserPermission;
import com.ascbank.model.base.PKEntity;
import com.ascbank.service.basis.BaseInterfaceService;

/**
 * @author jie
 *
 */
public interface UserPermissionService<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceService<T, E> {

	List<UserPermission> readPermissionId(Long PermissionId);

	List<UserPermission> readUserId(Long userId);

}
