package com.mrk.learn.md.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.mrk.learn.md.properties.DataSourceInfo;
import com.mrk.learn.md.properties.MrkDataSources;

import java.util.HashMap;
import java.util.Map;

public class DataSourceUtil {

    public static String defaultDataSourceName = null;

    /**
     * 解析数据源
     */
    public static Map<Object, Object> parseDataSource(MrkDataSources mrkDataSources) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        Map<String, DataSourceInfo> sources = mrkDataSources.getSources();
        sources.forEach((name, value)->{
            if(defaultDataSourceName ==null){
                //默认数据源，可以在配置中添加配置项指定。本例取第一个
                defaultDataSourceName = name;
            }
            DruidDataSource ds = new DruidDataSource();
            ds.setUrl(value.getUrl());
            ds.setUsername(value.getUsername());
            ds.setPassword(value.getPassword());
            ds.setDriverClassName(value.getDriverClassName());
            dataSourceMap.put(name,ds);
        });
        return dataSourceMap;
    }

    /**
     * 根据数据源名称获得数据源
     */
    public static DruidDataSource getDataSource(MrkDataSources mrkDataSources, String name){
        DataSourceInfo dataSourceInfo = mrkDataSources.getSources().get(name);
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceInfo.getUrl());
        druidDataSource.setDriverClassName(dataSourceInfo.getDriverClassName());
        druidDataSource.setUsername(dataSourceInfo.getUsername());
        druidDataSource.setPassword(dataSourceInfo.getPassword());
        return druidDataSource;
    }
}
