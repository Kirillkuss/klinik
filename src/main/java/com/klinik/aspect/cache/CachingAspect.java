package com.klinik.aspect.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * АОП с кэшированием
 */
@Slf4j
@Aspect
@Component
public class CachingAspect {

    @AfterReturning(pointcut = "@annotation(org.springframework.cache.annotation.Cacheable)", returning = "result")
    public void logCacheable(JoinPoint joinPoint, Object result) {
        List<Object> list = (List<Object>) result;
        log.info("Cached method: " + joinPoint.getSignature().getName() + " , count: " + list.stream().count());
    }
}
