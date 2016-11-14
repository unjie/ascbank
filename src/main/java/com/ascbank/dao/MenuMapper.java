package com.ascbank.dao;

import com.ascbank.dao.base.TreeInterfaceDao;
import com.ascbank.model.Menu;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface MenuMapper extends TreeInterfaceDao<Long, Menu> {

}