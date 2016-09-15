package com.ascbank.dao;

import java.util.List;

import com.ascbank.model.UserRole;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface UserRoleMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_role
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	int insert(UserRole record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_role
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	List<UserRole> selectAll();
}