package com.edu.tks.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class MessageDeSerializer implements Deserializer<ClientMessage> {

    private final ObjectMapper objectMapper = new JsonMapper();

    @Override
    public ClientMessage deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(new String(bytes), ClientMessage.class);
        }  catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
