/**
 *
 */
package com.ascbank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.SystemInfoMapper;
import com.ascbank.model.SystemInfo;
import com.ascbank.service.SystemInfoService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("SystemInfoService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class SystemInfoServiceImpl extends BaseAbstractService<Long, SystemInfo, SystemInfoMapper> implements SystemInfoService<Long, SystemInfo> {

	/**
	 *
	 */
	private static final long serialVersionUID = -8968224557117459349L;

}
