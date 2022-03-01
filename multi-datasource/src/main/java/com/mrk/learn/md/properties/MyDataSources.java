package com.mrk.learn.md.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据源连接配置
 */
@ConfigurationProperties("my.datasource")
public class MyDataSources {
    /** 数据源连接*/
    private Map<String, DruidDataSource> sources = new LinkedHashMap<>();

    public Map<Object, Object> getTargetDataSources() {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.putAll(sources);
        return dataSources;
    }
    public String getDefaultDataSourceName(){
        return sources.keySet().iterator().next();
    }
    public Map<String, DruidDataSource> getSources() {
        return sources;
    }

    public void setSources(Map<String, DruidDataSource> sources) {
        this.sources = sources;
    }
}
