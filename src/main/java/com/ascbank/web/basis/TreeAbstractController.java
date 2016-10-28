/**
 *
 */
package com.ascbank.web.basis;

import java.io.Serializable;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.model.base.TreeEntity;
import com.ascbank.service.basis.TreeInterfaceService;

/**
 * @author jie
 *
 */
public abstract class TreeAbstractController<T extends Serializable, E extends TreeEntity<T, E>, S extends TreeInterfaceService<T, E>> extends BaseAbstractController<T, E, S> implements TreeInterfaceController<T, E> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7776431377505449845L;
	
	@Override
	@ResponseBody
	@RequestMapping(value = { "/children/{parentId:[\\d]+}" }, method = { RequestMethod.GET })
	public JsonResultInfo children(@PathVariable("parentId") T parentId) {
		// TODO Auto-generated method stub
		JsonResultInfo info = new JsonResultInfo();
		info.setData(beanService.getByParnetId(parentId));
		info.setSuccess(true);
		info.setMessage("Succeed");
		return info;
	}
	
	@Override
	@ResponseBody
	@RequestMapping(value = { "/stems/{stem:[\\w]*}" }, method = { RequestMethod.GET })
	public JsonResultInfo stem(@PathVariable("stem") String stem) {
		// TODO Auto-generated method stub
		JsonResultInfo info = new JsonResultInfo();
		info.setData(beanService.getByStrm(stem.equalsIgnoreCase("root") ? "," : stem));
		info.setSuccess(true);
		info.setMessage("Succeed");
		return info;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = { "/tree/{stem:([\\d]+,)+}" }, method = { RequestMethod.GET })
	public JsonResultInfo treeChildren(@PathVariable("stem") String stem) {
		// TODO Auto-generated method stub
		JsonResultInfo info = new JsonResultInfo();
		info.setData(beanService.getTree(stem));
		info.setSuccess(true);
		info.setMessage("Succeed");
		return info;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = { "/tree/{parentId:[\\d]+}" }, method = { RequestMethod.GET })
	public JsonResultInfo treeChildren(@PathVariable("parentId") T parentId) {
		// TODO Auto-generated method stub
		JsonResultInfo info = new JsonResultInfo();
		info.setData(beanService.getTree(parentId));
		info.setSuccess(true);
		info.setMessage("Succeed");
		return info;
	}
}
