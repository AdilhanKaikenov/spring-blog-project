package com.epam.adok.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogDaoMethodsAspect {

    private static final Logger log = LoggerFactory.getLogger(LogDaoMethodsAspect.class);

    @Pointcut(value = "target(com.epam.adok.core.dao.Dao)")
    public void daoInterfacePointcut() {

    }

    @Before(value = "daoInterfacePointcut()")
    public void targetMethodAdvice(JoinPoint joinPoint) {
        log.info("Dao method logging start ::: {}", joinPoint.getSignature().toShortString());
    }

}
