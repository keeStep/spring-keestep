package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.beans.factory.support.DefaultListableBeanFactory;
import org.kee.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean.TestBeanReferenceParent;
import org.kee.spring.test.common.KeeBeanFactoryPostProcessor;
import org.kee.spring.test.common.KeeBeanPostProcessor;

/**
 * Unit test for simple App.
 */
public class Test06PostProcessorContext {

    @Test
    public void Test06AutoPostProcessor() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // ==> 3.加载BeanDefinition后，实例化之前执行BeanFactoryPostProcessor，修改BeanDefinition属性
        KeeBeanFactoryPostProcessor keeBeanFactoryPostProcessor = new KeeBeanFactoryPostProcessor();
        keeBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // ==> 4.配置BeanPostProcessor，方便在实例化之后执行，修改Bean对象的属性
        KeeBeanPostProcessor keeBeanPostProcessor = new KeeBeanPostProcessor();
        beanFactory.addBeanPostProcessor(keeBeanPostProcessor);

        // 5. 获取Bean对象调用方法
        TestBeanReferenceParent testBean = (TestBeanReferenceParent) beanFactory.getBean("testBeanReferenceParent");
        System.out.println("获取的城市名称：" + testBean.queryCityInfo());;
    }


    @Test
    public void Test06Context() {
        // 1.初始化 ApplicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

        // 2.获取Bean对象调用方法
        TestBeanReferenceParent testBean = (TestBeanReferenceParent) applicationContext.getBean("testBeanReferenceParent");
        System.out.println("获取的城市名称：" + testBean.queryCityInfo());;
    }
}
