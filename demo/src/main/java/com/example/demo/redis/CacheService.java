package com.example.demo.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service

public class CacheService {

    private final StringRedisTemplate redisTemplate;

    public CacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public boolean  isUserBlocked(String phone) {
        String blockKey = "blocked:user:" + phone;
        Boolean isBlocked = redisTemplate.hasKey(blockKey);
        if (Boolean.TRUE.equals(isBlocked)) {
            return true;
        }
        return false;
    }

    
}




