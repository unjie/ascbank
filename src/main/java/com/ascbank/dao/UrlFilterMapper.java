package com.ascbank.dao;

import java.util.List;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.UrlFilter;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface UrlFilterMapper extends BaseInterfaceDao<Long, UrlFilter> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table url_filter
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int deleteByPrimaryKey(Long id);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table url_filter
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int insert(UrlFilter record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table url_filter
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	List<UrlFilter> selectAll();
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table url_filter
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	UrlFilter selectByPrimaryKey(Long id);
	
	UrlFilter selectByURL(String url);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table url_filter
	 *
	 * @mbggenerated Wed Jul 13 16:52:44 CST 2016
	 */
	@Override
	int updateByPrimaryKey(UrlFilter record);
}