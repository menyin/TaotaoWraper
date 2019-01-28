package com.taotao.portal.controller;

import com.taotao.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String index(){
        String result = contentService.getContentByCid(111);
        System.out.println(result);
        return "index";
    }
}
