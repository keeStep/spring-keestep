package org.kee.spring.aop.support;


import org.kee.spring.aop.MethodMatcher;

import java.lang.reflect.Method;

/**
 * @author zhangdd on 2022/2/27
 */
public abstract class StaticMethodMatcher implements MethodMatcher {

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        throw new UnsupportedOperationException("Illegal MethodMatcher usage");
    }
}
