package com.example.spring.jackson.custom;

import com.example.spring.jackson.model.Car;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CarDeserialize extends JsonDeserializer<Car> {

    @Override
    public Car deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
//        Iterator<JsonNode> iterator = node.elements();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
        String brand = node.get("brand").asText();
        int doors = node.get("doors").asInt();
        return new Car("d-"+brand, doors);
    }
}