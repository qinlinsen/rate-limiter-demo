package com.qls.ratelimiterdemo.exception;

public class RateLimitException extends RuntimeException{
    public RateLimitException(String message) {
        super(message);
    }
}
