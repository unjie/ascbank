package com.ascbank.dao;

import com.ascbank.model.Navigation;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface NavigationMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table navigation
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table navigation
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int insert(Navigation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table navigation
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int insertSelective(Navigation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table navigation
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	Navigation selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table navigation
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int updateByPrimaryKeySelective(Navigation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table navigation
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int updateByPrimaryKey(Navigation record);
}