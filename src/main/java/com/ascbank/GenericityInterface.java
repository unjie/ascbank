/**
 *
 */
package com.ascbank;

import org.springframework.core.ResolvableType;

/**
 * @author jie
 *
 */
public interface GenericityInterface {

	default Class<?> getGenericity(Integer index) {
		return ResolvableType.forType(this.getClass().getGenericSuperclass()).resolveGeneric(index);
	}

	default Class<?>[] getGenericitys() {
		return ResolvableType.forType(this.getClass().getGenericSuperclass()).resolveGenerics();
	}
}
