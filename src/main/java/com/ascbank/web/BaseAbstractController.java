package com.ascbank.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
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
import com.ascbank.verify.FormatCheck;

public abstract class BaseAbstractController<T extends Serializable, E extends PKEntity<T>, S extends BaseInterfaceService<T, E>>
		implements BaseInterfaceController<T, E>, InjectionInterface<S> {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= -3550102906601887804L;
	
	private S					beanService;
	private Logger				log					= LoggerFactory.getLogger(BaseAbstractController.class);
	@Autowired
	protected Properties		systemConfig;
	
	@Override
	@RequestMapping(value = { "/create" }, method = RequestMethod.POST)
	@ResponseBody
	// @RequiresPermissions(value = "add")
	public JsonResultInfo create(@RequestBody @Validated(value = { FormatCheck.class, AddCheck.class }) E entity,
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
	@RequestMapping(value = { "/destroy/**", "/destroy" }, method = RequestMethod.DELETE)
	@ResponseBody
	// @EntityPermissions(permission = "destroy")
	public JsonResultInfo destroy(
			@RequestBody E entity/* , @PathVariable("id") T id */) {
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
	public String getHtml(HttpServletRequest request, @PathVariable("pagename") String pagename) {
		RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
		log.debug("=========={}=========", rm);
		return rm.value()[0] + "/" + ((pagename == null) ? "index" : pagename);
		
	}
	
	@Override
	@ResponseBody
	@RequestMapping(value = { "/read/{id}" }, method = { RequestMethod.GET })
	// @EntityPermissions(permission="read")
	public JsonResultInfo read(@PathVariable("id") T id) {
		// TODO Auto-generated method stub
		
		if (log.isDebugEnabled()) {
			log.debug("--------read----{}---------", id.toString());
		}
		
		JsonResultInfo info = new JsonResultInfo();
		info.setData(this.getBeanService().read(id));
		info.setSuccess(true);
		info.setMessage("{default.read.succeed}");
		return info;
	}
	
	@Override
	@RequestMapping(value = { "/reads" }, method = { RequestMethod.TRACE })
	@ResponseBody
	// @RequiresPermissions(value="read")
	public List<E> readAll(Integer page, Integer start, Integer limit, String property, String direction) {
		// TODO Auto-generated method stub
		if (page == null || limit == null) {
			return new ArrayList<E>();
		}
		Sort sort = StringUtils.isEmpty(property) && StringUtils.isEmpty(direction) ? null
				: new Sort(Direction.fromStringOrNull(direction), property);
		PageRequest pr = sort != null ? new PageRequest(page, limit, sort) : new PageRequest(page, limit);
		return this.getBeanService().list(pr).getContent();
	}
	
	@Override
	@Autowired
	public void setBean(S bean) {
		// TODO Auto-generated method stub
		this.setBeanService(bean);
	}
	
	/**
	 * @param beanService
	 *            the beanService to set
	 */
	public void setBeanService(S beanService) {
		this.beanService = beanService;
	}

	@Override
	@RequestMapping(value = { "/update", "/update/{id}" }, method = RequestMethod.PUT)
	@ResponseBody
	// @EntityPermissions(permission = "update")
	public JsonResultInfo update(@RequestBody @Validated(value = { FormatCheck.class, AddCheck.class }) E entity,
			BindingResult br) {
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
