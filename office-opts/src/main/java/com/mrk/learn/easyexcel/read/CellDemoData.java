package com.mrk.learn.easyexcel.read;

import com.alibaba.excel.metadata.CellData;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class CellDemoData {
    private CellData<String> string;
    // 这里注意 虽然是日期 但是 类型 存储的是number 因为excel 存储的就是number
    private CellData<Date> date;
    private CellData<Double> doubleData;
}