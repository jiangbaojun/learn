package com.mrk.learn.md.service;

import com.mrk.learn.md.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public int addOrders(Map<String, Object> params) {
        return testMapper.addOrders(params);
    }

    public List<Map<String, Object>> queryOrder() {
        return testMapper.queryOrder();
    }
}
