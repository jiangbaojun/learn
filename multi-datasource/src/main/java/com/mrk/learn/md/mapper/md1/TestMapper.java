package com.mrk.learn.md.mapper.md1;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("testMapper1")
public interface TestMapper {

    List<Map<String,Object>> selectList();
}
