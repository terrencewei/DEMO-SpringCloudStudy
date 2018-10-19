package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.config.ESConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrence on 2018/10/17.
 */
@Slf4j
public abstract class ESIndexService<T> {

    @Autowired
    private ElasticsearchTemplate mESTemplate;



    /**
     * Bulk indexing for all
     *
     * @return
     */
    public boolean bulkIndex() {
        return bulkIndex(0, queryRecordTotal());
    }



    /**
     * bulk index by offset and size
     *
     * @param pOffset
     * @param pSize
     * @return
     */
    public boolean bulkIndex(int pOffset, long pSize) {
        try {
            ESConfig config = getConfig();
            log.info("config:{}", config);
            String indexName = config.getIndexName();
            if (StringUtils.isBlank(indexName)) {
                log.error("indexName is empty");
                return false;
            }
            if (!mESTemplate.indexExists(indexName)) {
                mESTemplate.createIndex(indexName);
            }
            List<IndexQuery> queries = new ArrayList<>();
            log.info("Starting bulkIndex for [{}] record.", pSize);
            long queryBatchSize = config.getQueryFromDBBatchSize() < pSize ? config.getQueryFromDBBatchSize() : pSize;
            long queryTimes = pSize == queryBatchSize ? 1 : (pSize / queryBatchSize) + 1;
            int indexBatchSize = config.getIndexBatchSize();
            for (int i = 0; i < queryTimes; i++) {
                long offset = i * queryBatchSize;
                List<T> records = queryRecords(offset, i == queryTimes - 1 ? pSize - offset : queryBatchSize);
                int indexCounter = 0;
                IndexQuery query = null;
                for (T record : records) {
                    query = new IndexQuery();
                    query.setId(getRecordId(record));
                    query.setSource(getRecordContent(record));
                    query.setIndexName(indexName);
                    query.setType(config.getIndexType());

                    queries.add(query);

                    if (indexCounter % indexBatchSize == 0) {
                        mESTemplate.bulkIndex(queries);
                        queries.clear();
                        log.info("bulkIndex indexed indexBatchSize{} record");
                    }
                    indexCounter++;
                }
                if (queries.size() > 0) {
                    mESTemplate.bulkIndex(queries);
                }
                mESTemplate.refresh(indexName);
                log.info("bulkIndex [{}]% precent finished, remaining [{}] record",
                        String.format("%.2f", ((float) (i + 1) / queryTimes * 100)), pSize - offset);
            }
            return true;
        } catch (Exception e) {
            log.error("error occurs during index:{}", ExceptionUtils.getStackTrace(e));
        }
        return false;
    }



    protected abstract ESConfig getConfig();

    protected abstract long queryRecordTotal();

    protected abstract List<T> queryRecords(long pOffset, long pQueryBatchSize);

    protected abstract String getRecordId(T pRecord);

    protected abstract String getRecordContent(T pRecord);

}