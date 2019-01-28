package com.taotao.search.service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchItemServiceImpl implements SearchItemService{

    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SearchDao searchDao;

    public List<SearchItem> getItemList(){
        return searchItemMapper.getItemList();
    }

    @Override
    public TaotaoResult importItemsToIndex() {
        TaotaoResult taotaoResult = new TaotaoResult();
        List<SearchItem> itemList = searchItemMapper.getItemList();
        try{
            searchDao.addSearchItems(itemList);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(-1, "导入数据失败");
        }
        return TaotaoResult.build(1, "导入数据成功",itemList.size());
    }
    @Override
    public SearchResult search(SolrQuery query) {
        SearchResult result = new SearchResult();
        try {
            //根据query对象进行查询
            QueryResponse response = searchDao.search(query);
            //取查询结果
            SolrDocumentList solrDocumentList = response.getResults();

            //取查询结果总记录数
            long numFound = solrDocumentList.getNumFound();
            result.setRecordCount(numFound);
            List<SearchItem> itemList = new ArrayList<>();
            //把查询结果封装到SearchItem对象中
            for (SolrDocument solrDocument : solrDocumentList) {
                SearchItem item = new SearchItem();
                item.setCategory_name((String) solrDocument.get("item_category_name"));
                item.setId((String) solrDocument.get("id"));
                item.setImage((String) solrDocument.get("item_image"));
                item.setPrice((long) solrDocument.get("item_price"));
                item.setSell_point((String) solrDocument.get("item_sell_point"));
                //取高亮显示
                Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
                List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
                String title = "";
                if (list != null && list.size() > 0) {
                    title = list.get(0);
                } else {
                    title = (String) solrDocument.get("item_title");
                }
                item.setTitle(title);
                //添加到商品列表
                itemList.add(item);
            }
            //把结果添加到SearchResult中
            result.setItemList(itemList);
            //返回
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public SearchResult searchPage(String queryString, int pageIndex, int pageSize) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(queryString);
        pageSize=pageSize>1?pageSize:10;
        pageIndex = pageIndex > 1 ? pageIndex: 1;
        solrQuery.setStart((pageIndex-1)*pageSize);
        solrQuery.setRows(pageSize);
//        solrQuery.set("df", "item_title");//除了xml里配置复制域的方式，还有什么方式可以设置多个域？？
        solrQuery.set("df", "item_desc");//除了xml里配置复制域的方式，还有什么方式可以设置多个域？？
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");//怎么设置多个域高亮？？
        solrQuery.addHighlightField("item_title");//怎么设置多个域高亮？？
        solrQuery.setHighlightSimplePre("<span style='color:red;'");
        solrQuery.setHighlightSimplePost("</span>");
        SearchResult searchResult = this.search(solrQuery);
        long recordCount = searchResult.getRecordCount();
        double ceil = Math.ceil(recordCount*1.0/pageSize);
        searchResult.setTotalPages(new Double(ceil).longValue());
        return searchResult;
    }
}
