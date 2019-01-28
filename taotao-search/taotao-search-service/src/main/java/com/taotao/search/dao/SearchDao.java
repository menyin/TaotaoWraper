package com.taotao.search.dao;

import com.taotao.common.pojo.SearchItem;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

public interface SearchDao {
    QueryResponse search(SolrQuery query) throws SolrServerException;
    void addSearchItems(List<SearchItem> searchItems) throws Exception;
}
