package com.ascbank.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.UserMapper;
import com.ascbank.model.User;
import com.ascbank.service.BaseAbstractService;
import com.ascbank.service.UserService;

@Service("userService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class UserServiceImpl extends BaseAbstractService<Long, User, UserMapper> implements UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6000159997137264505L;
	@Resource
	private UserMapper userMap;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ascbank.service.UserService_#setUserMap(com.ascbank.dao.UserMapper)
	 */

	@Override
	public void setUserMap(UserMapper userMap) {
		this.userMap = userMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ascbank.service.UserService_#login(com.ascbank.model.User)
	 */

	@Override
	public User login(User user) {

		user = userMap.selectByUsername(user.getUsername());

		return user;

	}

	@Override
	public User read(String username) {
		// TODO Auto-generated method stub
		return userMap.selectByUsername(username);
	}

	@Override
	public User logout() {
		// TODO Auto-generated method stub
		return null;
	}

}
