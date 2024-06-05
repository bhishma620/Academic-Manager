package com.bhishma.ams.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class CachingKey implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getSimpleName()+"$"
                +method.getName()+"$"+
                StringUtils.arrayToDelimitedString(params,"$");
    }
}
