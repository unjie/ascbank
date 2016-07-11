package com.ascbank.dao;

import com.ascbank.model.Shortcut;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ShortcutMapper  extends  BaseInterfaceDao<Long,Shortcut>{
	
}