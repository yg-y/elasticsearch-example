# Elasticsearch Example

> Elasticsearch 是一个分布式、RESTful 风格的搜索和数据分析引擎，能够解决不断涌现出的各种用例。 作为 Elastic Stack 的核心，它集中存储您的数据，帮助您发现意料之中以及意料之外的情况。

[Elasticsearch 官网](https://www.elastic.co/cn/elasticsearch/)

- elasticsearch-example-common

```text
公共模块，直接引入即可
```

- elasticsearch-example-api

```text
api 模块，对外暴露接口
```

# Elasticsearch 配置

- ElasticSearchConfig

```java
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
                        new HttpHost(ip, port, "http")));
        return client;
    }
}

```

- pom.xml

```xml

<properties>
    <version.elasticsearch>7.8.0</version.elasticsearch>
    <version.alibaba.fastjson>1.2.75</version.alibaba.fastjson>
</properties>

<dependencyManagement>
    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.elasticsearch.client/elasticsearch-rest-high-level-client -->
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>${version.elasticsearch}</version>
        </dependency>
    
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${version.alibaba.fastjson}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

# Elasticsearch 安装

> 使用 docker-compose 方式安装，需要提前安装好 docker、docker-compose 环境

- docker-compose-es.yml

```yaml
# es.yml
version: '3.2'
services:
  elasticsearch:
    image: elasticsearch:7.8.0
    container_name: elk-es
    restart: always
    environment:
      # 开启内存锁定
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      # 指定单节点启动
      - discovery.type=single-node
    ulimits:
      # 取消内存相关限制  用于开启内存锁定
      memlock:
        soft: -1
        hard: -1
    volumes:
      - ../logs/data:/usr/share/elasticsearch/data
      - ../logs:/usr/share/elasticsearch/logs
      - ../logs/plugins:/usr/share/elasticsearch/plugins
    ports:
      - 9200:9200
    networks:
      - young
  kibana:
    image: kibana:7.8.0
    container_name: elk-kibana
    restart: always
    environment:
      ELASTICSEARCH_HOSTS: http://elk-es:9200
      I18N_LOCALE: zh-CN
    ports:
      - 5601:5601
    networks:
      - young

networks:
  young:
    driver: bridge
```

# 访问

- http://xxx.x.x.x:9200
- http://xxx.x.x.x:5601