package com.example.demo.controller;

// import com.example.demo.kafka.SmsProducer;
// import com.example.demo.model.SmsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.BlockUserRequest;
import com.example.demo.model.ApiResponse;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StringRedisTemplate redisTemplate;

    @PostMapping("/block")
    public ResponseEntity<ApiResponse> blockUser(
            @RequestBody BlockUserRequest request) {

        String key = "blocked:user:" + request.getPhone();

        Boolean alreadyBlocked = redisTemplate.hasKey(key);

        if (Boolean.TRUE.equals(alreadyBlocked)) {
            return ResponseEntity.ok(
                    new ApiResponse("ALREADY_BLOCKED",
                            "User is already blocked")
            );
        }

        redisTemplate.opsForValue().set(key, "1");

        return ResponseEntity.status(201).body(
                new ApiResponse("BLOCKED",
                        "User has been blocked successfully")
        );
    }
}
