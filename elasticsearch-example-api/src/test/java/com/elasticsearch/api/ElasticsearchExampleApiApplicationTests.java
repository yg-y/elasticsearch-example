package com.elasticsearch.api;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.api.service.IElasticSearchService;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ElasticsearchExampleApiApplicationTests {

    //http://192.168.20.205:9200/
    @Value("${elasticsearch.ip}")
    String ip;

    @Value("${elasticsearch.port}")
    Integer port;

    @Autowired
    RestHighLevelClient client;

    @Test
    void contextLoads() {
        System.err.println(ip);
        System.err.println(port);
    }

    @Test
    void saveEsData() throws IOException {
        //构造index请求
        IndexRequest request = new IndexRequest("es_test");
        //设置id，如果不传，则会自动生成一个id
        request.id("2");
        //第一种传数据方法
//        request.source("name","张三","age",18,"gender","男");

        //第二种，将对象转为json字符串作为传输内容
        User user = new User();
        user.setName("Jessie");
        user.setAge(18);
        user.setGender("男");
        String userString = JSON.toJSONString(user);
        request.source(userString, XContentType.JSON);

        IndexResponse index = client.index(request, RequestOptions.DEFAULT);
        System.out.println(index);
    }

    @Test
    void getData() throws IOException {
        //第一个参数是index，第二个参数是id
        GetRequest getRequest = new GetRequest("es_test", "2");
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Autowired
    IElasticSearchService iElasticSearchService;

    @Test
    void delete() {
        iElasticSearchService.deleteEsData();
    }

    @Data
    class User {
        String name;
        String gender;
        int age;
    }

    @Test
    void test_query() throws IOException {
        RestHighLevelClient clients = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.20.205", Integer.parseInt("9200"), "http")));

        SearchRequest searchRequest = new SearchRequest("newoms-pro-order-target");
        searchRequest.types("doc");
        searchRequest.searchType(SearchType.DEFAULT);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(QueryBuilders.termQuery("salePlatform", "SE"));
        queryBuilder.filter(QueryBuilders.rangeQuery("cancelTime").gt("1641004661000").lt("1641436661000"));
        qb.filter(queryBuilder);
        searchSourceBuilder.query(qb);

        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = clients.search(searchRequest, RequestOptions.DEFAULT);


        System.err.println(search);

    }
}
