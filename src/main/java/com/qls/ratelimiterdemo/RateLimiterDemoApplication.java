package com.qls.ratelimiterdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

/**
 * @author qinlinsen
 */

@SpringBootApplication
public class RateLimiterDemoApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RateLimiterDemoApplication.class, args);
        LOGGER.info(">>>RateLimiterDemoApplication start success at {}>>>", new Date());

    }

}
