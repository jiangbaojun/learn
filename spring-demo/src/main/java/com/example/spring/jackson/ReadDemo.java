package com.example.spring.jackson;

import com.example.spring.jackson.custom.DateDeserialize;
import com.example.spring.jackson.custom.SalerDeserialize;
import com.example.spring.jackson.model.Car;
import com.example.spring.jackson.model.Saler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReadDemo {

    //1.Read Object From JSON String
    @Test
    public void test1() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";
        Car car = objectMapper.readValue(carJson, Car.class);
        System.out.println(car);
    }

    //2.Read Object From JSON Reader
    @Test
    public void test2() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 4 }";
        Reader reader = new StringReader(carJson);
        Car car = objectMapper.readValue(reader, Car.class);
        System.out.println(car);
    }

    //3.Read Object From JSON File
    @Test
    public void test3() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("data/car.json");
        Car car = objectMapper.readValue(file, Car.class);
        System.out.println(car);
    }

    //4.Read Object From JSON via URL
    //本例使用的是文件URL，也可使用一个HTTP URL（如：http://jenkov.com/some-data.json ).
    @Test
    public void test4() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = new URL("file:data/car.json");
        Car car = objectMapper.readValue(url, Car.class);
        System.out.println(car);
    }

    //5.Read Object From JSON InputStream
    @Test
    public void test5() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream input = new FileInputStream("data/car.json");
        Car car = objectMapper.readValue(input, Car.class);
        System.out.println(car);
    }

    //6.Read Object From JSON Byte Array
    @Test
    public void test6() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";
        byte[] bytes = carJson.getBytes("UTF-8");
        Car car = objectMapper.readValue(bytes, Car.class);
        System.out.println(car);
    }

    //7.Read Object Array From JSON Array String
    @Test
    public void test7() throws Exception {
        String jsonArray = "[{\"brand\":\"ford\"}, {\"brand\":\"Fiat\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        Car[] car = objectMapper.readValue(jsonArray, Car[].class);
        System.out.println(car);
    }

    //8.Read Object List From JSON Array String
    @Test
    public void test8() throws Exception {
        String jsonArray = "[{\"brand\":\"ford\"}, {\"brand\":\"Fiat\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        List<Car> car = objectMapper.readValue(jsonArray, new TypeReference<List<Car>>() {});
        System.out.println(car);

    }

    //9.Read Map from JSON String
    @Test
    public void test9() throws Exception {
        String jsonObject = "{\"brand\":\"ford\", \"doors\":5}";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonObject, new TypeReference<Map<String, Object>>() {});
        System.out.println(jsonMap);
    }

    @Test
    public void test10() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new DateDeserialize());
        module.addDeserializer(Saler.class, new SalerDeserialize());
        //module.addDeserializer(Car.class, new CarDeserialize());
        objectMapper.registerModule(module);
        String carJson = "{ \"produceDate\" : \"2020-01-01 10:00:05\", \"brand\" : \"Mercedes\", \"doors\" : 5, \"saler\" : {\"address\" : \"beijing\"} }";
        Car car = objectMapper.readValue(carJson, Car.class);
        System.out.println(car);
    }
}
