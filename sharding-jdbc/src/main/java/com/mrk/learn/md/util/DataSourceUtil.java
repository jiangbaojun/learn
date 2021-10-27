package com.mrk.learn.md.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.mrk.learn.md.properties.DataSourceInfo;
import com.mrk.learn.md.properties.MrkDataSources;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceUtil {

    public static String defaultDataSource = null;

    public static Map<String, DataSource> parseDataSource(MrkDataSources mrkDataSources) {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        Map<String, DataSourceInfo> sources = mrkDataSources.getSources();
        sources.forEach((name, value)->{
            if(defaultDataSource==null){
                defaultDataSource = name;
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
}
