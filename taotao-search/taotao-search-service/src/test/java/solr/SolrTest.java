package solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolrTest {

    @Test
    public void addDocument() throws IOException, SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.1.236:8090/solr");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test3");
        document.addField("title", "苹果8手机壳 手机套 硅胶保护套");
        document.addField("content", "苹果8手机壳 手机套 硅胶保护套,居家旅行必备良品，请勿与同行劣质产品对比");
        httpSolrServer.add(document);
        httpSolrServer.commit();
    }
    @Test
    public void deleteDocumentById() throws IOException, SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.1.236:8090/solr");
        httpSolrServer.deleteById("test2");
        httpSolrServer.commit();
    }
    @Test
    public void deleteDocumentByQuery() throws IOException, SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.1.236:8090/solr");
        httpSolrServer.deleteByQuery("title:苹果7");
        httpSolrServer.commit();
    }
    @Test
    public void queryDocument() throws IOException, SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.1.236:8090/solr");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("title:苹果8");
        QueryResponse response = httpSolrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();
        for (SolrDocument doc : results) {
            System.out.println(doc.get("id"));
            System.out.println(doc.get("title"));
            System.out.println(doc.get("content"));
        }
        httpSolrServer.commit();
    }
    @Test
    public void queryDocumeLigthting() throws IOException, SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.1.236:8090/solr");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("苹果7");
        solrQuery.set("df","title");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("title");
        solrQuery.setHighlightSimplePre("<em style='color:red;'>");
        solrQuery.setHighlightSimplePost("</em>");
        QueryResponse response = httpSolrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();
        for (SolrDocument doc : results) {

            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(doc.get("id")).get("title");
            String itemTitle = null;
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) doc.get("title");
            }
            System.out.println(itemTitle);
            System.out.println(doc.get("title"));
        }
        httpSolrServer.commit();
    }


}
