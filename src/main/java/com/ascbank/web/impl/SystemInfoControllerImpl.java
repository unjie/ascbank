/**
 *
 */
package com.ascbank.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascbank.model.SystemInfo;
import com.ascbank.service.SystemInfoService;
import com.ascbank.web.SystemInfoController;
import com.ascbank.web.basis.BaseAbstractController;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/systeminfo")
public class SystemInfoControllerImpl extends BaseAbstractController<Long, SystemInfo, SystemInfoService<Long, SystemInfo>> implements SystemInfoController<Long, SystemInfo> {

	/**
	 *
	 */
	private static final long serialVersionUID = -5288810357832294398L;

}
