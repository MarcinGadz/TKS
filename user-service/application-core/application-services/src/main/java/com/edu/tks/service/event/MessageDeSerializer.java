package com.edu.tks.service.event;

import com.edu.tks.service.user.UserMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class MessageDeSerializer implements Deserializer<UserMessage> {

    private final ObjectMapper objectMapper = new JsonMapper();

    @Override
    public UserMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), UserMessage.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}