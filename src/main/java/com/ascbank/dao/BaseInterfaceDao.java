/**
 * 
 */
package com.ascbank.dao;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ascbank.model.Article;
import com.ascbank.model.base.PKEntity;

/**
 * @author jie
 *
 */
public interface BaseInterfaceDao<T extends Serializable, E extends PKEntity<T>> {
	T deleteByPrimaryKey(T id);

	T insert(E record);

	T insertSelective(E record);

	E selectByPrimaryKey(T id);

	T updateByPrimaryKeySelective(E record);

	T updateByPrimaryKey(E record);
	
	
	Page<E> selelctByPageableAll(Pageable pageable);
	
	Iterable<E> selelctBySortAll(Sort sort);
	
	
	
}
