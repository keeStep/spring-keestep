package org.kee.spring.aop;

import java.lang.reflect.Method;

/**
 * 方法执行Before通知标记接口
 * <p>Advice invoked before a method is invoked. Such advices cannot
 * prevent the method call proceeding, unless they throw a Throwable.
 *
 * @author Eric
 * @date 2023/9/14 23:11
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * method 被调用前调用执行
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
