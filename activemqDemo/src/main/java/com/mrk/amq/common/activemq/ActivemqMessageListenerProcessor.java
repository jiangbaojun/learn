package com.mrk.amq.common.activemq;

import com.mrk.amq.common.AbstractMessageListenerProcessor;
import com.mrk.amq.common.annotation.MyJmsListener;
import com.mrk.amq.common.constant.MessageMode;
import com.mrk.amq.common.util.MessageUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * activemq实现，消费端监听处理
 * @author baojun.jiang
 * @date 2022-09-30 14:28
 */
@Component
@ConditionalOnClass(ActiveMQConnectionFactory.class)
public class ActivemqMessageListenerProcessor extends AbstractMessageListenerProcessor implements InitializingBean {

    @Autowired
    private ActiveMQConnectionFactory connectionFactory;
    private TopicConnection topicConnection;
    private QueueConnection queueConnection;

    @Value("${spring.application.name}")
    private String systemName;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void init() {
        try {
            queueConnection = connectionFactory.createQueueConnection();
            queueConnection.start();
            topicConnection = connectionFactory.createTopicConnection();
            topicConnection.start();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理消费监听
     * @param listener 消费贷注解
     * @param method 方法
     * @param bean 实例
     * @return void
     * @date 2022/9/30 14:33
     */
    @Override
    protected void processMyJmsListener(MyJmsListener listener, Method method, Object bean) {
        if(listener.mode().getId()== MessageMode.POINT.getId()){
            registryToPointCustomer(listener, method, bean);
        }else if(listener.mode().getId()== MessageMode.PUBLISH.getId()){
            registryPublishCustomer(listener, method, bean);
        }else if(listener.mode().getId()== MessageMode.ROUTING.getId()){
            registryRoutingCustomer(listener, method, bean);
        }
    }

    /**
     * 注册路由消费者
     * @param listener 消费贷注解
     * @param method 方法
     * @param bean 实例
     * @return void
     * @date 2023/4/28 17:13
     */
    private void registryRoutingCustomer(MyJmsListener listener, Method method, Object bean) {
        QueueSession session;
        try {
            session = queueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
            String queueName = "Consumer."+systemName+".VirtualTopic."+listener.destination();
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(new ActivemqMessageListener(method, bean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册发布订阅消费者
     * @param listener 消费贷注解
     * @param method 方法
     * @param bean 实例
     * @return void
     * @date 2022/9/30 14:31
     */
    private void registryPublishCustomer(MyJmsListener listener, Method method, Object bean) {
        TopicSession session;
        try {
            session = topicConnection.createTopicSession(false, Session.CLIENT_ACKNOWLEDGE);
            Topic topic = session.createTopic(MessageUtil.getPrefix()+listener.destination());
            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(new ActivemqMessageListener(method, bean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册点对点消费者
     * @param listener 消费贷注解
     * @param method 方法
     * @param bean 实例
     * @return void
     * @date 2022/9/30 14:32
     */
    private void registryToPointCustomer(MyJmsListener listener, Method method, Object bean) {
        QueueSession session;
        try {
            session = queueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue(MessageUtil.getPrefix()+listener.destination());
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(new ActivemqMessageListener(method, bean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听到消息处理
     * @date 2022/9/30 16:51
     */
    class ActivemqMessageListener implements MessageListener {
        private Method method;
        private Object bean;
        public ActivemqMessageListener(Method method, Object bean) {
            this.method = method;
            this.bean = bean;
        }
        @Override
        public void onMessage(Message message) {
            try {
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    String msg = textMessage.getText();
                    System.out.println("收到消息："+msg);
                    MessageUtil.invokeListen(bean, method, msg);
                }else if(message instanceof ObjectMessage){
                    //支持对象消息，暂不处理回调
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    Serializable object = objectMessage.getObject();
                    System.out.println(object);
                }
                //没有异常，确认消息
                message.acknowledge();
            } catch (Exception e) {
                //出现异常，消息消费失败
                e.printStackTrace();
            }
        }
    }

}
