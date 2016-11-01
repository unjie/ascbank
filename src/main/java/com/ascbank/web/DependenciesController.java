/**
 *
 */
package com.ascbank.web;

import java.io.Serializable;

import com.ascbank.model.base.PKEntity;
import com.ascbank.web.basis.BaseInterfaceController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
public interface DependenciesController<T extends Serializable, E extends PKEntity<T>> extends BaseInterfaceController<T, E> {
	
	JsonResultInfo readFromId(Long fromId);
	
	JsonResultInfo readToId(Long toId);
	
}
