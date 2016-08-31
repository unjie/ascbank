package com.ascbank.service;

import java.io.Serializable;

import org.springframework.cache.Cache;

import com.ascbank.exception.UserException;
import com.ascbank.model.User;
import com.ascbank.model.base.PKEntity;
import com.ascbank.service.basis.BaseInterfaceService;

public interface UserService<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceService<Long, User> {
	
	public static final String	HASH_ALGORITHM		= "SHA-1";
	public static final int		HASH_INTERATIONS	= 1024;
	public static final int		SALT_SIZE			= 8;
	
	boolean canEvict(Cache userCache, T id, String username);
	
	User entryptPassword(User user);
	
	User login(User user) throws UserException;
	
	User logout();
	
	User read(String username);
	
}