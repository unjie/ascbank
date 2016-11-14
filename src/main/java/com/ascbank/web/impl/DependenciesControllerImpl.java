/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.model.Dependencies;
import com.ascbank.service.DependenciesService;
import com.ascbank.web.DependenciesController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/dependencies")
public class DependenciesControllerImpl extends BaseAbstractController<Long, Dependencies, DependenciesService<Long, Dependencies>> implements DependenciesController<Long, Dependencies> {

	/**
	 *
	 */
	private static final long serialVersionUID = -7713298953921279482L;

	@Override
	@ResponseBody
	@RequestMapping("/read/fromid/{fromId:[\\d]+}")
	public JsonResultInfo readFromId(@PathVariable("fromId") Long fromId) {
		JsonResultInfo jri = new JsonResultInfo();
		jri.setData(this.beanService.readFromId(fromId));
		jri.setSuccess(true);
		jri.setMessage("reda Article success!");
		return jri;
	}

	@Override
	@ResponseBody
	@RequestMapping("/read/toid/{toId:[\\d]+}")
	public JsonResultInfo readToId(@PathVariable("toId") Long toId) {
		JsonResultInfo jri = new JsonResultInfo();
		jri.setData(this.beanService.readFromId(toId));
		jri.setSuccess(true);
		jri.setMessage("reda Article success!");
		return jri;
	}

}
