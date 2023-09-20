package org.kee.spring.test;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;
import org.kee.spring.aop.AdvisedSupport;
import org.kee.spring.aop.MethodMatcher;
import org.kee.spring.aop.TargetSource;
import org.kee.spring.aop.aspectj.AspectJExpressionPointcut;
import org.kee.spring.aop.framework.JdkDynamicAopProxy;
import org.kee.spring.aop.framework.ReflectiveMethodInvocation;
import org.kee.spring.test.bean.CityDao;
import org.kee.spring.test.bean.CityService;
import org.kee.spring.test.bean11And12.IUserService;
import org.kee.spring.test.bean11And12.UserService;
import org.kee.spring.test.bean11And12.UserServiceInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Unit test for simple App.
 */
public class Test11Aop {


    @Test
    public void TestAop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(
                "execution(* org.kee.spring.test.bean.CityService.*(..))");

        Class<?> clazz = CityService.class;
        Class<?> wrongClass = CityDao.class;
        Method method = clazz.getDeclaredMethod("queryCityInfo");
        Method wrongMethod = wrongClass.getDeclaredMethod("initDataMethod");

        System.out.println("匹配测试：");
        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));

        System.out.println("不匹配测试：");
        System.out.println(pointcut.matches(wrongClass));
        System.out.println(pointcut.matches(wrongMethod, wrongClass));
    }


    @Test
    public void TestProxy() {
        // 1.组装代理信息
        AdvisedSupport advised = new AdvisedSupport();
        advised.setTargetSource(new TargetSource(new UserService()));
        advised.setMethodInterceptor(new UserServiceInterceptor());
        advised.setMethodMatcher(new AspectJExpressionPointcut("execution(* org.kee.spring.test.bean11.IUserService.*(..))"));

        // 2.Jdk代理
        IUserService proxyJdk = (IUserService) new JdkDynamicAopProxy(advised).getProxy();
        System.out.println("JDK代理测试结果：" + proxyJdk.queryUserInfo());

        // 3.Cglib代理
        IUserService proxyCglib = (IUserService) new JdkDynamicAopProxy(advised).getProxy();
        System.out.println("JDK代理测试结果：" + proxyCglib.register("马斯克"));

    }


    /**
     * 没有分治封装的AOP测试
     *
     * 本章是把该测试拆解封装【代理对象，方法匹配，方法拦截】
     * 扩展性更好
     */
    @Test
    public void test_proxy_method() {
        // 目标对象(可以替换成任何的目标对象)
        Object targetObj = new UserService();

        // AOP 代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            // 方法匹配器
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* org.kee.spring.test.bean11.IUserService.*(..))");

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    // 方法拦截器
                    MethodInterceptor methodInterceptor = invocation -> {
                        System.out.println("\r\nAOP拦截器|开始监控方法 ************** " + invocation.getMethod());
                        long start = System.currentTimeMillis();

                        try {
                            return invocation.proceed();
                        } finally {
                            System.out.println("AOP拦截器|方法耗时： " + (System.currentTimeMillis() - start) + "毫秒");
                            System.out.println("AOP拦截器|结束监控方法 ************** " + invocation.getMethod() + "\r\n");
                        }
                    };
                    // 反射调用
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                return method.invoke(targetObj, args);
            }
        });

        String result = proxy.queryUserInfo();
        System.out.println("测试结果：" + result);

    }
}
