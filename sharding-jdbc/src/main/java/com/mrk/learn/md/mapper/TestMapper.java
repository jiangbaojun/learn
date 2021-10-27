package com.mrk.learn.md.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestMapper {

    int addOrders(Map<String, Object> params);
    List<Map<String,Object>> queryOrder();
}
