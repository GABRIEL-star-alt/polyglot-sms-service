package com.example.demo.controller;

import com.example.demo.Services.SmsService;
import com.example.demo.model.SmsRequest;
// import com.example.demo.kafka.SmsProducer;
import com.example.demo.model.SmsEvent;
// import com.squareup.okhttp.Response;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<?> send(
            @RequestBody SmsRequest smsRequest) {
        String phone = smsRequest.getPhoneNumber();
        String message = smsRequest.getMessage();

        SmsEvent event = smsService.processSms(phone, message);
        return ResponseEntity.ok(event);
    }
}
