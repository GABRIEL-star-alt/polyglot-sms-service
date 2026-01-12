package com.example.demo.Services;

import com.example.demo.Exceptions.UserBlockedException;
import com.example.demo.kafka.SmsProducer;
import com.example.demo.moch3P.Thirdparty;
import com.example.demo.model.SmsEvent;
import com.example.demo.redis.CacheService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SmsServiceTest {

    @Mock
    private CacheService cacheService;

    @Mock
    private Thirdparty thirdparty;

    @Mock
    private SmsProducer producer;

    @InjectMocks
    private SmsService smsService;

    @Test
    void whenUserIsBlocked_thenBlockedEventSentAndExceptionThrown() {

        when(cacheService.isUserBlocked("999")).thenReturn(true);

        assertThrows(UserBlockedException.class, () ->
                smsService.processSms("999", "hello")
        );

        verify(producer).sendBlocked(any(SmsEvent.class));
        verifyNoInteractions(thirdparty);
    }

    @Test
    void whenUserNotBlocked_andThirdPartySuccess_thenSuccessEventSent() {

        when(cacheService.isUserBlocked("999")).thenReturn(false);
        when(thirdparty.sendSms(any(), any()))
                .thenReturn(new SmsEvent("999", "hello", "SUCCESS"));

        SmsEvent event = smsService.processSms("999", "hello");

        assertEquals("SUCCESS", event.getStatus());
        verify(producer).send(any(SmsEvent.class));
    }
}
