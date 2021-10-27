package com.mrk.learn.md.common;

import java.lang.annotation.*;

/**
 * 数据源切换注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyDatasource {
    DataSourceEnum value();
}
