package com.ascbank.validator.annotation.validtor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.domain.Persistable;

import com.ascbank.model.base.PKEntity;
import com.ascbank.validator.annotation.Unique;
import com.ascbank.validator.annotation.interfaced.ValidetorInterface;

@SuppressWarnings("rawtypes")
// @SupportedValidationTarget(value = { ValidationTarget.PARAMETERS })
public class UniqueValidator implements ConstraintValidator<Unique, Persistable<? extends Serializable>> {
	
	private static final Logger	log	= LoggerFactory.getLogger(UniqueValidator.class);
	
	private String[]			attributes;
	private BeanFactory			beanFactory;
	private ValidetorInterface	validetorBean;
	
	/**
	 * @Unque 验证 bean 需要实现ValidetorInterface 接口 bean Class与BeanName 只需要传入一个,BeanName优先于Class 如果都不设置这默认验证不通过
	 */
	@Override
	public void initialize(Unique annotation) {
		// TODO Auto-generated method stub
		Object obj = (beanFactory != null)
				? (annotation.verifyBean().isEmpty())
						? (annotation.verifyClass().isInterface() ? null
								: beanFactory.getBean(annotation.verifyClass()))
								: (beanFactory.getBean(annotation.verifyBean()))
								: null;
								
								this.validetorBean = (obj instanceof ValidetorInterface) ? (ValidetorInterface) obj : null;
								
								this.attributes = annotation.attributes();
								
	}
	
	@Override
	public boolean isValid(Persistable value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		for (String attr : attributes) {
			Object obj;
			try {
				obj = new PropertyDescriptor(attr, value.getClass()).getReadMethod().invoke(value);// value.getClass().getMethod("get"
				// +
				// StringUtil.capitalize(attr)).invoke(value);
				if (obj != null) {
					map.put(attr, obj);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				UniqueValidator.log.debug(e.toString());
			}
			
		}
		
		@SuppressWarnings("unchecked")
		List<PKEntity> list = this.validetorBean.verify(map);
		
		UniqueValidator.log.debug("-----------------list.size() ={}---------------", list.size());
		if (list.size() > 1) {
			return false;
		} else if (list.size() == 1) {
			Persistable e = list.get(0);
			UniqueValidator.log.debug("-----------------e.getId() :{} == value.getId() : {}---------------", e.getId(), value.getId());
			return (!e.isNew()) && (e.getId() == value.getId());
		}
		
		return true;
		
	}
	
	@Resource
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
}
