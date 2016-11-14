/**
 *
 */
package com.ascbank.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ascbank.dao.ProjectMapper;
import com.ascbank.model.Project;
import com.ascbank.service.ProjectService;
import com.ascbank.service.basis.TreeAbstractService;

/**
 * @author jie
 *
 */
@Service("projectService")
@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class ProjectServiceImpl extends TreeAbstractService<Long, Project, ProjectMapper> implements ProjectService<Long, Project> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3586505244920588895L;

}
