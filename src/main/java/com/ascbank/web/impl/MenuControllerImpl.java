/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascbank.model.Menu;
import com.ascbank.service.MenuService;
import com.ascbank.web.MenuController;
import com.ascbank.web.basis.TreeAbstractController;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuControllerImpl extends TreeAbstractController<Long, Menu, MenuService<Long, Menu>> implements MenuController<Long, Menu> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 333386088325045809L;

}
