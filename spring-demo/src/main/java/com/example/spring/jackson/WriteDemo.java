package com.example.spring.jackson;

import com.example.spring.jackson.custom.CustomAnnotationIntrospector;
import com.example.spring.jackson.custom.MyDateSerializer;
import com.example.spring.jackson.model.Car;
import com.example.spring.jackson.model.Employee;
import com.example.spring.jackson.model.Saler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
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
        car.setMyDate(new Date());
        objectMapper.writeValue(new FileOutputStream("C:\\Users\\BaojunJiang\\Desktop\\output-2.json"), car);
        String json = objectMapper.writeValueAsString(car);
        System.out.printf(json);
    }
    @Test
    public void test11() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = new Employee();
        employee.setName("xiaoming");
        employee.setEnrollmentDate(new Date());
        Car car = new Car("BMW", 4);
        car.setMyDate(new Date());
        employee.setCar(car);
        String json = objectMapper.writeValueAsString(employee);
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
        //序列化时，添加类名称.老版本：objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为东八区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss"));
        //空值不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        //objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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

        Employee employee = new Employee("John Doe", "john@doe.com");
        Car car = new Car("BMW", 4);
        car.setProduceDate(new Date());
        car.setSaler(new Saler("a", "beijing"));
        employee.setCar(car);
        System.out.println(objectMapper.writeValueAsString(employee));
    }

    @Test
    public void test5() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new MyDateSerializer());
        objectMapper.registerModule(module);
        Car car = new Car("BMW", 4);
        car.setProduceDate(new Date());
        car.setSaler(new Saler("a", "beijing"));
        String jsonStr = objectMapper.writeValueAsString(car);
        System.out.println(jsonStr);
    }

    /**
     * 忽略注解
     */
    @Test
    public void test6() throws Exception {
        Car car = new Car("BMW", 4);
        car.setProduceDate(new Date());
        car.setMyDate(new Date());
        car.setMyDate2(new Date());

        ObjectMapper om1 = new ObjectMapper();
        String json1 = om1.writeValueAsString(car);
        System.out.println(json1);
//        Car car1 = om1.readValue(json1, Car.class);
//        System.out.println(car1);


        ObjectMapper om2 = new ObjectMapper();
        //忽略注解支持
        om2.configure(MapperFeature.USE_ANNOTATIONS, false);
        String json2 = om2.writeValueAsString(car);
        System.out.println(json2);
        Car car2 = om2.readValue(json2, Car.class);
        System.out.println(car2);

        ObjectMapper om3 = new ObjectMapper();
        //忽略指定的注解支持，通过注解解析器CustomAnnotationIntrospector
        om3.setAnnotationIntrospector(new CustomAnnotationIntrospector());
        String json3 = om3.writeValueAsString(car);
        System.out.println(json3);
        Car car3 = om3.readValue(json3, Car.class);
        System.out.println(car3);



    }

}
