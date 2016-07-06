/**
 * 
 */
package com.ascbank.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascbank.model.User;
import com.ascbank.service.UserService;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/login")
	public String login(User user) {

		System.out.println(user);

		return "user/login";
	}

}
