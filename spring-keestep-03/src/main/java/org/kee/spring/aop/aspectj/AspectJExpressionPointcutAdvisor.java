package org.kee.spring.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.kee.spring.aop.Pointcut;
import org.kee.spring.aop.PointcutAdvisor;

import java.util.Objects;

/**
 * <p>获取切面表达式的访问者
 * Pointcut + Advice
 *
 * 使用：通过Bean注入 advice 和 expression, 内部创建 Pointcut
 * @author Eric
 * @date 2023/9/14 23:26
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    /**
     * 切面
     */
    private AspectJExpressionPointcut pointcut;
    /**
     * 通知处理（这里特指拦截通知处理）
     */
    private Advice advice;
    /**
     * 表达式
     */
    private String expression;


    /**
     * 获取驱动此通知的切入点
     * Get the Pointcut that drives this advisor.
     *
     * @return
     */
    @Override
    public Pointcut getPointcut() {
        if (Objects.isNull(pointcut)) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    /**
     * Return the advice part of this aspect. An advice may be an
     * interceptor, a before advice, a throws advice, etc.
     *
     * @return the advice that should apply if the pointcut matches
     * @see MethodInterceptor
     * @see org.kee.spring.aop.BeforeAdvice
     */
    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
