package org.kee.spring.aop;

/**
 * <p> 获取切面的访问者
 *
 * @author Eric
 * @date 2023/9/14 23:23
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * 获取驱动此通知的切入点
     * Get the Pointcut that drives this advisor.
     * @return
     */
    Pointcut getPointcut();
}
