package com.klinik.aspect;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class GlobalAspect {
    
    //@Before( value = "@annotation( com.klinik.aspect.GlobalOperation)")
    public void getDoctorOperationInfo( JoinPoint joinPoint ){
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

        log.info("Method args types:");
        Arrays.stream(methodSignature.getParameterTypes())
              .forEach(s -> log.info("arg type: " + s));

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
        GlobalOperation doctorOperation = method.getAnnotation(GlobalOperation.class);
        log.info("Doctor operation annotation: " + doctorOperation);
        log.info("Doctor operation value: " + doctorOperation.operation());
        
    }

    @Around( value = "@annotation( com.klinik.aspect.GlobalOperation)")
    public Object logExecutionTime( ProceedingJoinPoint joinPoint ) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        MethodSignature methodSignature = ( MethodSignature ) joinPoint.getSignature();
        log.info( "Method "  + methodSignature.getMethod() + " execution time  >> " +  + stopWatch.getTotalTimeMillis() + " ms" );
        return proceed;
    }

   // @After( value = "@annotation( com.klinik.aspect.GlobalOperation)")
    public void getDoctorOperationAfter( JoinPoint joinPoint ){
        MethodSignature methodSignature = ( MethodSignature ) joinPoint.getSignature();
        log.info("____________After___________");
        log.info( "returning type: " +  methodSignature.getReturnType());

    }

} 
