package org.kee.spring;

import org.junit.Test;
import org.kee.spring.bean.TestBean;
import org.kee.spring.bean.TestParamBean;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.surpport.DefaultListableBeanFactory;

/**
 * Unit test for simple App.
 */
public class Test03BeanInstantiation {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testBeanFactory() {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.注册bean对象
        BeanDefinition beanDefinition = new BeanDefinition(TestParamBean.class);
        beanFactory.registerBeanDefinition("testParamBean", beanDefinition);

        // 3.获取bean (构造函数带入参)
        TestParamBean testBean = (TestParamBean) beanFactory.getBean("testParamBean", "响尾蛇");

        // 4.执行bean方法
        testBean.printSomething();
    }
}
