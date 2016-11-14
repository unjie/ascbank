package com.ascbank.dao;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.UrlFilter;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface UrlFilterMapper extends BaseInterfaceDao<Long, UrlFilter> {

	UrlFilter selectByURL(String url);

}