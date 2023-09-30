package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean.CityService;

/**
 * Unit test for simple App.
 */
public class Test14AutowireFieldValue {


    @Test
    public void TestAutowire() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        CityService cityService = applicationContext.getBean("cityService", CityService.class);
        System.out.println("测试结果：" + cityService.queryCityInfo());
    }
}
