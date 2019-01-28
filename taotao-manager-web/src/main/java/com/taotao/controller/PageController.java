package com.taotao.controller;

import com.alibaba.dubbo.common.serialize.support.json.FastJsonObjectInput;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }

    @RequestMapping("/importDocs")
    @ResponseBody
    public String tesetImportDocuments() {
        TaotaoResult taotaoResult = searchItemService.importItemsToIndex();
        return taotaoResult.getMsg();
    }

    @RequestMapping("/queryDocs")
    @ResponseBody
    public TaotaoResult tesetQueryDocuments(String queryString, int pageIndex, int pageSize) {
        SearchResult searchResult = searchItemService.searchPage(queryString, pageIndex, pageSize);
/*
        if (searchResult.getRecordCount() > 0) {
            return TaotaoResult.build(200, "ok", searchResult).getMsg();
        } else {
            return TaotaoResult.build(500, "failed").getMsg();
        }
*/
        return TaotaoResult.build(200, "ok", searchResult);
    }

    @RequestMapping("/testGlobalException")
    public String testGlobalException() {
        int a=1/0;
        return "index";
    }

}

