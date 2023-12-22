package com.example.spring.jackson.model;

import com.example.spring.jackson.custom.MyDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Car {
    private String brand;
    private Integer doors;
    private Date produceDate;
    private Saler saler;

    //加了序列化器，默认的反序列化不支持，会无法反序列化。反序列化要额外处理。详见：com.example.spring.jackson.WriteDemo.test6示例
    @JsonSerialize(using = MyDateSerializer.class)
    private Date myDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date myDate2;

    public Car() {
    }

    public Car(String brand, Integer doors) {
        this.brand = brand;
        this.doors = doors;
    }
}
