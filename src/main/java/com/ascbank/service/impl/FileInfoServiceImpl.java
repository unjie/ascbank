/**
 *
 */
package com.ascbank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.FileInfoMapper;
import com.ascbank.model.FileInfo;
import com.ascbank.service.FileInfoService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("fileInfoService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class FileInfoServiceImpl extends BaseAbstractService<Long, FileInfo, FileInfoMapper> implements FileInfoService<Long, FileInfo> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8248606702925998294L;

}
