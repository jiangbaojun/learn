package com.mrk.learn.easyexcel.write;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class DemoData {
//    @ExcelProperty(value = "字符串标题", index =4)
    private String string;
//    @ExcelProperty(value = "日期标题", index = 7)
    private Date date;
//    @ExcelProperty(value = "数字标题",index = 5)
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
