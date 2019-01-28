package solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class CloudSolrTest {
    @Test
    public void addDocument() throws IOException, SolrServerException {
        CloudSolrServer  httpSolrServer = new CloudSolrServer("192.168.1.236:2181,192.168.1.236:2182,192.168.1.236:2183");
        httpSolrServer.setDefaultCollection("collection2");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test3");
        document.addField("title", "苹果8手机壳 手机套 硅胶保护套");
        document.addField("content", "苹果8手机壳 手机套 硅胶保护套,居家旅行必备良品，请勿与同行劣质产品对比");
        httpSolrServer.add(document);
        httpSolrServer.commit();
    }
}
