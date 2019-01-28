package com.taotao.content.service.impl;

import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private JedisClient jedisClient;

    public String getContentByCid(long cid) {
        try {
            String val = jedisClient.get("cny");
            if (StringUtils.isNotBlank(val)) {
                return val;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //请求数据库数
        String data = "社会主义好";
        //保存至redis
        try {
            jedisClient.set("cny", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回数据
        return data;
    }
}
