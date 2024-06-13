package com.mrk.amq.proto;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 发布订阅-发消息
 */
public class MyTopicProducer {

  public static void main(String[] args) {
    TopicSession session = null;
    TopicConnection conn = null;
    try {
//      String brokerUrl = "tcp://artemis-01.chengdudev.edetekapps.cn:61618";
      String brokerUrl = "tcp://192.168.230.90:61616";

//      String topicName = "my.ps.test.queue";
      String topicName = "VirtualTopic.test";

      ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
      conn = factory.createTopicConnection();
      conn.start();
      session = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
      // 创建消息队列
      Topic topic = session.createTopic(topicName);
      // 创建消息发送者
      TopicPublisher publisher = session.createPublisher(topic);
      // 设置持久化模式 NON_PERSISTENT不开启  PERSISTENT 开启 默认是开启
      publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
      MapMessage mapMessage = session.createMapMessage();
      mapMessage.setString("name", "xiaoming");
      mapMessage.setString("address", "shanghai");
      publisher.send(mapMessage);

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("访问ActiveMQ服务发生错误!!");
    } finally {
      
      try {
        // 回收会话资源
        if (null != session) {
          session.close();
        }
      } catch (JMSException e) {
        e.printStackTrace();
      }
      try {
        // 回收链接资源
        if (null != conn) {
          conn.close();
        }
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
  }
}