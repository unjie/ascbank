/**
 * 
 */
package com.ascbank.test.amqp;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author jie
 *
 */
public class TestSentAndReceive {
	public static void main(final String... args) throws Exception {
		
		//公共代码
		ApplicationContext context =
			    new GenericXmlApplicationContext("classpath:/ampq/spring-rabbit.xml");
		ConnectionFactory cf = context.getBean(ConnectionFactory.class);
		//ConnectionFactory cf = new CachingConnectionFactory();

		
		//发送端代码
		// set up the queue, exchange, binding on the broker
		RabbitAdmin admin = new RabbitAdmin(cf);
		Queue queue = new Queue("myQueue");
		admin.declareQueue(queue);//申明列队
		TopicExchange exchange = new TopicExchange("myExchange");//新建主题交换机
		admin.declareExchange(exchange);//申明主题交换机
		admin.declareBinding(//绑定申明 myQueue 列队与myExchange主题交换机
			BindingBuilder.bind(queue).to(exchange).with("foo.*"));//with 条件筛选 主题

		
		//监听端代码
		// set up the listener and container
		//新建一个简单的消息监听容器(container)
		SimpleMessageListenerContainer container =
				new SimpleMessageListenerContainer(cf);
		
		Object listener = new Object() {
			public void handleMessage(String foo) {
				System.out.println(foo);
			}
		};
		//新建 消息监听适配器(adapter) 
		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		
		// 设置监听容器的适配器(adapter)
		container.setMessageListener(adapter);
		//设置监听容器(container)的消息列队名称
		container.setQueueNames("myQueue");
		//开始监听
		container.start();

		// send something
		//发送端代码,发送消息  //如果不指定Exchange  (template.setExchange("")) 及使用默认的Exchange
		RabbitTemplate template = new RabbitTemplate(cf);//
		//template.convertAndSend("myExchange", "foo.bar", "Hello, world!");
		
		template.setExchange("myExchange");
		template.setQueue("foo.zero");
		MessageProperties mp= new MessageProperties();
		mp.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
		
		Message message= new Message("hello zero!".getBytes(),mp );
		template.send(message);//("hello zero");
		
		Thread.sleep(1000);
		container.stop();
	}
}
