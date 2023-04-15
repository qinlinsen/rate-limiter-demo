package com.qls.ratelimiterdemo.annotation;

import com.qls.ratelimiterdemo.constant.RateLimiterConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation of RateLimit
 * @author  qinlinsen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
   /**
    *@return  per second permits
    */
   double permit() default RateLimiterConstant.DEFAULT_PERMIT;
}
