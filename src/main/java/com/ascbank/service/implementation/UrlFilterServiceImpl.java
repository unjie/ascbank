/**
 * 
 */
package com.ascbank.service.implementation;

import org.springframework.stereotype.Service;

import com.ascbank.dao.UrlFilterMapper;
import com.ascbank.model.UrlFilter;
import com.ascbank.service.UrlFilterService;

/**
 * @author unjie wusu_jie@qq.com
 *
 */
@Service("urlFilterService")
public class UrlFilterServiceImpl implements UrlFilterService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ascbank.service.UrlFilterService#read(java.lang.String)
	 */
	@Override
	public UrlFilter read(String url) {
		// TODO Auto-generated method stub
		return urlFilterMapper.selectByURL(url);
	}

	private UrlFilterMapper urlFilterMapper;

	@Override
	public void setUrlFilterMapper(UrlFilterMapper urlFilterMapper) {
		this.urlFilterMapper = urlFilterMapper;
	}

}
