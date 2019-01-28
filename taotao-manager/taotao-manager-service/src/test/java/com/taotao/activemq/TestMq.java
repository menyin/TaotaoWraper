package com.taotao.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class TestMq {
    @Test
    public void queueProvider() throws JMSException{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-activemq.xml");
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        Destination destination = (Destination) applicationContext.getBean("test-queue");
        jmsTemplate.send(destination,new MessageCreator(){
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("这是第1个spring整合后的queue");
                return textMessage;
            }
        });

    }
}
