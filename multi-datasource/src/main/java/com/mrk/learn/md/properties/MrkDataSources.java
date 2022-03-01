package com.mrk.learn.md.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据源连接配置（自主解析，未使用）
 */
@ConfigurationProperties("datasource")
public class MrkDataSources {
    /** 数据源连接*/
    private Map<String, DataSourceInfo> sources = new LinkedHashMap<>();

    public Map<String, DataSourceInfo> getSources() {
        return sources;
    }

    public void setSources(Map<String, DataSourceInfo> sources) {
        this.sources = sources;
    }
}
