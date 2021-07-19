package com.elasticsearch.api.service;

import java.io.IOException;

/**
 * @author young
 * @version 1.0
 * @date 2021/4/8 11:58 上午
 * @description
 */
public interface IElasticSearchService {

    Object saveEsData();

    Object deleteEsData();

    Object getEsData();

    Object bulkEsData() throws IOException;
}
