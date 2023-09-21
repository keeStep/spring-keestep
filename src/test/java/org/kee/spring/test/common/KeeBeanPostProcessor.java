package org.kee.spring.test.common;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.config.BeanPostProcessor;
import org.kee.spring.test.bean.TestBeanReferenceParent;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/29 22:01
 */
public class KeeBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("testBeanReferenceParent".equals(beanName)) {
            TestBeanReferenceParent testBeanReferenceParent = (TestBeanReferenceParent) bean;
            testBeanReferenceParent.setCityName("深圳市");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
