package com.cjt.consultant.config;

import com.cjt.consultant.model.VerificationCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

public class RedisVerificationCodeSerializer implements RedisSerializer<VerificationCode> {

    private final ObjectMapper objectMapper;

    public RedisVerificationCodeSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(VerificationCode verificationCode) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(verificationCode);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing VerificationCode", e);
        }
    }

    @Override
    public VerificationCode deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, VerificationCode.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error deserializing VerificationCode", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
