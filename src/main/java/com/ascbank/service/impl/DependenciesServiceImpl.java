/**
 *
 */
package com.ascbank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.DependenciesMapper;
import com.ascbank.model.Dependencies;
import com.ascbank.service.DependenciesService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("dependenciesService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class DependenciesServiceImpl extends BaseAbstractService<Long, Dependencies, DependenciesMapper> implements DependenciesService<Long, Dependencies> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -815822323760563958L;
	
	@Override
	public List<Dependencies> readFromId(Long fromId) {
		return getBean().selectFromId(fromId);
	}
	
	@Override
	public List<Dependencies> readToId(Long toId) {
		
		return getBean().selectToId(toId);
	}

}
