package com.mrk.learn.md.mapper.md3;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("testMapper3")
public interface TestMapper {

    List<Map<String,Object>> selectList();
}
