package com.ascbank.dao;

import java.util.List;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.UserPermission;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface UserPermissionMapper extends BaseInterfaceDao<Long, UserPermission> {
	
	List<UserPermission> selectPermissionId(Long permissionId);
	
	List<UserPermission> selectUserId(Long userId);
}