/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascbank.model.Gantt;
import com.ascbank.service.GanttService;
import com.ascbank.web.GanttController;
import com.ascbank.web.basis.TreeAbstractController;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/gantt")
public class GanttControllerImpl extends TreeAbstractController<Long, Gantt, GanttService<Long, Gantt>> implements GanttController<Long, Gantt> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -6295337656854781122L;
	
}
