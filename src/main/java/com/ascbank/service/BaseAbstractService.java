/**
 *
 */
package com.ascbank.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.GenericityInterface;
import com.ascbank.dao.base.BaseInterfaceDao;
import com.ascbank.dependency.injection.InjectionInterface;
import com.ascbank.model.base.PKEntity;
import com.ascbank.repository.modules.SearchFilter;
import com.ascbank.repository.modules.SearchFilter.Operator;
import com.ascbank.security.shiro.authz.annotation.AutoPermissions;
import com.ascbank.verify.ValidetorInterface;

/**
 * @author jie
 *
 */
public abstract class BaseAbstractService<T extends Serializable, E extends PKEntity<T>, D extends BaseInterfaceDao<T, E>>
		implements BaseInterfaceService<T, E>, InjectionInterface<D>, ValidetorInterface<T, E>, GenericityInterface {
	/**
	 *
	 */
	private static final long	serialVersionUID	= -8237810984312825562L;
	@Autowired
	D							beanDao;

	private final Logger		log					= LoggerFactory.getLogger(BaseAbstractService.class);

	@Override
	@Transactional
	@AutoPermissions
	// @Caching(evict = { @CacheEvict(value = "Page", allEntries = true) }, put = { @CachePut(value = "springEhcache", key = "#root.targetClass.getName()+'_'+#result.id", condition = "#result !=null") })
	public E add(E entity) {
		// 类类型表达式：使用“T(Type)”来表示java.lang.Class实例，“Type”必须是类全限定名，“java.lang”包除外，即该包下的类可以不指定包名；使用类类型表达式还可以进行访问类静态方法及类静态字段。
		// TODO Auto-generated method stub
		if (log.isDebugEnabled()) {
			log.debug("----------------- add entity={}------------------", entity);
		}
		assert (beanDao.insert(entity) == 1);
		return entity;
	}

	@Override
	@Transactional
	@AutoPermissions
	// @Caching(evict = { @CacheEvict(value = "Page", allEntries = true), @CacheEvict(value = "springEhcache", key ="#root.targetClass.getName()+'_'+#id", condition = "#id !=null") })
	public void delete(T id) {
		// TODO Auto-generated method stub
		if (log.isDebugEnabled()) {
			log.debug("----------------- delete entity={}------------------", id);
		}
		beanDao.deleteByPrimaryKey(id);
	}

	public D getBean() {
		return this.beanDao;
	}
	
	/*
	 * @Override public Class<?> getGenericity(Integer index) { return ResolvableType.forType(this.getClass().getGenericSuperclass()).resolveGeneric(index); }
	 * 
	 * @Override public Class<?>[] getGenericitys() { return ResolvableType.forType(this.getClass().getGenericSuperclass()).resolveGenerics(); }
	 */

	@Override
	@AutoPermissions
	// @Caching(cacheable = { @Cacheable(value = "Page", key = "#root.targetClass.getName()+'_page_'+#pageable.getPageNumber()+'_'+#pageable.getPageSize()+#pageable.sort.iterator().next().getProperty()+'_'+#pageable.sort.iterator().next().getDirection().name()",condition = "#pageable != null") })
	public List<E> read(Pageable pageable) {
		return beanDao.selelctByPageableAll(pageable);
	}

	@Override
	@AutoPermissions
	// @Caching(cacheable = { @Cacheable(value = "Page", key = "#root.targetClass.getName()+'_'+#sort.iterator().next().getProperty()+'_'+#sort.iterator().next().getDirection().name()", condition = "#sort != null") })
	public List<E> read(Sort sort) {
		// TODO Auto-generated method stub
		log.debug("----------------- readAll entity={}------------------", sort);
		return beanDao.selelctBySortAll(sort);
	}

	@Override
	@AutoPermissions(permission = "read")
	// @RequiresPermissions("read:*")
	// @Caching(cacheable = { @Cacheable(value = "ServicCache", key = "#root.targetClass.getName()+'_'+#id +#root.target.logInfo('loginfo_'+#id)", condition = "#id!=null and #root.target.logInfo('bease-read_'+#id)!=null") })
	public E read(T id) {
		// TODO Auto-generated method stub
		log.debug("---------------read {}----", id);
		return beanDao.selectByPrimaryKey(id);
	}

	@Override
	public void setBean(D bean) {
		// TODO Auto-generated method stub
		this.beanDao = bean;
	}
	
	@Override
	@Transactional
	@AutoPermissions
	// @Caching(evict = { @CacheEvict(value = "Page", allEntries = true),@CacheEvict(value = "springEhcache", key = "#root.targetClass.getName()+'_'+#entity.id", condition = "#entity!=null and (#entity.id !=null)") }, put = { @CachePut(value = "springEhcache", key = "#root.targetClass.getName()+'_'+#result.id", condition = "#entity!=null") })
	public E update(E entity) {
		log.debug("----------------- update entity={}------------------", entity);
		beanDao.updateByPrimaryKeySelective(entity);
		return entity;
	}

	@Override
	public List<E> verify(Map<String, Object> map) {
		
		// TODO Auto-generated method stub
		
		List<SearchFilter> list = new ArrayList<SearchFilter>(); // 获取当前new的对象的 泛型的父类 类型

		for (Entry<String, Object> entry : map.entrySet()) {
			list.add(new SearchFilter(entry.getKey(), Operator.EQ, entry.getValue()));
		}
		
		return null;
		
	}
}
