package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.aop.AdvisedSupport;
import org.kee.spring.aop.TargetSource;
import org.kee.spring.aop.aspectj.AspectJExpressionPointcut;
import org.kee.spring.aop.framework.JdkDynamicAopProxy;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean.CityDao;
import org.kee.spring.test.bean.CityService;
import org.kee.spring.test.bean11And12.IUserService;
import org.kee.spring.test.bean11And12.UserService;
import org.kee.spring.test.bean11And12.UserServiceInterceptor;

import java.lang.reflect.Method;

/**
 * Unit test for simple App.
 */
public class Test12Aop4SpringBean {


    @Test
    public void TestSpringBeanProxy() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("AOP代理测试结果：" + userService.queryUserInfo());
        System.out.println("AOP代理测试结果：" + userService.register("马斯克"));
    }
}
