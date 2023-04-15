package com.qls.ratelimiterdemo.aop;

import com.qls.ratelimiterdemo.exception.RateLimitException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author qinlinsen
 */
@ControllerAdvice
public class SpringControllerAop {

    /**
     * @return request to many, please try again later when occurred {@link com.qls.ratelimiterdemo.exception.RateLimitException}
     */
    @ResponseBody
    @ExceptionHandler(value = RateLimitException.class)
    public String rateLimitException() {
        return "request to many, please try again later";
    }
}
