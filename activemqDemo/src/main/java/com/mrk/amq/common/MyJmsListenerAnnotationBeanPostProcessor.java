package com.mrk.amq.common;

import com.mrk.amq.common.annotation.MyJmsListener;
import com.mrk.amq.common.util.MessageAnnotationUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;


/**
 * 自定义MyJmsListener注解解析
 * 也可以继承默认解析org.springframework.jms.annotation.JmsListenerAnnotationBeanPostProcessor
 * @author baojun.jiang
 * @date 2022/9/28 16:25
 */
@Component
public class MyJmsListenerAnnotationBeanPostProcessor implements Ordered, MergedBeanDefinitionPostProcessor {

	int LOWER_LEVEL = 100000;

	@Autowired
	private ActiveMQConnectionFactory connectionFactory;
	@Autowired(required = false)
	private AbstractMessageListenerProcessor abstractMessageListenerProcessor;


	@Override
	public int getOrder() {
		return LOWER_LEVEL;
	}

	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(abstractMessageListenerProcessor==null){
			return bean;
		}
		if (bean instanceof AopInfrastructureBean || bean instanceof JmsListenerContainerFactory ||
				bean instanceof JmsListenerEndpointRegistry) {
			return bean;
		}
		Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
		if (AnnotationUtils.isCandidateClass(targetClass, MyJmsListener.class)) {
			Map<Method, Set<MyJmsListener>> annotatedMethods = MessageAnnotationUtil.parse(bean);
			if (!annotatedMethods.isEmpty()) {
				annotatedMethods.forEach((method, listeners) ->
						listeners.forEach(listener -> abstractMessageListenerProcessor.processMyJmsListener(listener, method, bean)));
			}
		}
		return bean;
	}

	private void processMyJmsListener(MyJmsListener listener, Method method, Object bean) {
		// 创建连接
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			// 创建会话
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// 创建目的地 (主题 or 队列)
			Destination destination = session.createTopic(listener.destination());

			// 创建消息消费者
			MessageConsumer consumer = session.createConsumer(destination);

			System.out.println("服务端开始监听消息。。。");

			consumer.setMessageListener(message -> {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = null;
					try {
						text = textMessage.getText();
					} catch (JMSException e) {
						e.printStackTrace();
					}
					System.out.println("接收到的消息为: " + text);
				} else {
					System.out.println("消息为空");
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}