package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_SESSION}")
    private String USER_SESSION;
    @Value("${USER_SESSION_EXPIRE}")
    private Integer USER_SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String data, int type) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        if (type == 1) {
            criteria.andUsernameEqualTo(data);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(data);
        } else if (type == 3) {
            criteria.andEmailEqualTo(data);
        } else {
            return TaotaoResult.build(400, "参数type包含非法数据");
        }
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if (tbUsers == null || tbUsers.size() == 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult register(TbUser tbUser) {

        if (StringUtils.isBlank(tbUser.getUsername())) {
            return TaotaoResult.build(400, "用户名不能为空", false);
        }
        if (StringUtils.isBlank(tbUser.getPassword())) {
            return TaotaoResult.build(400, "密码不能为空", false);
        }
        if (!(boolean) checkData(tbUser.getUsername(), 1).getData()) {
            return TaotaoResult.build(400, "用户名重复", false);
        }
        if (StringUtils.isNotBlank(tbUser.getPhone()) && !(boolean) checkData(tbUser.getPhone(), 2).getData()) {
            return TaotaoResult.build(400, "手机号重复", false);
        }
        if (StringUtils.isNotBlank(tbUser.getEmail()) && !(boolean) checkData(tbUser.getEmail(), 3).getData()) {
            return TaotaoResult.build(400, "邮箱重复", false);
        }
        String mdbStr = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(mdbStr);

        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUserMapper.insert(tbUser);
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult login(String userName, String password) {

        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(userName);
        criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if (tbUsers != null && tbUsers.size() == 1) {
            String token = UUID.randomUUID().toString();
            String json = JsonUtils.objectToJson(tbUsers.get(0));
            jedisClient.set(USER_SESSION + ":" + token, json);
            jedisClient.expire(USER_SESSION + ":" + token, USER_SESSION_EXPIRE);
            return TaotaoResult.build(200,"登录成功",token);
        } else {
            return TaotaoResult.build(400, "用户名或密码错误", false);
        }
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if (StringUtils.isNotBlank(json)) {
            jedisClient.expire(USER_SESSION + ":" + token, USER_SESSION_EXPIRE);
            TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
            return TaotaoResult.ok(tbUser);
        } else {
            return TaotaoResult.build(400, "token无效或过期", false);
        }
    }
}
