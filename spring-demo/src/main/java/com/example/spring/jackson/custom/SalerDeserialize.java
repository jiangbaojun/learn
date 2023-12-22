package com.example.spring.jackson.custom;

import com.example.spring.jackson.model.Saler;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class SalerDeserialize extends JsonDeserializer<Saler> {

    @Override
    public Saler deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
//        Iterator<JsonNode> iterator = node.elements();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
        JsonNode nameNode = node.get("name");
        String name = nameNode==null?"default-name":nameNode.asText();
        String address = node.get("address").asText();
        return new Saler("d-"+name, "@"+address);
    }
}