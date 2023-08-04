package com.example.controller;

import com.example.service.HelloService;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
@Slf4j
@RequestMapping("/limit")
@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;
    private final RateLimiter limiter = RateLimiter.create(98.0);
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @GetMapping("/hello")
    public Object testLimiter() throws RequestLimitException {
        boolean tryAcquire = limiter.tryAcquire(0, TimeUnit.SECONDS);

        if (!tryAcquire) {
            log.error("访问量过大，被限流，时间{}", LocalDateTime.now().format(dtf));
            throw new RequestLimitException();
        }

        log.info("接口访问成功，时间{}", LocalDateTime.now().format(dtf));
        return helloService.hello();
    }

}
