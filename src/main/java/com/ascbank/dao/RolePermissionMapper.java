package com.ascbank.dao;

import com.ascbank.model.RolePermission;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface RolePermissionMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table role_permission
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int insert(RolePermission record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table role_permission
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int insertSelective(RolePermission record);
}