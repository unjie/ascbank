/**
 *
 */
package com.ascbank.dao.base;

import java.io.Serializable;
import java.util.List;

import com.ascbank.model.base.TreeEntity;

/**
 * @author jie
 *
 */
public interface TreeInterfaceDao<T extends Serializable, E extends TreeEntity<T, E>> extends BaseInterfaceDao<T, E> {
	
	List<E> selectByParentId(T parentId);

	List<E> selectByStem(String stem);

	List<E> selectLikeStem_(String stem);
	
}
