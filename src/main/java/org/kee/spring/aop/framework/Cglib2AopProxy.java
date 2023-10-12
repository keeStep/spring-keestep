package org.kee.spring.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.kee.spring.aop.AdvisedSupport;
import org.kee.spring.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * <p> Cglib 实现代理
 *
 * @author Eric
 * @date 2023/9/10 0:00
 */
public class Cglib2AopProxy implements AopProxy {

    private final AdvisedSupport advisedSupport;

    public Cglib2AopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();

        Class<?> aClass = advisedSupport.getTargetSource().getTarget().getClass();
        aClass = ClassUtils.isCglibProxyClass(aClass) ? aClass.getSuperclass() : aClass;
        enhancer.setSuperclass(aClass);
        enhancer.setInterfaces(advisedSupport.getTargetSource().getTargetClass());
        // callback  加拦截器干啥？ -- Cglib的代理执行操作(这个是Cglib专有拦截器，看包名！)
        // 【可以对比JDK代理是实现了 InvocationHandler 的 invoke(), 而Cglib需要定义个拦截器来使用代理去执行】
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));

        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advisedSupport;

        public DynamicAdvisedInterceptor(AdvisedSupport advisedSupport) {
            this.advisedSupport = advisedSupport;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, objects, methodProxy);

            // 方法匹配成功则执行方法拦截操作
            if (advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTarget().getClass())) {
                return advisedSupport.getMethodInterceptor().invoke(methodInvocation);
            }

            // 匹配不成功则直接继续执行原方法
            return methodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        /**
         * Cglib 代理执行对象的方法
         * @return
         * @throws Throwable
         */
        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments);
        }
    }
}
