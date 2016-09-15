/**
 *
 */
package com.ascbank.verify;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ascbank.model.base.PKEntity;

/**
 * @author jie
 *
 */
public interface ValidetorInterface<T extends Serializable, E extends PKEntity<T>> {
	
	public List<E> verify(Map<String, Object> map);
	
}
