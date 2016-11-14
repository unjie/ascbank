package com.ascbank.service;

import java.io.Serializable;

import com.ascbank.model.Menu;
import com.ascbank.model.base.TreeEntity;
import com.ascbank.service.basis.TreeInterfaceService;

public interface MenuService<T extends Serializable, E extends TreeEntity<T, E>> extends TreeInterfaceService<Long, Menu> {

}