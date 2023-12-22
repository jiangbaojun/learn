package com.example.spring.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class JsonParserDemo {
    public static void main(String[] args) throws IOException {
        String carJson =
                "{\"name\":\"YourBatman\",\"age\":18,\"dog\":{\"name\":\"旺财\",\"color\":\"WHITE\"},\"hobbies\":[\"篮球\",\"football\"]}";

        JsonFactory factory = new JsonFactory();
        JsonParser  jsonParser  = factory.createParser(carJson);
        //JsonParser jsonParser = factory.createParser(new File("java-jackson/src/main/resources/person.json"));

        // 只要还没到末尾，也就是}这个符号，就一直读取
        // {"name":"YourBatman","age":18,"dog":{"name":"旺财","color":"WHITE"},"hobbies":["篮球","football"]}
        JsonToken jsonToken = null; // token类型
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jsonParser.getCurrentName();
            if ("name".equals(fieldname)) {
                jsonToken = jsonParser.nextToken();
                System.out.println("==============token类型是：" + jsonToken);
                System.out.println(jsonParser.getText());
            } else if ("age".equals(fieldname)) {
                jsonToken = jsonParser.nextToken();
                System.out.println("==============token类型是：" + jsonToken);
                System.out.println(jsonParser.getIntValue());
            } else if ("dog".equals(fieldname)) {
                jsonToken = jsonParser.nextToken();
                System.out.println("==============token类型是：" + jsonToken);
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String dogFieldName = jsonParser.getCurrentName();
                    if ("name".equals(dogFieldName)) {
                        jsonToken = jsonParser.nextToken();
                        System.out.println("======================token类型是：" + jsonToken);
                        System.out.println(jsonParser.getText());
                    } else if ("color".equals(dogFieldName)) {
                        jsonToken = jsonParser.nextToken();
                        System.out.println("======================token类型是：" + jsonToken);
                        System.out.println(jsonParser.getText());
                    }
                }
            } else if ("hobbies".equals(fieldname)) {
                jsonToken = jsonParser.nextToken();
                System.out.println("==============token类型是：" + jsonToken);
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    System.out.println(jsonParser.getText());
                }
            }
        }

        // 关闭IO流
        jsonParser.close();
    }
}
