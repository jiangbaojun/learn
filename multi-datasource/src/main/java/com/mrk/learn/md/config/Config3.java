package com.mrk.learn.md.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.mrk.learn.md.properties.MrkDataSources;
import com.mrk.learn.md.util.DataSourceUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 方案一：每个数据源单独配置各自的template、factory和transaction
 * 数据源3
 */
//@Configuration
//@MapperScan(basePackages={"com.mrk.learn.md.mapper.md3"},sqlSessionFactoryRef="sessionFactory3")
public class Config3 {

    @Autowired
    private MrkDataSources mrkDataSources;

    /**
     * 测试库3数据源
     */
    @Bean
    public DruidDataSource dataSource3(){
        return DataSourceUtil.getDataSource(mrkDataSources,"demo_ds_3");
    }
    @Bean
    public SqlSessionFactoryBean sessionFactory3(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource3());
        return sqlSessionFactoryBean;
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate3() throws Exception{
        SqlSessionTemplate sqlSessionTemplate =
                new SqlSessionTemplate(sessionFactory3().getObject());
        return sqlSessionTemplate;
    }
    @Bean
    public DataSourceTransactionManager transactionManager3(){
        DataSourceTransactionManager dataSourceTransactionManager
                = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource3());
        return dataSourceTransactionManager;
    }


}
