package com.taotao.portal.listener;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemAddMessageListener1 implements MessageListener {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfig freeMarkerConfigurer;

    @Value("${ftlBasePath}")
    private String ftlBasePath;


    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            try {
                String text = msg.getText();
                String itemId = text.split("：")[1];
//                TbItem tbItem = itemService.getItemById(Long.parseLong(itemId));
                Thread.sleep(1000);//暂停1s以放商品添加事务未提交
                TbItem tbItem = new TbItem();//模拟测试数据而已
                tbItem.setTitle("华为手机就是牛B~~");
                tbItem.setId((long) 123456789);

                //kai开始生成静态页面
                Configuration configuration = freeMarkerConfigurer.getConfiguration();
                Template template = configuration.getTemplate("item.ftl");
//                Template template = configuration.getTemplate("item.html");//测试费ftl的文件能否作为模板
                Map map= new HashMap<>();
                map.put("item", tbItem);
                FileWriter fileWriter = new FileWriter(new File(ftlBasePath + "/item-"+tbItem.getId() + ".html"));
                template.process(map, fileWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
