package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.BeanDefinition;
import org.kee.spring.BeanFactory;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:32
 */
public class TestBeanTest {

    @Test
    public void testBeanFactory() {
        // 1.初始化 beanFactory
        BeanFactory beanFactory = new BeanFactory();

        // 2.注册 Bean
        BeanDefinition beanDefinition = new BeanDefinition(new TestBean());
        beanFactory.registerBeanDefinition("testBean", beanDefinition);

        // 3.获取Bean
        TestBean testBean = (TestBean) beanFactory.getBean("testBean");

        // 4.使用Bean
        testBean.printSomething();
    }

}