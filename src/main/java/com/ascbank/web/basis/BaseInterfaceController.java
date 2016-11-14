package com.ascbank.web.basis;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import com.ascbank.model.base.PKEntity;

public interface BaseInterfaceController<T extends Serializable, E extends PKEntity<T>> extends Serializable {

	JsonResultInfo create(E entity, BindingResult br);

	JsonResultInfo destroy(E entity);

	// public abstract List<E> readAll(Integer page, Integer start, Integer
	// limit);

	// public abstract List<E> readAll(String property, String direction);

	JsonResultInfo destroy(T id);

	JsonResultInfo read(T id);

	JsonResultInfo readAll(Pageable pageable);

	JsonResultInfo update(E entity, BindingResult br);

	// public abstract String page(String page, Model model);
}
