package com.ascbank.dao;

import java.util.List;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.UserRole;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface UserRoleMapper extends BaseInterfaceDao<Long, UserRole> {

	List<UserRole> selecRoleId(Long toId);

	List<UserRole> selectUserId(Long fromId);
}