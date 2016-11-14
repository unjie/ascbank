package com.ascbank.dao;

import java.util.List;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.Dependencies;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface DependenciesMapper extends BaseInterfaceDao<Long, Dependencies> {

	Dependencies selectFromAndToId(Dependencies dep);

	List<Dependencies> selectFromId(Long fromId);

	List<Dependencies> selectToId(Long toId);

}