package com.edu.tks.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class MessageDeSerializer implements Deserializer<MessageEntity> {

    private final ObjectMapper objectMapper = new JsonMapper();

    @Override
    public MessageEntity deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), MessageEntity.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}