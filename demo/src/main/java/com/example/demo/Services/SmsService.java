package com.example.demo.Services;

import com.example.demo.Exceptions.UserBlockedException;
import com.example.demo.kafka.SmsProducer;
import com.example.demo.moch3P.Thirdparty;
import com.example.demo.model.SmsEvent;
import com.example.demo.redis.CacheService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final CacheService cacheService;
    private final Thirdparty thirdparty;
    private final SmsProducer producer;

    public SmsEvent processSms(String phone, String message) {

        SmsEvent event = new SmsEvent(phone, message, "PENDING");

        if (cacheService.isUserBlocked(phone)) {
            event.setStatus("BLOCKED");
            producer.sendBlocked(event);
            throw new UserBlockedException("User is blocked");
        }

        SmsEvent result = thirdparty.sendSms(phone, message);
        event.setStatus(result.getStatus());

        producer.send(event);
        return event;
    }
}
