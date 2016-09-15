package com.ascbank.dao;

import java.util.List;

import com.ascbank.model.RolePermission;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface RolePermissionMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table role_permission
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	int insert(RolePermission record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table role_permission
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	List<RolePermission> selectAll();
}