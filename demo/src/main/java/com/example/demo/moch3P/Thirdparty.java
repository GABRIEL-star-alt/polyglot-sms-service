package com.example.demo.moch3P;

import org.springframework.stereotype.Service;

import com.example.demo.model.SmsEvent;

import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor

public class Thirdparty {


    public SmsEvent sendSms(String phone, String message) {
         boolean success = Math.random() > 0.2;
        // String uuid = java.util.UUID.randomUUID().toString();
        // return success ? uuid : null;
        if (success) {
            return new SmsEvent(phone, message, "SUCCESS");
        } else {
            return new SmsEvent(   phone, message, "FAILED" );
        }
    }
    
}
