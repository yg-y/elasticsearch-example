package com.elasticsearch.api;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.api.service.IElasticSearchService;
import lombok.Data;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootTest
class ElasticsearchExampleApiApplicationTests {

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
}
