/**
 * 
 */
package com.ascbank.test.ampq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author jie
 *
 */
public class TestAMPQ {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context =
			    new GenericXmlApplicationContext("classpath:/ampq/spring-rabbit.xml");
			AmqpTemplate template = context.getBean(AmqpTemplate.class);
			template.convertAndSend("myqueue", "foo");
			String foo = (String) template.receiveAndConvert("myqueue");
	}
	
}
