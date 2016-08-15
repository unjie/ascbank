/**
 *
 */
package com.ascbank.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.model.base.PKEntity;

/**
 * @author jie
 *
 */
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public interface BaseInterfaceService<T extends Serializable, E extends PKEntity<T>> extends Serializable {
	
	@Transactional
	public abstract E add(E entity) throws Exception;

	@Transactional
	public abstract void delete(T id) throws Exception;

	public abstract List<E> list(Pageable pageable);

	public abstract List<E> list(Sort sort);

	public abstract E read(T id);

	@Transactional
	public abstract E update(E entity) throws Exception;

}
