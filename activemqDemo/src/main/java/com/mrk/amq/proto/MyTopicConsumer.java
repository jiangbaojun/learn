package com.mrk.amq.proto;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 发布订阅-收消息
 */
public class MyTopicConsumer {

    public static void main(String[] args) {
        reciveHelloFormActiveMq();
    }

  public static void reciveHelloFormActiveMq() {
      TopicSession session = null;
      TopicConnection conn = null;
      try {
        TopicConnectionFactory factory = new ActiveMQConnectionFactory("tcp://artemis-01.chengdudev.edetekapps.cn:61618");
        conn = factory.createTopicConnection();
        conn.start();
        session = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建消息队列
        Topic topic = session.createTopic("my.ps.test.queue");
        // 创建消息接受者
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
          @Override
          public void onMessage(Message msg) {
            if (msg != null) {
                          MapMessage map = (MapMessage) msg;
                          try {
                             System.out.println(map.getString("name") + "接收#" + map.getString("address"));
                          } catch (JMSException e) {
                              e.printStackTrace();
                          }
                      }
          }
        });
          System.out.println("开始监听消息");
         // 休眠100s再关闭
          Thread.sleep(1000 * 100);
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