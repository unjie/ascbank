/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.model.Task;
import com.ascbank.service.TaskService;
import com.ascbank.web.TaskController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/task")
public class TaskControllerImpl extends BaseAbstractController<Long, Task, TaskService<Long, Task>> implements TaskController<Long, Task> {

	/**
	 *
	 */
	private static final long serialVersionUID = 6497433358626430389L;

	@Override
	@ResponseBody
	@RequestMapping("/read/parentid/{parentid:[\\d]+}")
	public JsonResultInfo reads(@PathVariable("parentid") Long parentid) {
		JsonResultInfo jri = new JsonResultInfo();
		jri.setData(this.beanService.readParentId(parentid));
		jri.setSuccess(true);
		jri.setMessage("reda Article success!");
		return jri;
	}

}
