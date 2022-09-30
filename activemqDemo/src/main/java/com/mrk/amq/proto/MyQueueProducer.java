package com.mrk.amq.proto;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 点对点-发消息
 */
public class MyQueueProducer {

    public static final  String QUEUE_NAME = "my.simple.test.queue";

    public static void main(String[] args){
        MyQueueProducer producer = new MyQueueProducer();
        try{
            producer.producer("hello, activemq");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void producer(String message) throws JMSException {
        QueueConnectionFactory factory = null;
        QueueConnection connection = null;
        QueueSession session = null;
        MessageProducer producer = null;
        try {
            /**
             * 1.创建连接工厂
             * 创建工厂，构造方法有三个参数：分别是用户名、密码、连接地址
             * 无参构造：有默认的连接地址，localhost
             * 一个参数：无验证模式，无用户的认证
             * 三个参数：有认证和连接地址，我这里使用三个参数的构造方法
             */
//            factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
            factory = new ActiveMQConnectionFactory("tcp://artemis-01.chengdudev.edetekapps.cn:61618");
            /**
             * 2.创建连接，有两个方法（我这里使用无参数的）
             * 无参数
             * 有参数：用户名、密码；
             */
            connection = factory.createQueueConnection();
            /**
             * 3.启动连接
             * 生产者可以不用调用start()方法启动，因为在发送消息的时候回进行检查
             * 如果未启动连接，会自动启动。
             * 如果有特殊配置，需要配置完成后再启动连接
             */
            connection.start();
            /**
             * 4.用连接创建会话
             * 有两个参数：是否需要事务、消息确认机制
             * 如果支持事务，对于生产者来说第二个参数就无效了，这个时候第二个参数建议传入Session.SESSION_TRANSACTED
             * 如果不支持事务，第二个参数有效且必须传递
             *
             * AUTO_ACKNOWLEDGE：自动确认，消息处理后自动确认（商业开发不推荐）
             * CLIENT_ACKNOWLEDGE：客户端手动确认，消费者处理后必须手动确认
             * DUPS_OK_ACKNOWLEDGE：有副本的客户端手动确认，消息可以多次处理（不建议）
             */
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            /**
             * 5.用会话创建目的地（队列）、生产者、消息
             * 队列名是队列的唯一标记
             * 创建生产者的时候可以指定目的地，也可以在发送消息的时候再指定
             */
            Destination destination = session.createQueue(QUEUE_NAME);
            producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            /**
             * 6.生产者发送消息到目的地
             */
            producer.send(textMessage);
            System.out.println("消息发送成功");
        } catch(Exception ex){
            throw ex;
        } finally {
            /**
             * 7.释放资源
             */
            if(producer != null){
                producer.close();
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