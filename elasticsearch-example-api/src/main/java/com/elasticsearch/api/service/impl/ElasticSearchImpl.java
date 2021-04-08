package com.elasticsearch.api.service.impl;

import com.elasticsearch.api.service.IElasticSearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author young
 * @version 1.0
 * @date 2021/4/8 11:58 上午
 * @description
 */
@Service
public class ElasticSearchImpl implements IElasticSearchService {

    @Autowired
    RestHighLevelClient client;

    @Override
    public Object saveEsData() {
        return null;
    }

    @Override
    public Object deleteEsData() {
        return null;
    }

    @Override
    public Object getEsData() {
        return null;
    }

    @Override
    public Object updateEsData() {
        return null;
    }
}
