package com.example.demo.kafka;

import com.example.demo.moch3P.Thirdparty;
import com.example.demo.model.SmsEvent;
import com.example.demo.redis.CacheService;
import com.example.demo.Exceptions.UserBlockedException;

import lombok.RequiredArgsConstructor;


import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
@Service
@RequiredArgsConstructor
public class SmsProducer {

    private final KafkaTemplate<String, SmsEvent> kafkaTemplate;

    @Value("${sms.topic}")
    private String topic;

    @Value("${sms.blocked}")
    private String blockedTopic;

    public void send(SmsEvent event) {
        kafkaTemplate.send(topic, event);
    }

    public void sendBlocked(SmsEvent event) {
        kafkaTemplate.send(blockedTopic, event);
    }
}
