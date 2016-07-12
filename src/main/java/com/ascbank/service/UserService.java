package com.ascbank.service;

import org.springframework.cache.Cache;

import com.ascbank.dao.UserMapper;
import com.ascbank.exception.UserException;
import com.ascbank.model.User;

public interface UserService extends BaseInterfaceService<Long, User> {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	static final int SALT_SIZE = 8;
	
	User login(User user) throws UserException;

	User read(String username);

	User logout();

	User entryptPassword(User user);

	boolean canEvict(Cache userCache, Long id, String username);

}