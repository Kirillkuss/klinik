package com.klinik.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;
import java.lang.reflect.Method;
import java.util.UUID;

public class CustomKeyGenerator implements KeyGenerator {

    public Object generate(Object target, Method method, Object... params) {
        return UUID.randomUUID().toString();
        /**+ " for " + target.getClass().getSimpleName() + " - " + method.getName() + " - "
                + StringUtils.arrayToDelimitedString(params, "_");*/
    }
}