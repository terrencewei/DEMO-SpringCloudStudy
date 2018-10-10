package com.aaxis.microservice.training.demo1.util;

import java.util.Collection;

/**
 * Created by terrence on 2018/10/10.
 */
public class CommonUtils {

    public static boolean isEmpty(Collection pCollection) {
        return pCollection == null || pCollection.isEmpty();
    }



    public static boolean isNotEmpty(Collection pCollection) {
        return !isEmpty(pCollection);
    }
}