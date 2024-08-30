package com.klinik.aspect.error;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
/**
 * Навыполнение всех контролеров обработчяик
 */
@Slf4j
@Aspect
@Component
public class ExceptionAspect {

    @Around("execution(* com.klinik.controller..*(..))")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("Exception in method: " + joinPoint.getSignature().getName() + ", Message: " + ex.getMessage());
            throw ex;
        }
    }
}
