package com.aaxis.microservice.training.demo1.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * Created by terrence on 2018/10/13.
 */
@Slf4j
public abstract class RedisCache<T> {

    public static final int TIMEOUT_SECOUNDS = 300;

    @Autowired
    protected StringRedisTemplate mStringRedisTemplate;



    public T get(String prefix, String id) {
        String key = prefix + "_" + id;
        ValueOperations<String, String> operations = mStringRedisTemplate.opsForValue();
        boolean hasKey = mStringRedisTemplate.hasKey(key);
        if (hasKey) {
            String valJson = operations.get(key);
            log.info("get value from Redis:[key:{}, value:{}]", key, valJson);
            T val = JSONObject.parseObject(valJson, getClazz());
            return val;
        }
        T value = getById(id);
        if (value != null) {
            String valJson = JSONObject.toJSONString(value);
            log.info("set value to Redis:[key:{}, value:{}]", key, valJson);
            operations.set(key, valJson, TIMEOUT_SECOUNDS, TimeUnit.SECONDS);
            // if update value, not 'set' value, need also update the timeout
            // redisTemplate.expire(key, TIMEOUT_SECOUNDS, TimeUnit.SECONDS);
        }
        log.info("get value not from redis by id:{}", id);
        return value;
    }



    protected abstract <T> Class<T> getClazz();

    protected abstract T getById(String pId);
}