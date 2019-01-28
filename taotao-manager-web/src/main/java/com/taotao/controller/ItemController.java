package com.taotao.controller;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable long itemId){
        TbItem item = itemService.getItemById(536563);
        return item;
    }
    @RequestMapping("/item/list")
    @ResponseBody
    public Object getItemList(Integer page,Integer rows){
        TbItem item = itemService.getItemById(536563);
        Object itemList = itemService.getItemList(page, rows);
        return itemList;
    }
    @RequestMapping("/item/add")
    @ResponseBody
    public Object getItemList(TbItem tbItem,String desc){
        //造数据
        tbItem.setTitle("苹果手机6plus");
        desc = "很好用，但非常贵，买不起~";
        Object result = itemService.addItem(tbItem, desc);
        return result;
    }


}
