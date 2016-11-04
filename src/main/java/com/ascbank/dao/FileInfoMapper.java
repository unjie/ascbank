package com.ascbank.dao;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.FileInfo;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface FileInfoMapper extends BaseInterfaceDao<Long, FileInfo> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_file_info
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int deleteByPrimaryKey(Long id);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_file_info
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int insert(FileInfo record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_file_info
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int insertSelective(FileInfo record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_file_info
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	FileInfo selectByPrimaryKey(Long id);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_file_info
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int updateByPrimaryKey(FileInfo record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_file_info
	 *
	 * @mbggenerated Thu Oct 20 10:03:04 CST 2016
	 */
	@Override
	int updateByPrimaryKeySelective(FileInfo record);
}