package com.example.spring.jackson.custom;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

public class CustomAnnotationIntrospector extends JacksonAnnotationIntrospector {

    @Override
    public Object findSerializer(Annotated annotated) {
        JsonSerialize annotation = annotated.getAnnotation(JsonSerialize.class);
        if(annotation!=null && annotation.using()!=null && annotation.using()==MyDateSerializer.class){
            //不使用MyDateSerializer日期序列化器，改为默认的
            return new DateSerializer();
        }
        return super.findSerializer(annotated);
    }
}
