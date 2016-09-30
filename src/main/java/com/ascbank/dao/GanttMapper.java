package com.ascbank.dao;

import com.ascbank.dao.base.TreeInterfaceDao;
import com.ascbank.model.Gantt;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface GanttMapper extends TreeInterfaceDao<Long, Gantt> {
	
}