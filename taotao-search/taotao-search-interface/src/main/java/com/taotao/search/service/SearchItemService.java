package com.taotao.search.service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import org.apache.solr.client.solrj.SolrQuery;

import java.io.IOException;
import java.util.List;

public interface SearchItemService {
    List<SearchItem> getItemList();
    TaotaoResult importItemsToIndex();
    SearchResult search(SolrQuery query);
    SearchResult searchPage(String queryString, int pageIndex, int pageSize);
}
