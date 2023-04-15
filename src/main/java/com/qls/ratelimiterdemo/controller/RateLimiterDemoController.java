package com.qls.ratelimiterdemo.controller;

import com.qls.ratelimiterdemo.annotation.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinlinsen
 */

@RestController
@RequestMapping("/demo")
public class RateLimiterDemoController {

    @GetMapping("/rateLimit")
    @RateLimit(permit = 3)
    public String rateLimitDemo() {
        return "success";
    }


    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
