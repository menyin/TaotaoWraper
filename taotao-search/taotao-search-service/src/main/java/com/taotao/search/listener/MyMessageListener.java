package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            System.out.println("接收到spring整合q啦");
            TextMessage msg = (TextMessage) message;
            System.out.println(msg);
        }
    }
}
