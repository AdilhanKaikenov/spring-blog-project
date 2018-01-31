package com.epam.adok.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    @Pointcut(value = "!@annotation(org.springframework.transaction.annotation.Transactional)")
    public void targetMethodNotTransactional() {
    }

    @Before(value = "targetMethodsPointcut() && targetMethodNotTransactional()")
    public void startMethodLogAdvice(JoinPoint joinPoint) {
        log.info("Before method : {} ", joinPoint.getSignature().toShortString());
    }

    @After(value = "targetMethodsPointcut() && targetMethodNotTransactional()")
    public void endMethodLogAdvice(JoinPoint joinPoint) {
        log.info("After method : {} ", joinPoint.getSignature().toShortString());
    }

    @Around(value = "targetMethodsPointcut() && @annotation(org.springframework.transaction.annotation.Transactional)")
    public Object logTransactionalMethodAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().toShortString();
        log.info("Start TRANSACTIONAL method : {} ", methodName);

        Object result = pjp.proceed();

        if (result != null) {
            log.info("{} returned {}", methodName, result.toString());
        }
        log.info("Transaction completed");
        return result;
    }
}
