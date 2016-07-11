package com.ascbank.dao;

import com.ascbank.model.Role;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface RoleMapper  extends  BaseInterfaceDao<Long,Role>{
	
}