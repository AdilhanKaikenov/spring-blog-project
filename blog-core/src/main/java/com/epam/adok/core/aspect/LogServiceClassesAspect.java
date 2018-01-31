package com.epam.adok.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogServiceClassesAspect {

    private static final Logger log = LoggerFactory.getLogger(LogServiceClassesAspect.class);

    // any join point within the service package or a sub-package
    @Pointcut(value = "within(com.epam.adok.core.service..*)")
    public void targetMethodsPointcut() {
    }

    @Before(value = "targetMethodsPointcut()")
    public void startMethodLogAdvice(JoinPoint joinPoint) {
        log.info("Before method : {} ", joinPoint.getSignature().toShortString());
    }

    @After(value = "targetMethodsPointcut()")
    public void endMethodLogAdvice(JoinPoint joinPoint) {
        log.info("After method : {} ", joinPoint.getSignature().toShortString());
    }
}
