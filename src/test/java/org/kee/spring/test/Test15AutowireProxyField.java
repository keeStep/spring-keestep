package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean.CityService;
import org.kee.spring.test.bean11And12.IUserService;

/**
 * Unit test for simple App.
 */
public class Test15AutowireProxyField {


    @Test
    public void Test15AutowireProxyField() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}
