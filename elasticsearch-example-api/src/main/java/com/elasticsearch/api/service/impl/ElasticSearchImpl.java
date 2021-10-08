package com.elasticsearch.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.elasticsearch.api.service.IElasticSearchService;
import com.elasticsearch.entity.UserEsEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author young
 * @version 1.0
 * @date 2021/4/8 11:58 上午
 * @description
 */
@Slf4j
@Service
public class ElasticSearchImpl implements IElasticSearchService {

    @Autowired
    RestHighLevelClient client;

    private static final String ES_INDEX_USERS = "es_index_users";
    private static final String ES_INDEX_USERS_2 = "es_index_users_2";

    @Override
    public Object saveEsData() {
        List<UserEsEntity> userEsEntities = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UserEsEntity userEsEntity = new UserEsEntity();
            userEsEntity.setId(i);
            userEsEntity.setName("young" + i);
            userEsEntity.setEmail("young.yg" + i + "@foxmail.com");
            userEsEntities.add(userEsEntity);
        }

        //构造index请求
        IndexRequest request = new IndexRequest(ES_INDEX_USERS);
        request.id("3");

        Map<String, Object> source = new HashMap<>();
        source.put("user", userEsEntities);

        request.source(JSON.toJSONString(source), XContentType.JSON);
        IndexResponse index = null;

        try {
            index = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("elasticsearch save data error : {}", e.getMessage());
        }

        return index;
    }

    @Override
    public Object deleteEsData() {
        DeleteRequest deleteRequest = new DeleteRequest(ES_INDEX_USERS_2, "user_1");
        DeleteResponse delete = null;
        try {
            delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("elasticsearch delete data error : {}", e.getMessage());
        }
        return delete;
    }

    @Override
    public Object getEsData() {
        GetRequest getRequest = new GetRequest(ES_INDEX_USERS, "3");
        GetResponse response = null;
        try {
            response = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("elasticsearch get data error : {}", e.getMessage());
        }
        return response;
    }

    @Override
    public Object bulkEsData() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 5; i < 10; i++) {
            UserEsEntity userEsEntity = new UserEsEntity();
            userEsEntity.setId(i);
            userEsEntity.setName("young" + i);
            userEsEntity.setEmail("young.yg" + i + "@foxmail.com2");
            UpdateRequest updateRequest = new UpdateRequest(ES_INDEX_USERS_2, "user_" + userEsEntity.getId());
            updateRequest.doc(JSONObject.toJSONString(userEsEntity), XContentType.JSON);
            updateRequest.upsert(XContentType.JSON);
            bulkRequest.add(updateRequest);
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulk;
    }
}
