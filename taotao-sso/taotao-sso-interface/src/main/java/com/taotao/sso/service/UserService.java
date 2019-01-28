package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;

public interface UserService {
    TaotaoResult checkData(String data,int type);
    TaotaoResult register(TbUser tbUser);
    TaotaoResult login(String userName, String password);
    TaotaoResult getUserByToken(String token);

}