/**
 *
 */
package com.ascbank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.GanttMapper;
import com.ascbank.model.Gantt;
import com.ascbank.service.GanttService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("ganttService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class GanttServiceImpl extends BaseAbstractService<Long, Gantt, GanttMapper> implements GanttService<Long, Gantt> {

	/**
	 *
	 */
	private static final long serialVersionUID = -7207805758680856544L;
	
}
