package com.edu.tks.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class MessageSerializer implements Serializer<MessageEntity> {

    private final ObjectMapper objectMapper = new JsonMapper();

    @Override
    public byte[] serialize(String topic, MessageEntity data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }

    }
}