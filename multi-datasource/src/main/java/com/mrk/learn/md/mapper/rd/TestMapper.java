package com.mrk.learn.md.mapper.rd;

import com.mrk.learn.md.common.DataSourceEnum;
import com.mrk.learn.md.common.MyDatasource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("testMapper")
public interface TestMapper {

    List<Map<String,Object>> selectList();

    /**
     * 在mapper接口层指定是数据源也是可以的
     * @return
     */
    @MyDatasource(DataSourceEnum.DS3)
    List<Map<String,Object>> selectList_tt();
}
