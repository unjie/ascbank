/**
 *
 */
package com.ascbank.service.basis;

import java.io.Serializable;
import java.util.List;

import com.ascbank.model.base.TreeEntity;

/**
 * @author jie
 *
 */
public interface TreeInterfaceService<T extends Serializable, E extends TreeEntity<T, E>> extends BaseInterfaceService<T, E> {
	
	public List<E> getByParnetId(T parentId);
	
	public List<E> getByStrm(String stem);

	public E getTree(E e);
	
	public List<E> getTree(String stem);

	public List<E> getTree(T parentId);
}
