/**
 *
 */
package com.ascbank.service.basis;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ascbank.dao.base.TreeInterfaceDao;
import com.ascbank.model.base.TreeEntity;

/**
 * @author jie
 *
 */

public abstract class TreeAbstractService<T extends Serializable, E extends TreeEntity<T, E>, D extends TreeInterfaceDao<T, E>> extends BaseAbstractService<T, E, D> implements TreeInterfaceService<T, E> {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 5377582627680895086L;

	private Logger				log					= LoggerFactory.getLogger(TreeAbstractService.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ascbank.service.basis.BaseAbstractService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(String stem) {
		// TODO Auto-generated method stub
		this.beanDao.deleteByLikeStem_(stem);
	}

	@Override
	public List<E> getByParnetId(T parentId) {
		// TODO Auto-generated method stub
		return this.beanDao.selectByParentId(parentId);
	}

	@Override
	public List<E> getByStrm(String stem) {
		// TODO Auto-generated method stub
		return this.beanDao.selectByStem(stem);
	}

	@Override
	public E getTree(E e) {

		if (e.getId() != null) {
			e.setChildren(getTree(e.getId()));
		} else if (e.getStem() != null) {
			e.setChildren(getTree(e.getStem()));
		}

		return e;
	}

	@Override
	public List<E> getTree(String stem) {
		// TODO Auto-generated method stub
		log.debug("----------> {} <--------", stem);
		List<E> es = this.beanDao.selectByLikeStem_(stem);
		if (es.isEmpty() && es.size() < 1) {
			return null;
		}
		for (E e : es) {
			((TreeEntity<T, E>) e).setChildren(this.getTree(e.getStem()));
		}

		return es;
	}

	@Override
	public List<E> getTree(T parentId) {
		// TODO Auto-generated method stub
		List<E> es = this.beanDao.selectByParentId(parentId);
		if (es.isEmpty() && es.size() < 1) {
			return null;
		}

		for (E e : es) {
			((TreeEntity<T, E>) e).setChildren(this.getTree(e.getId()));
		}

		return es;
	}

}
