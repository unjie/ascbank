package com.ascbank.dao;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.Permission;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface PermissionMapper extends BaseInterfaceDao<Long, Permission> {
	
}