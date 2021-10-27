package com.mrk.learn.md.mapper.md2;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("testMapper2")
public interface TestMapper {

    List<Map<String,Object>> selectList();
}
