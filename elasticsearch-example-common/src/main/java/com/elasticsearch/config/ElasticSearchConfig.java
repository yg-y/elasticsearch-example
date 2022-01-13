package com.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author young
 * @version 1.0
 * @date 2021/4/8 11:44 上午
 * @description
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.ip}")
    String ip;

    @Value("${elasticsearch.port}")
    Integer port;

    @Bean
    public RestHighLevelClient esClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.20.205", port, "http")));
        return client;
    }
}
