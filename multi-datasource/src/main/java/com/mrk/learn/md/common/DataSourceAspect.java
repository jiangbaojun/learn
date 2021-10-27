package com.mrk.learn.md.common;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切换数据源切面
 */
@Component
@Aspect
public class DataSourceAspect {

    @Pointcut("@within(com.mrk.learn.md.common.MyDatasource) || @annotation(com.mrk.learn.md.common.MyDatasource)")
    public void pc(){
    }

    /**
     * 执行前设置数据源
     * @param dataSource
     */
    @Before("pc() && @annotation(dataSource)")
    public void doBefore(MyDatasource dataSource){
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    /**
     * 执行后清除数据源（还原为默认）
     */
    @After("pc()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }
}
