package com.ascbank.service;

import com.ascbank.dao.UserMapper;
import com.ascbank.exception.UserException;
import com.ascbank.model.User;

public interface UserService extends BaseInterfaceService<Long, User> {

	void setUserMap(UserMapper userMap);

	User login(User user) throws UserException;

	User read(String username);

	User logout();

}