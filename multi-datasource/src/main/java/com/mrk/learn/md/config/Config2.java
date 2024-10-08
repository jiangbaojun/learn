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
 * 本例做了特殊测试。basePackages扫描，包含了com.mrk.learn.md.mapper.md1包。但是com.mrk.learn.md.mapper.md1.TestMapper类只能被一个sqlSessionFactory创建mapper接口代理对象，另一个sqlSessionFactory尝试获取mapper接口代理对象返回空。
 * 数据源2
 */
@Configuration
@MapperScan(basePackages={"com.mrk.learn.md.mapper.md2","com.mrk.learn.md.mapper.md1"},
        sqlSessionFactoryRef="sessionFactory2", sqlSessionTemplateRef = "sqlSessionTemplate2")
public class Config2 {

    @Autowired
    private MrkDataSources mrkDataSources;

    /**
     * 测试库2数据源
     */
    @Bean
    public DruidDataSource dataSource2(){
        return DataSourceUtil.getDataSource(mrkDataSources,"demo_ds_2");
    }
    @Bean
    public SqlSessionFactoryBean sessionFactory2(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource2());
        return sqlSessionFactoryBean;
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate2() throws Exception{
        SqlSessionTemplate sqlSessionTemplate =
                new SqlSessionTemplate(sessionFactory2().getObject());
        return sqlSessionTemplate;
    }
    @Bean
    public DataSourceTransactionManager transactionManager2(){
        DataSourceTransactionManager dataSourceTransactionManager
                = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource2());
        return dataSourceTransactionManager;
    }
}
