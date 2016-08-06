/**
 *
 */
package com.ascbank.service;

import com.ascbank.dao.UrlFilterMapper;
import com.ascbank.model.UrlFilter;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
public interface UrlFilterService {
	
	UrlFilter read(String url);
	
	void setUrlFilterMapper(UrlFilterMapper urlFilterMapper);
	
}
