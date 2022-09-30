package com.mrk.amq.proto;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 点对点-收消息
 */
public class MyQueueConsumer {

    public static final  String QUEUE_NAME = "my.simple.test.queue";

    public static void main(String[] args){
        MyQueueConsumer consumer = new MyQueueConsumer();
        try{
            consumer.consumer();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void consumer() throws JMSException {
        QueueConnectionFactory factory = null;
        QueueConnection connection = null;
        QueueSession session = null;
        MessageConsumer consumer = null;
        try {
            //factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
            factory = new ActiveMQConnectionFactory("tcp://artemis-01.chengdudev.edetekapps.cn:61618");
            connection = factory.createQueueConnection();
            /**
             * 消费者必须启动连接，否则无法消费
             */
            connection.start();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(QUEUE_NAME);
            consumer = session.createConsumer(destination);
            /**
             * 获取队列消息，同步
             */
//            Message message = consumer.receive();
//            String text = ((TextMessage) message).getText();
//            System.out.println("消息消费成功：" + text);

            /**
             * 获取队列消息，异步
             */
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        //需要手动确认消息
                        message.acknowledge();
                        TextMessage om = (TextMessage) message;
                        String data = om.getText();
                        System.out.println(data);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("开始监听消息");
            // 休眠100s再关闭
            Thread.sleep(1000 * 100);
        } catch(Exception ex){
            ex.printStackTrace();
        } finally {
            /**
             * 7.释放资源
             */
            if(consumer != null){
                consumer.close();
            }
            
            if(session != null){
                session.close();
            }

            if(connection != null){
                connection.close();
            }
        }
    }
}