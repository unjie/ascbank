/**
 *
 */
package com.ascbank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.MenuMapper;
import com.ascbank.model.Menu;
import com.ascbank.service.MenuService;
import com.ascbank.service.basis.TreeAbstractService;

/**
 * @author jie
 *
 */
@Service("menuService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class MenuServiceImpl extends TreeAbstractService<Long, Menu, MenuMapper> implements MenuService<Long, Menu> {
	
	private static final long serialVersionUID = 7594942276660876994L;
	
}
