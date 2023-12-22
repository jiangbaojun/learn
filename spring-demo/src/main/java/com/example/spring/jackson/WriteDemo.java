package com.example.spring.jackson;

import com.example.spring.jackson.custom.DateSerializer;
import com.example.spring.jackson.model.Car;
import com.example.spring.jackson.model.Employee;
import com.example.spring.jackson.model.Saler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WriteDemo {

    @Test
    public void test1() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("BMW", 4);
        objectMapper.writeValue(new FileOutputStream("C:\\Users\\BaojunJiang\\Desktop\\output-2.json"), car);
        String json = objectMapper.writeValueAsString(car);
        System.out.printf(json);
    }

    @Test
    public void test2() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper(new CBORFactory());
        Employee employee = new Employee("John Doe", "john@doe.com");
        employee.setCar(new Car("BMW", 4));
        byte[] cborBytes = null;
        try {
            cborBytes = objectMapper.writeValueAsBytes(employee);
            System.out.println(new String(cborBytes, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            Employee employee2 = objectMapper.readValue(cborBytes, Employee.class);
            System.out.println(employee2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * yaml格式
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Employee employee = new Employee("John Doe", "john@doe.com");
        employee.setCar(new Car("BMW", 4));
        String yamlString = null;
        try {
            yamlString = objectMapper.writeValueAsString(employee);
            System.out.println(yamlString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            Employee employee2 = objectMapper.readValue(yamlString, Employee.class);
            System.out.println(employee2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(new Car(new Date())));
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为东八区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss"));
        //空值不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化日期时以timestamps输出，默认true
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //序列化枚举是以toString()来输出，默认false，即默认以name()来输出
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        //序列化枚举是以ordinal()来输出，默认false
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, false);
        //类为空时，不要抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //反序列化时,遇到未知属性时是否引起结果失败
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //解析器支持解析结束符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        System.out.println(objectMapper.writeValueAsString(new Car(new Date())));
    }

    @Test
    public void test5() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateSerializer());
        objectMapper.registerModule(module);
        Car car = new Car("BMW", 4);
        car.setProduceDate(new Date());
        car.setSaler(new Saler("a", "beijing"));
        String jsonStr = objectMapper.writeValueAsString(car);
        System.out.println(jsonStr);
    }

}
