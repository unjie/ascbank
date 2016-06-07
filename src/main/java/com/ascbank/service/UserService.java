package com.ascbank.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import com.ascbank.dao.UserMapper;
import com.ascbank.model.User;

@Service
@Transactional(readOnly = true,isolation=Isolation.READ_UNCOMMITTED)
public class UserService {

	@Resource
	private UserMapper userMap;
	
	
	
	public void setUserMap(UserMapper userMap) {
		this.userMap = userMap;
	}



	public User login(User user){
		
		
		user = userMap.selectByUsername(user.getUsername());
		
		return user;
		
	}
	
}
