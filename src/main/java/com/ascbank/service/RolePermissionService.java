/**
 *
 */
package com.ascbank.service;

import java.io.Serializable;
import java.util.List;

import com.ascbank.model.RolePermission;
import com.ascbank.model.base.PKEntity;
import com.ascbank.service.basis.BaseInterfaceService;

/**
 * @author jie
 *
 */
public interface RolePermissionService<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceService<T, E> {

	List<RolePermission> readPermissionId(Long permissionId);

	List<RolePermission> readRoleId(Long roleId);

}
