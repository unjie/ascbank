package com.ascbank.service;

import org.springframework.cache.Cache;

import com.ascbank.exception.UserException;
import com.ascbank.model.User;

public interface UserService extends BaseInterfaceService<Long, User> {

	public static final String	HASH_ALGORITHM		= "SHA-1";
	public static final int		HASH_INTERATIONS	= 1024;
	public static final int		SALT_SIZE			= 8;
	
	boolean canEvict(Cache userCache, Long id, String username);
	
	User entryptPassword(User user);
	
	User login(User user) throws UserException;
	
	User logout();
	
	User read(String username);
	
}