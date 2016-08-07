/**
 *
 */
package com.ascbank.dao.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ascbank.model.base.PKEntity;

/**
 * @author jie
 *
 */
public interface BaseInterfaceDao<T extends Serializable, E extends PKEntity<T>> {
	
	int deleteByPrimaryKey(T id);
	
	int insert(E record);
	
	int insertSelective(E record);
	
	List<E> selectAll();
	
	E selectByPrimaryKey(T id);
	
	List<E> selelctByPageableAll(Pageable pageable);
	
	List<E> selelctBySortAll(Sort sort);
	
	int updateByPrimaryKey(E record);
	
	int updateByPrimaryKeySelective(E record);
	
}
