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
public class TestSent {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context =
			    new GenericXmlApplicationContext("classpath:/ampq/spring-rabbit.xml");
			AmqpTemplate template = context.getBean(AmqpTemplate.class);//使用默认交换机 Default Exchange
			template.convertAndSend("myqueue", "foo");//发送"foo"消息到myqueue 列队
		//	String foo = (String) template.receiveAndConvert("myqueue");//接受转换myqueue列队消息
	}
	
}
