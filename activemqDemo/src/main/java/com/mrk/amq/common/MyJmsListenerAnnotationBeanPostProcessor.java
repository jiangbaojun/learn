package com.mrk.amq.common;

import com.mrk.amq.common.annotation.MyJmsListener;
import com.mrk.amq.common.util.MessageUtil;
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
			Map<Method, Set<MyJmsListener>> annotatedMethods = MessageUtil.parse(bean);
			if (!annotatedMethods.isEmpty()) {
				annotatedMethods.forEach((method, listeners) ->
						listeners.forEach(listener -> abstractMessageListenerProcessor.processMyJmsListener(listener, method, bean)));
			}
		}
		return bean;
	}
}