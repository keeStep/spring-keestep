package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.aop.AdvisedSupport;
import org.kee.spring.aop.TargetSource;
import org.kee.spring.aop.aspectj.AspectJExpressionPointcut;
import org.kee.spring.aop.framework.JdkDynamicAopProxy;
import org.kee.spring.test.bean.CityDao;
import org.kee.spring.test.bean.CityService;
import org.kee.spring.test.bean11.IUserService;
import org.kee.spring.test.bean11.UserService;
import org.kee.spring.test.bean11.UserServiceInterceptor;

import java.lang.reflect.Method;

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
}
