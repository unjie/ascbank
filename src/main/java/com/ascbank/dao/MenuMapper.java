package com.ascbank.dao;

import java.util.List;

import com.ascbank.dao.base.TreeInterfaceDao;
import com.ascbank.model.Menu;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface MenuMapper extends TreeInterfaceDao<Long, Menu> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table menu
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table menu
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int insert(Menu record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table menu
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	List<Menu> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table menu
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	Menu selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table menu
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int updateByPrimaryKey(Menu record);
}