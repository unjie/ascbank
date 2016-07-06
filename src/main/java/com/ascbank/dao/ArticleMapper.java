package com.ascbank.dao;

import com.ascbank.model.Article;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ArticleMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table article
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table article
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int insert(Article record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table article
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int insertSelective(Article record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table article
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	Article selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table article
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int updateByPrimaryKeySelective(Article record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table article
	 *
	 * @mbggenerated Tue Jun 07 15:09:14 CST 2016
	 */
	int updateByPrimaryKey(Article record);
}