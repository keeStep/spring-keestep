package org.kee.spring.test.bean11;

import org.kee.spring.aop.MethodBeforeAdvice;
import org.kee.spring.context.annotation.Component;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/9/16 23:55
 */
@Component("beforeAdvice")
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    /**
     * method 被调用前调用执行
     *
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("```````Dia！~~ 我就是个拦截器，代理执行的我吧");
    }
}
