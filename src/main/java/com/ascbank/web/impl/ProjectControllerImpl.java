/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascbank.model.Project;
import com.ascbank.service.ProjectService;
import com.ascbank.web.ProjectController;
import com.ascbank.web.basis.TreeAbstractController;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectControllerImpl extends TreeAbstractController<Long, Project, ProjectService<Long, Project>> implements ProjectController<Long, Project> {

	/**
	 *
	 */
	private static final long serialVersionUID = 333386088325045809L;

}
