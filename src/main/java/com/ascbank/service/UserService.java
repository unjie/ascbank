package com.ascbank.service;

import com.ascbank.dao.UserMapper;
import com.ascbank.model.User;

public interface UserService {

	void setUserMap(UserMapper userMap);

	User login(User user);

}