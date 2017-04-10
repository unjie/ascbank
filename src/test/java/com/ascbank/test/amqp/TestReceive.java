/**
 * 
 */
package com.ascbank.test.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author jie
 *
 */
public class TestReceive {
	public static void main(String[] args) {
		ApplicationContext context =
			    new GenericXmlApplicationContext("classpath:/ampq/spring-rabbit.xml");
		AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
		System.out.println("Received: " + amqpTemplate.receiveAndConvert("myqueue"));
	}
}
