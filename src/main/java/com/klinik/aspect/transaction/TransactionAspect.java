package com.klinik.aspect.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
/**
 * Пример логирования методов на который аннтотация @Transactional
 */
@Slf4j
@Aspect
@Component
public class TransactionAspect {
    @Around(value = "@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object logTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("Transaction starting for method: " + joinPoint.getSignature().getName());
            Object result = joinPoint.proceed();
            log.info("Transaction committed for method: " + joinPoint.getSignature().getName());
            return result;
        } catch (Throwable t) {
            log.error("Transaction rolled back for method: " + joinPoint.getSignature().getName());
            throw t; 
        }
    }
}