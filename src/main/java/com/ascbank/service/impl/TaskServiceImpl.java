/**
 *
 */
package com.ascbank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.TaskMapper;
import com.ascbank.model.Task;
import com.ascbank.service.TaskService;
import com.ascbank.service.basis.BaseAbstractService;

/**
 * @author jie
 *
 */
@Service("taskService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class TaskServiceImpl extends BaseAbstractService<Long, Task, TaskMapper> implements TaskService<Long, Task> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7187225558380515542L;

	@Override
	public List<Task> readParentId(Long parentid) {
		return this.getBean().selelctByParentId(parentid);
	}

}
