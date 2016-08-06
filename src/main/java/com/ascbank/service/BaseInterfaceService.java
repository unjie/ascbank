/**
 *
 */
package com.ascbank.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ascbank.model.base.PKEntity;

/**
 * @author jie
 *
 */
public interface BaseInterfaceService<T extends Serializable, E extends PKEntity<T>> extends Serializable {
	
	public abstract E add(E entity) throws Exception;

	public abstract void delete(T id) throws Exception;

	public abstract List<E> list(Pageable pageable);

	public abstract List<E> list(Sort sort);

	public abstract E read(T id);

	public abstract E update(E entity) throws Exception;

}
