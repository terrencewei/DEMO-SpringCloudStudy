package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.service.ESProductIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/product/elasticsearch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class ESProductController {

    @Autowired
    private ESProductIndexService mESProductIndexService;



    @GetMapping("/baselineIndexing")
    public boolean baselineIndexing() {
        log.info("baselineIndexing()");
        return mESProductIndexService.bulkIndex();
    }



    @GetMapping("/partialIndexing/{offset}/{total}")
    public boolean partialIndexing(@PathVariable int offset, @PathVariable int total) {
        log.info("partialIndexing() offset:{}, total:{}", offset, total);
        return mESProductIndexService.bulkIndex(offset, total);
    }

}
