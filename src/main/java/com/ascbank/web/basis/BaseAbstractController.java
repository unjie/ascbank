package com.ascbank.web.basis;

import java.io.Serializable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ascbank.dependency.injection.InjectionInterface;
import com.ascbank.exception.ArticleException;
import com.ascbank.model.base.PKEntity;
import com.ascbank.service.BaseInterfaceService;
import com.ascbank.verify.AddCheck;

public abstract class BaseAbstractController<T extends Serializable, E extends PKEntity<T>, S extends BaseInterfaceService<T, E>>
		implements BaseInterfaceController<T, E>, InjectionInterface<S> {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= -3550102906601887804L;
	
	@Autowired
	protected S					beanService;
	private Logger				log					= LoggerFactory.getLogger(BaseAbstractController.class);
	@Autowired
	protected Properties		systemConfig;
	
	@Override
	@ResponseBody
	// @RequiresPermissions(value = "add")
	@RequestMapping(value = { "/create" }, method = RequestMethod.POST)
	public JsonResultInfo create(@RequestBody @Validated(value = { AddCheck.class }) E entity,
			BindingResult br) {
		
		JsonResultInfo info = new JsonResultInfo();
		try {
			if (br.hasErrors()) {
				throw new ArticleException(br.getAllErrors().iterator().next().getDefaultMessage());
			}
			// for (E e : entity) {
			log.debug("------------create--->{}<------------------------", entity);
			entity = this.getBeanService().add(entity);
			// }
			
			info.setSuccess(true);
			info.setMessage("{default.create.succeed}");
			info.setData(entity);
		} catch (Exception e) {
			info.setSuccess(false);
			info.setMessage(e.getMessage());
		}
		return info;
	}
	
	@Override
	@ResponseBody
	// @AutoPermissions(permission = "destroy")
	@RequestMapping(value = { "/destroy/**", "/destroy" }, method = { RequestMethod.DELETE, RequestMethod.POST })
	public JsonResultInfo destroy(@RequestBody E entity) {
		JsonResultInfo info = new JsonResultInfo();
		try {
			// for (E e : entity) {
			log.debug("---------------destroy------>{}<-----------------------", entity);
			this.getBeanService().delete(entity.getId());
			// }
			info.setSuccess(true);
			info.setMessage("{default.destroy.succeed}");
		} catch (Exception e) {
			info.setSuccess(false);
			info.setMessage(e.getMessage());
		}
		return info;
	}
	
	/**
	 * @return the beanService
	 */
	public S getBeanService() {
		return beanService;
	}
	
	@Override
	@RequestMapping(value = { "/", "/{pagename:[\\w]+}**" }, path = {}, method = RequestMethod.GET, consumes = { "text/plain", "application/*" })
	public String getHtml(@PathVariable("pagename") String pagename) {
		RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
		log.debug("=========={}=========", rm);
		return rm.value()[0] + "/" + ((pagename == null) ? "index" : pagename);
		
	}
	
	@Override
	@ResponseBody
	// @AutoPermissions(permission = "read")
	@RequestMapping(value = { "/read/{id}" }, method = { RequestMethod.GET })
	public JsonResultInfo read(@PathVariable("id") T id) {
		// TODO Auto-generated method stub
		
		if (log.isDebugEnabled()) {
			log.debug("--------read----{}---------", id.toString());
		}
		
		JsonResultInfo info = new JsonResultInfo();
		info.setData(this.getBeanService().read(id));
		info.setSuccess(true);
		info.setMessage("{default.read.succeed}");
		log.debug("------------------info =>{}-------------------", info);
		return info;
	}
	
	@Override
	@ResponseBody
	// @RequiresPermissions(value = "read")
	@RequestMapping(value = { "/reads" }, method = { RequestMethod.TRACE, RequestMethod.POST })
	public JsonResultInfo readAll(@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		JsonResultInfo info = new JsonResultInfo();
		try {
			info.setSuccess(true);
			info.setMessage("{default.update.succeed}");
			info.setData(this.getBeanService().list(pageable));
		} catch (Exception e) {
			info.setSuccess(false);
			info.setMessage(e.getMessage());
		}
		return info;
	}
	
	@Override
	public void setBean(S bean) {
		// TODO Auto-generated method stub
		this.beanService = bean;
	}
	
	@Override
	@ResponseBody
	// @AutoPermissions(permission = "update")
	@RequestMapping(value = { "/update", "/update/{id}" }, method = { RequestMethod.PUT, RequestMethod.POST }, consumes = MediaType.ALL_VALUE)
	public JsonResultInfo update(@RequestBody @Validated(value = { AddCheck.class }) E entity, BindingResult br) {
		// TODO Auto-generated method stub
		JsonResultInfo info = new JsonResultInfo();
		try {
			if (br.hasErrors()) {
				throw new ArticleException(br.getAllErrors().iterator().next().getDefaultMessage());
			}
			// for (E e : entity) {
			log.debug("-----------------------update--->{}<-------------", entity);
			entity = this.getBeanService().update(entity);
			// }
			info.setSuccess(true);
			info.setMessage("{default.update.succeed}");
			info.setData(entity);
		} catch (Exception e) {
			info.setSuccess(false);
			info.setMessage(e.getMessage());
		}
		return info;
	}
	
}
