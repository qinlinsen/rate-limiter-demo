package com.qls.ratelimiterdemo.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.qls.ratelimiterdemo.annotation.RateLimit;
import com.qls.ratelimiterdemo.exception.RateLimitException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * aop of RateLimit
 * @author qinlinsen
 */
@Aspect
@Component
public class RateLimitAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimitAop.class);
    /**
     * rate limiter connection identifier symbol
     */
    private static final String RATE_LIMITER_CONNECTION_IDENTIFIER_SYMBOL ="_";
    private ConcurrentHashMap<String, RateLimiter> RATE_LIMITER  = new ConcurrentHashMap<>();
    private RateLimiter rateLimiter;

    /**
     * pointcut about annotation com.qls.ratelimiterdemo.annotation.RateLimit
     */
    @Pointcut("@annotation(com.qls.ratelimiterdemo.annotation.RateLimit)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //get signature
        Signature sig = point.getSignature();
        //get method signature
        MethodSignature methodSignature = (MethodSignature) sig;
        //get target
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        RateLimit annotation = currentMethod.getAnnotation(RateLimit.class);
        //get permits
        double limitNum = annotation.permit();
        String defaultRateLimiterIdentifier =defaultRateLimiterIdentifier(target,methodSignature);
        LOGGER.info(">>>>>defaultRateLimiterIdentifier={}>>>>>",defaultRateLimiterIdentifier);
        if(RATE_LIMITER.containsKey(defaultRateLimiterIdentifier)){
            rateLimiter=RATE_LIMITER.get(defaultRateLimiterIdentifier);
        }else {
            RATE_LIMITER.put(defaultRateLimiterIdentifier, RateLimiter.create(limitNum));
            rateLimiter=RATE_LIMITER.get(defaultRateLimiterIdentifier);
        }
        if(rateLimiter.tryAcquire()) {
            return point.proceed();
        } else {
            LOGGER.info(">>> exceed rate limit occurred RateLimitException identifier is {}>>>",defaultRateLimiterIdentifier);
            throw new RateLimitException("request to many, please try again later");
        }
    }


    private String defaultRateLimiterIdentifier(Object target, MethodSignature methodSignature){
        Class<?> targetClz = target.getClass();
        String targetClzName = targetClz.getName();
        String methodName = methodSignature.getName();
        Class[] parameterTypes = methodSignature.getParameterTypes();
        StringBuilder identifierBuilder = new StringBuilder();

        identifierBuilder.append(targetClzName);
        identifierBuilder.append(RATE_LIMITER_CONNECTION_IDENTIFIER_SYMBOL);
        identifierBuilder.append(methodName);
        if(Objects.nonNull(parameterTypes)){
            identifierBuilder.append(RATE_LIMITER_CONNECTION_IDENTIFIER_SYMBOL);
            Arrays.stream(parameterTypes).forEach(parameterType -> {
                identifierBuilder.append(parameterType.getName());
                identifierBuilder.append(RATE_LIMITER_CONNECTION_IDENTIFIER_SYMBOL);
            });
        }

        return identifierBuilder.toString();
    }
}
