package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UserController {
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Autowired
    private UserService userService;
    @RequestMapping("/user/check/{data}/{type}")
    @ResponseBody
    public TaotaoResult checkUserData(@PathVariable  String data ,@PathVariable int type) {
        TaotaoResult taotaoResult = userService.checkData(data, type);
        return taotaoResult;
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult registerUser(TbUser tbUser) {
        return userService.register(tbUser);
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult loginUser(String userName, String password, HttpServletRequest request,HttpServletResponse response){
        TaotaoResult result = userService.login(userName, password);
        if (result.getStatus()==200) {
            CookieUtils.setCookie(request,response,TOKEN_KEY,result.getData().toString());
        }
        return result;
    }
    @RequestMapping(value={"/user/token/{token}"})
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback, HttpServletResponse response) throws IOException {
        TaotaoResult result = userService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
