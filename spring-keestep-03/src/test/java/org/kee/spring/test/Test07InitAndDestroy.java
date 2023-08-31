package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.beans.factory.surpport.DefaultListableBeanFactory;
import org.kee.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean.CityService;
import org.kee.spring.test.bean.TestBeanReferenceParent;
import org.kee.spring.test.common.KeeBeanFactoryPostProcessor;
import org.kee.spring.test.common.KeeBeanPostProcessor;

/**
 * Unit test for simple App.
 */
public class Test07InitAndDestroy {

    @Test
    public void Test() {
        // 1.初始化 ApplicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2.获取Bean对象调用方法
        CityService cityService = (CityService) applicationContext.getBean("cityService");
        System.out.println("获取的城市名称：" + cityService.queryCityInfo());;
    }
}
