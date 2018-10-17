package com.aaxis.microservice.training.demo1.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        System.out.println("-------------------------------------------");
        System.out.println("SpringUtil.applicationContext == " + SpringUtil.applicationContext);
        System.out.println("-------------------------------------------");

    }

    public static Object getBean(String beanId) throws BeansException {
        if (applicationContext.containsBean(beanId)) {
            return applicationContext.getBean(beanId);
        }
        return null;
    }

    public static <T> T getBeanByClass(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static ApplicationContext getApplicationContext() {
        return SpringUtil.applicationContext;
    }

}