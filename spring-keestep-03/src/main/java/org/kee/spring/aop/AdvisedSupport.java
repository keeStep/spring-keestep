package org.kee.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * <p> AOP代理配置管理器的基类
 *
 * @author Eric
 * @date 2023/9/8 22:57
 */
public class AdvisedSupport {

    /**
     * 是否需要Cglib代理
     */
    private boolean proxyTargetClass;

    /**
     * 被代理的目标对象
     */
    private TargetSource targetSource;

    /**
     * 方法匹配器
     */
    private MethodMatcher methodMatcher;

    /**
     * 方法拦截器
     */
    private MethodInterceptor methodInterceptor;


    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }
}
