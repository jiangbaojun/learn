package com.mrk.learn.atomikos.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Bean
    public DataSource dataSource1() {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl("jdbc:mysql://localhost:3306/db1?allowMultiQueries=true&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        mysqlXADataSource.setUser("root");
        mysqlXADataSource.setPassword("12345678");

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("dataSource1");
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setMinPoolSize(5);
        xaDataSource.setMaxPoolSize(20);
        return xaDataSource;
    }

    @Bean
    public DataSource dataSource2() {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl("jdbc:mysql://localhost:3306/db2?allowMultiQueries=true&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        mysqlXADataSource.setUser("root");
        mysqlXADataSource.setPassword("12345678");

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("dataSource2");
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setMinPoolSize(5);
        xaDataSource.setMaxPoolSize(20);
        return xaDataSource;
    }
}
