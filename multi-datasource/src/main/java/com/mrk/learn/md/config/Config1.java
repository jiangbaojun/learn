package com.mrk.learn.md.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.mrk.learn.md.properties.MrkDataSources;
import com.mrk.learn.md.util.DataSourceUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 方案一：每个数据源单独配置各自的template、factory和transaction
 * 数据源1
 */
@Configuration
@MapperScan(basePackages={"com.mrk.learn.md.mapper.md1"},
        sqlSessionFactoryRef="sessionFactory1", sqlSessionTemplateRef = "sqlSessionTemplate1")
public class Config1 {

    @Autowired
    private MrkDataSources mrkDataSources;

    /**
     * 测试库1数据源
     */
    @Bean
    public DruidDataSource dataSource1(){
        return DataSourceUtil.getDataSource(mrkDataSources,"demo_ds_1");
    }
    @Bean
    public SqlSessionFactoryBean sessionFactory1(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource1());
        return sqlSessionFactoryBean;
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate1() throws Exception{
        SqlSessionTemplate sqlSessionTemplate =
                new SqlSessionTemplate(sessionFactory1().getObject());
        return sqlSessionTemplate;
    }
    @Bean
    public DataSourceTransactionManager transactionManager1(){
        DataSourceTransactionManager dataSourceTransactionManager
                = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource1());
        return dataSourceTransactionManager;
    }


}
