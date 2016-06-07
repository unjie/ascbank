/**
 * 
 */
package com.ascbank.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@RequestMapping(value="/login")
	public String login(){
		
		
		
		return "user/login";
	}

}
