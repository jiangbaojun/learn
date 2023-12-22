package com.example.spring.jackson.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateDeserialize extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        //不要一直读取，只能读一次。包括getValueAsString
        //JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        try {
            String fieldName = jsonParser.getCurrentName();
            String value = jsonParser.getValueAsString();
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}