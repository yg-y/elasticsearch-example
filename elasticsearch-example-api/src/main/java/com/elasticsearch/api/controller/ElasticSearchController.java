package com.elasticsearch.api.controller;

import com.elasticsearch.api.service.IElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author young
 * @version 1.0
 * @date 2021/4/8 11:57 上午
 * @description
 */
@RequestMapping("/elasticsearch")
@RestController
public class ElasticSearchController {

    @Autowired
    IElasticSearchService iElasticSearchService;

    @GetMapping("/save")
    public Object saveEsData() {
        return iElasticSearchService.saveEsData();
    }

    @GetMapping("/delete")
    public Object deleteEsData() {
        return iElasticSearchService.deleteEsData();
    }

    @GetMapping("/get")
    public Object getEsData() {
        return iElasticSearchService.getEsData();
    }

    @GetMapping("/update")
    public Object updateEsData() {
        return iElasticSearchService.updateEsData();
    }
}
