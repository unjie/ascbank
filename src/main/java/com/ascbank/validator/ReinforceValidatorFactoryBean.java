package com.ascbank.validator;

import java.util.Collection;

import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

//@Component("validator")
public class ReinforceValidatorFactoryBean extends LocalValidatorFactoryBean {
	
	/// private static final Logger log =
	/// LoggerFactory.getLogger(ReinforceValidatorFactoryBean.class);
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		// log.info("==============>validate(Object target, Errors errors) >>>"
		// + target.toString());
		Object[] obj = target.getClass().isArray() ? (Object[]) target
				: (target instanceof Collection ? ((Collection<?>) target).toArray() : null);
		if (obj != null) {
			for (Object o : obj) {
				super.validate(o, errors);
				// log.info("====>" + o.toString());
				if (errors.hasErrors()) {
					return;
				}
			}
		} else {
			super.validate(target, errors);
		}
	}
	
	@Override
	public void validate(Object target, Errors errors, Object... validationHints) {
		// TODO Auto-generated method stub
		// log.info("==============>validate(Object target, Errors errors,
		// Object... validationHints) >>>"
		// + target.toString());
		Object[] obj = target.getClass().isArray() ? (Object[]) target
				: (target instanceof Collection ? ((Collection<?>) target).toArray() : null);
		if (obj != null) {
			for (Object o : obj) {
				super.validate(o, errors, validationHints);
				// log.info("====>" + o.toString());
				if (errors.hasErrors()) {
					return;
				}
			}
		} else {
			super.validate(target, errors, validationHints);
		}
		
	}
	
}
