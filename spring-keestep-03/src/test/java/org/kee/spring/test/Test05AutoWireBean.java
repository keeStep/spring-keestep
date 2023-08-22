package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.beans.factory.surpport.DefaultListableBeanFactory;
import org.kee.spring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Unit test for simple App.
 */
public class Test05AutoWireBean {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void Test05AutoWireBean() {
        // TODO 创建文件 spring.xml
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. TODO 获取Bean对象调用方法
    }
}
