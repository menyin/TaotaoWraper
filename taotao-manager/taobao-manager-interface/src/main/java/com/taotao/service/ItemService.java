package com.taotao.service;

import com.taotao.pojo.TbItem;

public interface ItemService {
    TbItem getItemById(long itemId);
    Object getItemList(Integer page,Integer rows);
    Object addItem(TbItem tbItem,String desc);
}
