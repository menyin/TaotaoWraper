package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemAddMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            try {
                System.out.println("来呀comeon~~~");
                System.out.println(msg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
