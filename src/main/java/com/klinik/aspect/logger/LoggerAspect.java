package com.klinik.aspect.logger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.klinik.aspect.logger.annotation.ExecuteTimeLog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
    /**
     * Получение информации до выполнения метод о метода 
     * @param joinPoint
     */
    @Before( value = "@annotation( com.klinik.aspect.logger.annotation.OperationInfoBefore)")
    public void getOperationInfo( JoinPoint joinPoint ){
        log.info("____________Before___________");
        MethodSignature methodSignature = ( MethodSignature ) joinPoint.getSignature();
        //Получение информации о сигнатуре метода
        log.info("full method description: " + methodSignature.getMethod());
        log.info("method name: " + methodSignature.getMethod().getName());
        log.info("declaring type: " + methodSignature.getDeclaringType());
        // Получение информации об аргументах
        log.info("Method args names:");
        Arrays.stream(methodSignature.getParameterNames())
              .forEach(s -> log.info("arg name: " + s));
        // Получение информации об типах аргументах       
        log.info("Method args types:");
        Arrays.stream(methodSignature.getParameterTypes())
              .forEach(s -> log.info("arg type: " + s));
        // Получение информации об значения аргументов  
        log.info("Method args values:");
        Arrays.stream(joinPoint.getArgs())
              .forEach(o -> log.info("arg value: " + o.toString()));
        // Получение дополнительной информации
        log.info("returning type: " + methodSignature.getReturnType());
        log.info("method modifier: " + Modifier.toString(methodSignature.getModifiers()));
        Arrays.stream(methodSignature.getExceptionTypes())
          .forEach(aClass -> log.info("exception type: " + aClass));
        // Получение информации об аннотациях методов
        Method method = methodSignature.getMethod();
        ExecuteTimeLog operation = method.getAnnotation(ExecuteTimeLog.class);
        log.info("Operation annotation: " + operation);
        log.info("Operation value: " + operation.operation());
        
    }
    /**
     * Время выполнения метода через АОП
     * @param joinPoint - ProceedingJoinPoint
     * @return Object
     * @throws Throwable
     */
    @Around( value = "@annotation( com.klinik.aspect.logger.annotation.ExecuteTimeLog)")
    public Object logExecutionTime( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = proceedingJoinPoint.proceed();
        stopWatch.stop();
        MethodSignature methodSignature = ( MethodSignature ) proceedingJoinPoint.getSignature();
        String[] paramNames = methodSignature.getParameterNames();
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Method name: ").append( methodSignature.getMethod().getName()).append(", ");
        for (int i = 0; i < paramNames.length; i++) {
            logMessage.append(paramNames[i]).append(" = ").append( proceedingJoinPoint.getArgs()[i]);
            if (i < paramNames.length - 1) {
                logMessage.append(", "); 
            }
        }
        logMessage.append( ", Execution time: " + stopWatch.getTotalTimeMillis() + " ms");
        log.info(logMessage.toString());
        return proceed;
    }
    /**
     * Получение информации о методе после выполнения метода
     * @param joinPoint
     */
    @After( value = "@annotation( com.klinik.aspect.logger.annotation.OperationInfoAfter)")
    public void getOperationInfoAfter( JoinPoint joinPoint ){
        MethodSignature methodSignature = ( MethodSignature ) joinPoint.getSignature();
        log.info("____________After___________");
        log.info( "returning type: " +  methodSignature.getReturnType());

    }

} 
