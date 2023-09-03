package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean.CityService;

/**
 * Unit test for simple App.
 */
public class Test08Aware {

    @Test
    public void Test() {
        // 1.初始化 ApplicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        // 注册容器关闭的钩子
        applicationContext.registerShutdownHook();

        // 2.获取Bean对象调用方法
        CityService cityService = (CityService) applicationContext.getBean("cityService");
        System.out.println("获取的城市信息：" + cityService.queryCityInfo());
        System.out.println("BeanFactoryAware 感知到的信息：" + cityService.getBeanFactory());
        System.out.println("ApplicationContextAware 感知到的信息：" + cityService.getApplicationContext());
    }
}
