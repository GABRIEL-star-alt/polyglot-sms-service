package com.example.demo.controller;

// import com.example.demo.kafka.SmsProducer;
// import com.example.demo.model.SmsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StringRedisTemplate redisTemplate;

    @PostMapping("/block/{phone}")
    public ResponseEntity<String> blockUser(@PathVariable String phone) {
        redisTemplate.opsForValue()
                .set("blocked:user:" + phone, "1");
        System.out.println("REDIS BLOCK KEY = blocked:user:" + phone);

        return ResponseEntity.accepted().body("User with phone " + phone + " has been blocked.");
    }
}
