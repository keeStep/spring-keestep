package org.kee.spring.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.kee.spring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * <p>方法Before拦截器
 *
 * 调用 {@link MethodBeforeAdvice#before(Method, Object[], Object)} 来实现拦截通知
 *
 * @author Eric
 * @date 2023/9/14 23:35
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
