package com.aaxis.microservice.training.demo1.config;

/**
 * Created by terrence on 2018/10/17.
 */
public class ESConfig {

    private String indexName;
    private String indexType;
    private int    indexBatchSize;
    private int    queryFromDBBatchSize;



    @Override
    public String toString() {
        return String.format("ESConfig (indexBatchSize=%s, indexName=%s, indexType=%s, queryFromDBBatchSize=%s)",
                this.indexBatchSize, this.indexName, this.indexType, this.queryFromDBBatchSize);
    }



    public String getIndexName() {
        return indexName;
    }



    public void setIndexName(String pIndexName) {
        indexName = pIndexName;
    }



    public String getIndexType() {
        return indexType;
    }



    public void setIndexType(String pIndexType) {
        indexType = pIndexType;
    }



    public int getIndexBatchSize() {
        return indexBatchSize;
    }



    public void setIndexBatchSize(int pIndexBatchSize) {
        indexBatchSize = pIndexBatchSize;
    }



    public int getQueryFromDBBatchSize() {
        return queryFromDBBatchSize;
    }



    public void setQueryFromDBBatchSize(int pQueryFromDBBatchSize) {
        queryFromDBBatchSize = pQueryFromDBBatchSize;
    }
}