package com.ascbank.dao;

import java.util.List;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.RolePermission;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface RolePermissionMapper extends BaseInterfaceDao<Long, RolePermission> {
	
	List<RolePermission> selectPermissionId(Long permissionId);
	
	List<RolePermission> selectRoleId(Long roleId);
}