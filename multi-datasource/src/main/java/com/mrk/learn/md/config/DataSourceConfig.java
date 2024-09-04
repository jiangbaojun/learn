package com.mrk.learn.md.config;


import com.mrk.learn.md.common.MultipleDataSource;
import com.mrk.learn.md.properties.MrkDataSources;
import com.mrk.learn.md.properties.MyDataSources;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 方案二：动态数据源
 */
@Configuration
@EnableConfigurationProperties({MrkDataSources.class, MyDataSources.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan(basePackages = "com.mrk.learn.md.mapper.rd", sqlSessionFactoryRef = "sessionFactory", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

    @Autowired
    private MyDataSources myDataSources;

    /**
     * 动态数据源配置
     */
    @Bean
    public DataSource multipleDataSource() {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
//        Map<Object, Object> targetDataSources = DataSourceUtil.parseDataSource(mrkDataSources);
//        String defaultDataSourceName = DataSourceUtil.defaultDataSourceName;
        Map<Object, Object> targetDataSources = myDataSources.getTargetDataSources();
        String defaultDataSourceName = myDataSources.getDefaultDataSourceName();
        //设置数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(targetDataSources.get(defaultDataSourceName));
        return multipleDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sessionFactory(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(multipleDataSource());
        return sqlSessionFactoryBean;
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception{
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactory().getObject());
        return sqlSessionTemplate;
    }
    @Bean
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(multipleDataSource());
        return dataSourceTransactionManager;
    }
}
