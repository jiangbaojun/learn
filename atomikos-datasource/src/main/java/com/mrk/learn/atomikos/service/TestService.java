package com.mrk.learn.atomikos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
public class TestService {

    private final JdbcTemplate jdbcTemplate1;
    private final JdbcTemplate jdbcTemplate2;

    @Autowired
    public TestService(DataSource dataSource1, DataSource dataSource2) {
        this.jdbcTemplate1 = new JdbcTemplate(dataSource1);
        this.jdbcTemplate2 = new JdbcTemplate(dataSource2);
    }

    @Transactional
    public void performTransaction(Boolean error) {
        // 在第一个数据源上执行操作
        jdbcTemplate1.update("INSERT INTO t1 (name) VALUES (?)", "value1");

        // 在第二个数据源上执行操作
        jdbcTemplate2.update("INSERT INTO t2 (name) VALUES (?)", "value2");

        // 模拟一个异常，以触发回滚
        if (error!=null && error) {
            throw new RuntimeException("模拟异常，触发回滚");
        }
    }
}
