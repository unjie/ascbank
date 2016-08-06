package com.ascbank.validator.annotation.interfaced;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Persistable;

public interface ValidetorInterface<T extends Serializable, E extends Persistable<T>> {
	
	public List<E> verify(Map<String, Object> map);
}
