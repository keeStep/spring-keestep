package org.kee.spring.aop;

import org.aopalliance.aop.Advice;

/**
 * <p> 获取通知标记类的访问者
 *  {@link org.aopalliance.aop.Advice}
 *
 * @author Eric
 * @date 2023/9/14 23:14
 */
public interface Advisor {

    /**
     * Return the advice part of this aspect. An advice may be an
     * interceptor, a before advice, a throws advice, etc.
     * @return the advice that should apply if the pointcut matches
     * @see org.aopalliance.intercept.MethodInterceptor
     * @see BeforeAdvice
     */
    Advice getAdvice();
}
