package com.eventRegistration.eventRegistrationSystem.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.eventRegistration.eventRegistrationSystem.controller.VisitorController.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // Proceed with the actual method execution

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.info("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);

        return result;
    }
}
