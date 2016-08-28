package com.ascbank.web.basis;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import com.ascbank.model.base.PKEntity;

public interface BaseInterfaceController<T extends Serializable, E extends PKEntity<T>> extends Serializable {
	
	public abstract JsonResultInfo create(E entity, BindingResult br);
	
	public abstract JsonResultInfo destroy(E entity, T id);
	
	public String getHtml(String pagename);
	
	// public abstract List<E> readAll(Integer page, Integer start, Integer
	// limit);
	
	// public abstract List<E> readAll(String property, String direction);
	
	public abstract JsonResultInfo read(T id);
	
	public abstract JsonResultInfo readAll(Pageable pageable);
	
	public abstract JsonResultInfo update(E entity, BindingResult br);
	
	// public abstract String page(String page, Model model);
}
