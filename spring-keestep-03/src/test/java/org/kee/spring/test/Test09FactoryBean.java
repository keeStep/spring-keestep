package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean.CityService;

/**
 * Unit test for simple App.
 */
public class Test09FactoryBean {

    /**
     * 测试单例模式与原型模式
     */
    @Test
    public void TestPrototype() {
        // 1.初始化 ApplicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2.多次获取bean对象，对比 {单例模式与原型模式} 下多个对象是否相同
        CityService cityService1 = (CityService) applicationContext.getBean("cityService");
        CityService cityService2 = (CityService) applicationContext.getBean("cityService");

        // 3.打印对比对象
        System.out.println(cityService1 + " -> 十六进制哈希值：" + Integer.toHexString(cityService1.hashCode()));
        System.out.println(cityService2 + " -> 十六进制哈希值：" + Integer.toHexString(cityService2.hashCode()));
    }

}
