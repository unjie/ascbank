package com.ascbank.dao;

import com.ascbank.dao.base.TreeInterfaceDao;
import com.ascbank.model.Project;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ProjectMapper extends TreeInterfaceDao<Long, Project> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_project
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int deleteByPrimaryKey(Long id);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_project
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int insert(Project record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_project
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int insertSelective(Project record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_project
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	Project selectByPrimaryKey(Long id);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_project
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int updateByPrimaryKey(Project record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_project
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int updateByPrimaryKeySelective(Project record);
}