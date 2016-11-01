/**
 *
 */
package com.ascbank.service;

import java.io.Serializable;
import java.util.List;

import com.ascbank.model.Dependencies;
import com.ascbank.model.base.PKEntity;
import com.ascbank.service.basis.BaseInterfaceService;

/**
 * @author jie
 *
 */
public interface DependenciesService<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceService<T, E> {
	
	List<Dependencies> readFromId(Long fromId);
	
	List<Dependencies> readToId(Long toId);
	
}
