package com.ascbank.service;

import java.io.Serializable;
import java.util.List;

import com.ascbank.model.base.PKEntity;
import com.ascbank.service.basis.BaseInterfaceService;

public interface TaskService<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceService<T, E> {
	
	List<E> readParentId(Long parentid);
	
}