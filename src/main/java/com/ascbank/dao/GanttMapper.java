package com.ascbank.dao;

import java.util.List;

import com.ascbank.dao.base.TreeInterfaceDao;
import com.ascbank.model.Gantt;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface GanttMapper extends TreeInterfaceDao<Long, Gantt> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table gantt
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table gantt
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int insert(Gantt record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table gantt
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	List<Gantt> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table gantt
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	Gantt selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table gantt
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int updateByPrimaryKey(Gantt record);
}