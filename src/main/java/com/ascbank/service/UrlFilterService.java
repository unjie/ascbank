/**
 *
 */
package com.ascbank.service;

import java.io.Serializable;

import com.ascbank.dao.UrlFilterMapper;
import com.ascbank.model.UrlFilter;
import com.ascbank.model.base.PKEntity;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
public interface UrlFilterService<T extends Serializable, E extends PKEntity<T>> {

	UrlFilter read(String url);

	void setUrlFilterMapper(UrlFilterMapper urlFilterMapper);

}
