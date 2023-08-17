package org.kee.spring;

import org.junit.Test;
import org.kee.spring.test.bean.TestBeanReference;
import org.kee.spring.test.bean.TestBeanReferenceParent;
import org.kee.spring.test.bean.TestParamBean;
import org.kee.spring.beans.PropertyValue;
import org.kee.spring.beans.PropertyValues;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.surpport.DefaultListableBeanFactory;

/**
 * Unit test for simple App.
 */
public class Test04BeanApplyProperty {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testBeanApplyProperty() {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.注册bean引用对象
        BeanDefinition refBeanDefinition = new BeanDefinition(TestBeanReference.class);
        beanFactory.registerBeanDefinition("testBeanReference", refBeanDefinition);

        // 3.注册带属性的Bean
        // 3.1.属性构建
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("cityCode", "XIY"));
        propertyValues.addPropertyValue(new PropertyValue("testBeanReference", beanFactory.getBean("testBeanReference")));
        // 3.2.注册bean
        BeanDefinition beanDefinition = new BeanDefinition(TestBeanReferenceParent.class, propertyValues);
        beanFactory.registerBeanDefinition("testBeanReferenceParent", beanDefinition);

        // 4.获取bean
        TestBeanReferenceParent testBean = (TestBeanReferenceParent) beanFactory.getBean("testBeanReferenceParent");

        // 5.执行bean方法
        System.out.println("获取的城市名称：" + testBean.queryCityName());;
    }
}
