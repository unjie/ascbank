package com.ascbank.dao;

import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.model.User;
import com.ascbank.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface UserMapper extends BaseInterfaceDao<Long, User> {
	
	User selectByEmail(String email);

	User selectByPhone(Long phone);
	
	User selectByUser(User user);

	User selectByUsername(String username);
	
}