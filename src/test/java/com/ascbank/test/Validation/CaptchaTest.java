/**
 *
 */
package com.ascbank.test.Validation;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ascbank.model.User;

/**
 * @author jie
 *
 */
public class CaptchaTest {
	
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void Test() {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("123456");
		user.setSave(true);
		user.setCaptcha("capt");

		Set<ConstraintViolation<User>> constraintViolations =
				validator.validate(user);
		
		assertEquals(0, constraintViolations.size());
	}
}
