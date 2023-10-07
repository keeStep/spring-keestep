package org.kee.spring.test.bean;

import org.kee.spring.aop.MethodBeforeAdvice;
import org.kee.spring.context.annotation.Component;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/7 23:01
 */
@Component("beforeAdvice")
public class SpouseAdvice implements MethodBeforeAdvice {
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
        System.out.println("关怀小两口(切面)：" + method);
    }
}
