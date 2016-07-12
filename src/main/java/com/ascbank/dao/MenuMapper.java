package com.ascbank.dao;

import com.ascbank.model.Menu;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface MenuMapper extends BaseInterfaceDao<Long, Menu> {

}