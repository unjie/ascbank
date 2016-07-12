package com.ascbank.dao;

import com.ascbank.model.Gantt;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface GanttMapper extends BaseInterfaceDao<Long, Gantt> {

}