package org.kee.spring.aop;

import java.lang.reflect.Method;

/**
 * <p>方式匹配定义
 *
 * @author Eric
 * @date 2023/9/8 22:29
 */
public interface MethodMatcher {

    /**
     * 给定的方法能否匹配上切点表达式
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}
