package org.kee.spring.test.bean11And12;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>自定义AOP的拦截器
 * （注意不是Cglib包里那个MethodInterceptor）
 *
 * @author Eric
 * @date 2023/9/10 22:55
 */
public class UserServiceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("\r\nAOP拦截器|开始监控方法 ************** " + methodInvocation.getMethod());
        long start = System.currentTimeMillis();

        try {
            return methodInvocation.proceed();
        } finally {
            System.out.println("AOP拦截器|方法耗时： " + (System.currentTimeMillis() - start) + "毫秒");
            System.out.println("AOP拦截器|结束监控方法 ************** " + methodInvocation.getMethod() + "\r\n");
        }

    }
}
