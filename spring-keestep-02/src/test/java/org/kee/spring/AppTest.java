package org.kee.spring;

import org.junit.Test;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.surpport.DefaultListableBeanFactory;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.注册bean对象
        BeanDefinition beanDefinition = new BeanDefinition(TestBean.class);
        beanFactory.registerBeanDefinition("testBean", beanDefinition);

        // 3.获取bean
        TestBean testBean = (TestBean) beanFactory.getBean("testBean");
        testBean.printSomething();

        // 4.再次获取验证缓存
        TestBean testBeanSingle = (TestBean) beanFactory.getBean("testBean");
        testBeanSingle.printSomething();
    }
}
