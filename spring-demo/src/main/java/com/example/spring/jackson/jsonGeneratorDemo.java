package com.example.spring.jackson;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class jsonGeneratorDemo {
    public static void main(String[] args) throws IOException {
        JsonFactory factory = new JsonFactory();

        // 此处最终输输出到OutputStreams输出流（此处输出到文件）
        //JsonGenerator jsonGenerator = factory.createGenerator(new File("java-jackson/src/main/resources/person.json"), JsonEncoding.UTF8);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JsonGenerator jsonGenerator = factory.createGenerator(byteArrayOutputStream, JsonEncoding.UTF8);
        jsonGenerator.writeStartObject(); //开始写，也就是这个符号 {

        jsonGenerator.writeStringField("name", "YourBatman");
        jsonGenerator.writeNumberField("age", 18);

        // 写入Dog对象（枚举对象）
        jsonGenerator.writeFieldName("dog");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "旺财");
        jsonGenerator.writeStringField("color", "WHITE");
        jsonGenerator.writeEndObject();

        //写入一个数组格式
        jsonGenerator.writeFieldName("hobbies"); // "hobbies" :
        jsonGenerator.writeStartArray(); // [
        jsonGenerator.writeString("篮球"); // "篮球"
        jsonGenerator.writeString("football"); // "football"
        jsonGenerator.writeEndArray(); // ]

        jsonGenerator.writeEndObject(); //结束写，也就是这个符号 }

        // 关闭IO流
        jsonGenerator.close();

        //输出查看
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        System.out.println(new String(byteArray, StandardCharsets.UTF_8));
    }
}
