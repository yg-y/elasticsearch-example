package com.elasticsearch.api.controller;

import com.elasticsearch.api.service.IElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author young
 * @version 1.0
 * @date 2021/4/8 11:57 上午
 * @description
 */
@Slf4j
@RequestMapping("/elasticsearch")
@RestController
public class ElasticSearchController {

    @Autowired
    IElasticSearchService iElasticSearchService;

    /**
     * 储存数据到 es 中
     *
     * @return
     */
    @GetMapping("/save")
    public Object saveEsData() {
        return iElasticSearchService.saveEsData();
    }

    /**
     * 根据索引删除对应的 es 数据
     *
     * @return
     */
    @GetMapping("/delete")
    public Object deleteEsData() {
        return iElasticSearchService.deleteEsData();
    }

    /**
     * 根据索引获取对应的es数据
     *
     * @return
     */
    @GetMapping("/get")
    public Object getEsData() {
        return iElasticSearchService.getEsData();
    }

    /**
     * 增量或全量操作 es 数据
     *
     * @return
     */
    @GetMapping("/bulk")
    public Object bulkEsData() throws IOException {
        return iElasticSearchService.bulkEsData();
    }

    @GetMapping("/logs")
    public String printLogs() {
        log.info(this.getClass().getSimpleName() + " info : " + System.currentTimeMillis());
        log.warn(this.getClass().getSimpleName() + " warn : " + System.currentTimeMillis());
        log.error(this.getClass().getSimpleName() + " error : " + System.currentTimeMillis());
        return "logs";
    }
}
