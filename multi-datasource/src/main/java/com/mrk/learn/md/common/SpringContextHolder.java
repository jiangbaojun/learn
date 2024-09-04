package com.mrk.learn.md.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext outApplicationContext) throws BeansException {
        applicationContext=outApplicationContext;
    }

    public static <T> T getBean(String name)
    {
        return (T)applicationContext.getBean(name);
    }
    public static <T> T getBean(Class<T> beanClass)
    {
        return (T)applicationContext.getBean(beanClass);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static boolean containBean(String name)
    {
        return applicationContext.containsBean(name);
    }
}
