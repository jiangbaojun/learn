package com.mrk.learn.md.service;

import com.mrk.learn.md.common.DataSourceContextHolder;
import com.mrk.learn.md.common.DataSourceEnum;
import com.mrk.learn.md.common.MyDatasource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {

    //此处加(required = false)是为了方便测试
    @Autowired(required = false)
    private com.mrk.learn.md.mapper.rd.TestMapper testMapper;
    @Autowired(required = false)
    private  com.mrk.learn.md.mapper.md1.TestMapper testMapper1;
    @Autowired(required = false)
    private  com.mrk.learn.md.mapper.md2.TestMapper testMapper2;
    @Autowired(required = false)
    private  com.mrk.learn.md.mapper.md3.TestMapper testMapper3;

    /**
     * 方案二：手动切换数据源
     * @return
     */
    public List<Map<String, Object>> selectManual(String db) {
        if(StringUtils.isNotEmpty(db)){
            //设置数据源
            DataSourceContextHolder.setDataSource(db);
        }
        List<Map<String, Object>> mapList = testMapper.selectList();
        //重置或者设置为默认数据源
        DataSourceContextHolder.clear();
        return mapList;
    }
    /**
     * 方案二
     * 在mapper层使用注解指定数据源
     */
    @MyDatasource(DataSourceEnum.DS1)
    public List<Map<String, Object>> tt_t(String db) {
        return testMapper.selectList_tt();
    }

    /**
     * 方案二：数据源1
     */
    @MyDatasource(DataSourceEnum.DS1)
    public List<Map<String, Object>> tta1(String db) {
        return testMapper.selectList();
    }
    /**
     * 方案二：数据源2
     */
    @MyDatasource(DataSourceEnum.DS2)
    public List<Map<String, Object>> tta2(String db) {
        return testMapper.selectList();
    }
    /**
     * 方案二：数据源3
     */
    @MyDatasource(DataSourceEnum.DS3)
    public List<Map<String, Object>> tta3(String db) {
        return testMapper.selectList();
    }



    /**
     * 方案一：查询数据库1
     */
    public List<Map<String, Object>> select1() {
        return testMapper1.selectList();
    }
    /**
     * 方案一：查询数据库2
     */
    public List<Map<String, Object>> select2() {
        return testMapper2.selectList();
    }
    /**
     * 方案一：查询数据库3
     */
    public List<Map<String, Object>> select3() {
        return testMapper3.selectList();
    }

}
