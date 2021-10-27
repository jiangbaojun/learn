package com.mrk.learn.easyexcel.read;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
@ToString
public class DemoData {
    private String string;
    private Date date;
    private Double doubleData;
}
